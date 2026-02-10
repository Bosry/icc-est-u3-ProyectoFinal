package models;

import java.io.*;
import java.util.*;

public class Grafo {
    public Map<String, Nodo> nodos = new HashMap<>();
    public void agregarNodo(String id, int x, int y) {
        Nodo nuevo = new Nodo(id, x, y);
        nodos.put(id, nuevo);
    }
    public Collection<Nodo> obtenerTodosLosNodos() {
        return nodos.values();
    }
    public Map<String, Nodo> getNodos() {
        return nodos;
    }
    public void eliminarNodo(String id) {
        Nodo nodoAEliminar = nodos.get(id);
        if (nodoAEliminar != null) {
            for (Nodo n : nodos.values()) {
                n.getVecinos().remove(nodoAEliminar);
            }
            nodos.remove(id);
        }
    }

    public void guardarEnArchivo(String ruta) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            for (Nodo n : nodos.values()) {
                pw.print("NODO," + n.getId() + "," + n.getX() + "," + n.getY() + "," + n.isBloqueado());
                for (Nodo v : n.getVecinos()) {
                    pw.print("," + v.getId());
                }
                pw.println();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void cargarDesdeArchivo(String ruta) {
        File f = new File(ruta);
        if (!f.exists()) return;
        nodos.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] d = linea.split(",");
                if (d[0].trim().equalsIgnoreCase("NODO")) {
                    Nodo n = new Nodo(d[1].trim(), Integer.parseInt(d[2].trim()), Integer.parseInt(d[3].trim()));
                    n.setBloqueado(Boolean.parseBoolean(d[4].trim()));
                    nodos.put(n.getId(), n);
                }
            }
            try (BufferedReader br2 = new BufferedReader(new FileReader(f))) {
                while ((linea = br2.readLine()) != null) {
                    if (linea.trim().isEmpty()) continue;
                    String[] d = linea.split(",");
                    if (d.length > 1) {
                        Nodo n = nodos.get(d[1].trim());
                        if (n != null) {
                            for (int i = 5; i < d.length; i++) {
                                Nodo v = nodos.get(d[i].trim());
                                if (v != null) n.agregarVecino(v);
                            }
                        }
                    }
                }
            }
            System.out.println("Nodos cargados correctamente: " + nodos.size());
        } catch (Exception e) { 
            System.err.println("Error al cargar grafo: " + e.getMessage());
            e.printStackTrace(); 
        }
    }
    public List<String> bfs(String inicioId, String finId, List<String> explorados) {
        Queue<String> cola = new LinkedList<>();
        Map<String, String> padres = new HashMap<>();
        cola.add(inicioId);
        padres.put(inicioId, null);

        while (!cola.isEmpty()) {
            String actualId = cola.poll();
            explorados.add(actualId);
            if (actualId.equals(finId)) return reconstruirRuta(padres, finId);

            Nodo actualNodo = nodos.get(actualId);
            if (actualNodo != null) {
                for (Nodo v : actualNodo.getVecinos()) {
                    if (!v.isBloqueado() && !padres.containsKey(v.getId())) {
                        padres.put(v.getId(), actualId);
                        cola.add(v.getId());
                    }
                }
            }
        }
        return null;
    }

    public List<String> dfs(String inicioId, String finId, List<String> explorados) {
        Stack<String> pila = new Stack<>();
        Map<String, String> padres = new HashMap<>();
        pila.push(inicioId);
        padres.put(inicioId, null);

        while (!pila.isEmpty()) {
            String actualId = pila.pop();
            explorados.add(actualId);
            if (actualId.equals(finId)) return reconstruirRuta(padres, finId);

            Nodo actualNodo = nodos.get(actualId);
            if (actualNodo != null) {
                for (Nodo v : actualNodo.getVecinos()) {
                    if (!v.isBloqueado() && !padres.containsKey(v.getId())) {
                        padres.put(v.getId(), actualId);
                        pila.push(v.getId());
                    }
                }
            }
        }
        return null;
    }

    private List<String> reconstruirRuta(Map<String, String> padres, String finId) {
        List<String> ruta = new ArrayList<>();
        for (String at = finId; at != null; at = padres.get(at)) {
            ruta.add(at);
        }
        Collections.reverse(ruta);
        return ruta;
    }

    public void registrarTiempoCSV(String alg, double ms, int tam) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("assets/tiempos.csv", true))) {
            pw.println(alg + "," + ms + "," + tam + "," + new java.util.Date());
        } catch (IOException e) { e.printStackTrace(); }
    }
}