package models;

import java.io.*;
import java.util.*;

public class Grafo {

    public Map<String, Nodo> nodos = new HashMap<>();

    public void agregarNodo(String id, int x, int y) {
        nodos.putIfAbsent(id, new Nodo(id, x, y));
    }

    public void conectar(String a, String b) {
        if (nodos.containsKey(a) && nodos.containsKey(b)) {
            nodos.get(a).conectar(nodos.get(b));
            nodos.get(b).conectar(nodos.get(a));
        }
    }

    public void eliminarNodo(String id) {
        Nodo eliminado = nodos.remove(id);
        if (eliminado == null) {
            return;
        }

        for (Nodo n : nodos.values()) {
            n.vecinos.remove(eliminado);
        }
    }

    public void limpiar() {
        nodos.clear();
    }

    public Collection<Nodo> obtenerTodosLosNodos() {
        return nodos.values();
    }

    public void guardarEnArchivo(String ruta) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {

            for (Nodo n : nodos.values()) {
                bw.write("N," + n.getId() + "," + n.getX() + "," + n.getY() + "," + n.isBloqueado());
                bw.newLine();
            }

            Set<String> guardadas = new HashSet<>();
            for (Nodo n : nodos.values()) {
                for (Nodo v : n.vecinos) {
                    String key = n.getId().compareTo(v.getId()) < 0
                            ? n.getId() + "-" + v.getId()
                            : v.getId() + "-" + n.getId();

                    if (!guardadas.contains(key)) {
                        bw.write("A," + n.getId() + "," + v.getId());
                        bw.newLine();
                        guardadas.add(key);
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error guardando grafo");
        }
    }

    public void cargarDesdeArchivo(String ruta) {
        nodos.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String l;
            while ((l = br.readLine()) != null) {
                String[] p = l.split(",");

                if (p[0].equals("N")) {
                    Nodo n = new Nodo(
                            p[1],
                            Integer.parseInt(p[2]),
                            Integer.parseInt(p[3])
                    );
                    if (p.length > 4) {
                        n.setBloqueado(Boolean.parseBoolean(p[4]));
                    }
                    nodos.put(p[1], n);
                }
            }
        } catch (IOException e) {
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String l;
            while ((l = br.readLine()) != null) {
                String[] p = l.split(",");
                if (p[0].equals("A")) {
                    conectar(p[1], p[2]);
                }
            }
        } catch (IOException e) {
        }
    }
}
