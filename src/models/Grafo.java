package models;

import java.io.*;
import java.util.*;

public class Grafo {

    public Map<String, Nodo> nodos = new HashMap<>();

    public void agregarNodo(String id, int x, int y) {
        if (!nodos.containsKey(id)) {
            nodos.put(id, new Nodo(id, x, y));
        }
    }
    public void registrarTiempoCSV(String algoritmo, double tiempoMs, int numNodos) {
        File folder = new File("assets");
        if (!folder.exists()) {
            folder.mkdir();
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter("assets/tiempos.csv", true))) {
            pw.println(algoritmo + "," + String.format("%.4f", tiempoMs) + "," + numNodos + "," + new java.util.Date());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<String> bfs(String inicioId, String finId, List<String> explorados) {
        if (!nodos.containsKey(inicioId) || !nodos.containsKey(finId)) {
            return null;
        }
        Queue<String> cola = new LinkedList<>();
        Map<String, String> padres = new HashMap<>();
        Set<String> visitados = new HashSet<>();

        cola.add(inicioId);
        visitados.add(inicioId);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            explorados.add(actual);

            if (actual.equals(finId)) {
                return reconstruirCamino(padres, inicioId, finId);
            }

            for (Nodo v : nodos.get(actual).getVecinos()) {
                if (!visitados.contains(v.getId()) && !v.isBloqueado()) {
                    visitados.add(v.getId());
                    padres.put(v.getId(), actual);
                    cola.add(v.getId());
                }
            }
        }
        return null;
    }
    public List<String> dfs(String inicioId, String finId, List<String> explorados) {
        if (!nodos.containsKey(inicioId) || !nodos.containsKey(finId)) {
            return null;
        }
        Stack<String> pila = new Stack<>();
        Map<String, String> padres = new HashMap<>();
        Set<String> visitados = new HashSet<>();

        pila.push(inicioId);

        while (!pila.isEmpty()) {
            String actual = pila.pop();
            if (visitados.contains(actual)) {
                continue;
            }

            visitados.add(actual);
            explorados.add(actual);

            if (actual.equals(finId)) {
                return reconstruirCamino(padres, inicioId, finId);
            }

            for (Nodo v : nodos.get(actual).getVecinos()) {
                if (!visitados.contains(v.getId()) && !v.isBloqueado()) {
                    padres.put(v.getId(), actual);
                    pila.push(v.getId());
                }
            }
        }
        return null;
    }

    private List<String> reconstruirCamino(Map<String, String> padres, String inicio, String fin) {
        List<String> camino = new ArrayList<>();
        String actual = fin;
        while (actual != null) {
            camino.add(0, actual);
            actual = padres.get(actual);
        }
        return camino;
    }

    public void guardarEnArchivo(String ruta) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            for (Nodo n : nodos.values()) {
                pw.println("N," + n.getId() + "," + n.getX() + "," + n.getY() + "," + n.isBloqueado());
            }
            for (Nodo n : nodos.values()) {
                for (Nodo v : n.getVecinos()) {
                    pw.println("A," + n.getId() + "," + v.getId());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarDesdeArchivo(String ruta) {
        nodos.clear();
        File file = new File(ruta);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String l;
            List<String[]> aristas = new ArrayList<>();
            while ((l = br.readLine()) != null) {
                String[] p = l.split(",");
                if (p[0].equals("N")) {
                    agregarNodo(p[1], Integer.parseInt(p[2]), Integer.parseInt(p[3]));
                    nodos.get(p[1]).setBloqueado(Boolean.parseBoolean(p[4]));
                } else if (p[0].equals("A")) {
                    aristas.add(p);
                }
            }
            for (String[] a : aristas) {
                Nodo o = nodos.get(a[1]);
                Nodo d = nodos.get(a[2]);
                if (o != null && d != null) {
                    o.agregarVecino(d);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Collection<Nodo> obtenerTodosLosNodos() {
        return nodos.values();
    }

    public void eliminarNodo(String id) {
        Nodo n = nodos.get(id);
        if (n == null) {
            return;
        }
        for (Nodo otro : nodos.values()) {
            otro.eliminarVecino(n);
        }
        nodos.remove(id);
    }
}
