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
    public void eliminarNodo(String id) {
        Nodo n = nodos.get(id);
        if (n == null) return;
        for (Nodo otro : nodos.values()) {
            otro.getVecinos().remove(n); 
        }
        nodos.remove(id);
        System.out.println("Nodo " + id + " eliminado correctamente.");
    }

    public void limpiar() {
        nodos.clear();
    }

    public Collection<Nodo> obtenerTodosLosNodos() {
        return nodos.values();
    }

    public List<String> bfs(String inicioId, String finId) {
        if (!nodos.containsKey(inicioId) || !nodos.containsKey(finId)) return null;

        Queue<String> cola = new LinkedList<>();
        Map<String, String> padres = new HashMap<>();
        Set<String> visitados = new HashSet<>();

        cola.add(inicioId);
        visitados.add(inicioId);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            if (actual.equals(finId)) return reconstruirCamino(padres, inicioId, finId);

            Nodo nodoActual = nodos.get(actual);
            if (nodoActual.isBloqueado()) continue;

            for (Nodo vecino : nodoActual.getVecinos()) {
                if (!visitados.contains(vecino.getId()) && !vecino.isBloqueado()) {
                    visitados.add(vecino.getId());
                    padres.put(vecino.getId(), actual);
                    cola.add(vecino.getId());
                }
            }
        }
        return null;
    }

    public List<String> dfs(String inicioId, String finId) {
        if (!nodos.containsKey(inicioId) || !nodos.containsKey(finId)) return null;

        Stack<String> pila = new Stack<>();
        Map<String, String> padres = new HashMap<>();
        Set<String> visitados = new HashSet<>();

        pila.push(inicioId);

        while (!pila.isEmpty()) {
            String actual = pila.pop();
            if (actual.equals(finId)) return reconstruirCamino(padres, inicioId, finId);

            if (!visitados.contains(actual)) {
                visitados.add(actual);
                Nodo nodoActual = nodos.get(actual);
                if (nodoActual.isBloqueado()) continue;

                for (Nodo vecino : nodoActual.getVecinos()) {
                    if (!visitados.contains(vecino.getId()) && !vecino.isBloqueado()) {
                        padres.put(vecino.getId(), actual);
                        pila.push(vecino.getId());
                    }
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
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String l;
            List<String[]> aristasParaCargar = new ArrayList<>();

            while ((l = br.readLine()) != null) {
                String[] p = l.split(",");
                if (p[0].equals("N")) {
                    agregarNodo(p[1], Integer.parseInt(p[2]), Integer.parseInt(p[3]));
                    if (p.length > 4) {
                        nodos.get(p[1]).setBloqueado(Boolean.parseBoolean(p[4]));
                    }
                } else if (p[0].equals("A")) {
                    aristasParaCargar.add(p);
                }
            }
            for (String[] a : aristasParaCargar) {
                Nodo origen = nodos.get(a[1]);
                Nodo destino = nodos.get(a[2]);
                if (origen != null && destino != null) {
                    origen.agregarVecino(destino);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}