package models;

import java.io.*;
import java.util.*;

public class Grafo {

    public Map<String, Nodo> nodos = new HashMap<>();

    public void agregarNodo(String id, int x, int y) {
        nodos.putIfAbsent(id, new Nodo(id, x, y));
    }

    public void eliminarNodo(String id) {
        Nodo n = nodos.get(id);
        if (n == null) return;

        for (Nodo otro : nodos.values()) {
            otro.eliminarVecino(n);
        }
        nodos.remove(id);
    }

    public void limpiar() {
        nodos.clear();
    }

    public Collection<Nodo> obtenerTodosLosNodos() {
        return nodos.values();
    }

    public void conectar(String a, String b) {
        Nodo n1 = nodos.get(a);
        Nodo n2 = nodos.get(b);
        if (n1 != null && n2 != null) {
            n1.agregarVecino(n2);
            n2.agregarVecino(n1);
        }
    }

    public void conectarDirigido(String a, String b) {
        Nodo n1 = nodos.get(a);
        Nodo n2 = nodos.get(b);
        if (n1 != null && n2 != null) {
            n1.conectarA(n2);
        }
    }

    public void conectarBidireccional(String a, String b) {
        conectarDirigido(a, b);
        conectarDirigido(b, a);
    }

    public void borrarDireccion(String a, String b) {
        Nodo n1 = nodos.get(a);
        Nodo n2 = nodos.get(b);
        if (n1 != null && n2 != null) {
            n1.desconectarA(n2);
            n2.desconectarA(n1);
        }
    }

    public void guardarEnArchivo(String ruta) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {

            for (Nodo n : nodos.values()) {
                pw.println("N," + n.getId() + "," + n.getX() + "," + n.getY());
            }

            Set<String> guardadas = new HashSet<>();
            for (Nodo n : nodos.values()) {
                for (Nodo v : n.getVecinos()) {
                    String key = n.getId() + "-" + v.getId();
                    String rev = v.getId() + "-" + n.getId();
                    if (!guardadas.contains(key) && !guardadas.contains(rev)) {
                        pw.println("A," + n.getId() + "," + v.getId());
                        guardadas.add(key);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarDesdeArchivo(String ruta) {
        nodos.clear();
        List<String[]> aristas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String l;
            while ((l = br.readLine()) != null) {
                String[] p = l.split(",");
                if (p[0].equals("N")) {
                    agregarNodo(p[1], Integer.parseInt(p[2]), Integer.parseInt(p[3]));
                } else if (p[0].equals("A")) {
                    aristas.add(p);
                }
            }

            for (String[] a : aristas) {
                conectar(a[1], a[2]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}