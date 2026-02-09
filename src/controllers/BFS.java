package controllers;

import java.util.*;
import models.Grafo;
import models.Nodo;

public class BFS {

    private long ultimoTiempo;

    public List<Nodo> buscar(Grafo grafo, String inicioId, String finId) {
        long inicioTiempo = System.nanoTime();

        Nodo inicio = grafo.nodos.get(inicioId);
        Nodo fin = grafo.nodos.get(finId);

        if (inicio == null || fin == null) {
            return null;
        }
        if (inicio.isBloqueado() || fin.isBloqueado()) {
            return null;
        }

        Queue<Nodo> cola = new LinkedList<>();
        Set<Nodo> visitados = new HashSet<>();
        Map<Nodo, Nodo> padres = new HashMap<>();

        cola.add(inicio);
        visitados.add(inicio);

        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();

            if (actual.equals(fin)) {
                ultimoTiempo = System.nanoTime() - inicioTiempo;
                return reconstruir(padres, fin);
            }

            for (Nodo v : actual.vecinos) {
                if (!visitados.contains(v) && !v.isBloqueado()) {
                    visitados.add(v);
                    padres.put(v, actual);
                    cola.add(v);
                }
            }
        }
        return null;
    }

    private List<Nodo> reconstruir(Map<Nodo, Nodo> padres, Nodo fin) {
        LinkedList<Nodo> ruta = new LinkedList<>();
        Nodo aux = fin;
        while (aux != null) {
            ruta.addFirst(aux);
            aux = padres.get(aux);
        }
        return ruta;
    }

    public long getUltimoTiempo() {
        return ultimoTiempo;
    }
}
