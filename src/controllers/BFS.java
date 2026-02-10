package controllers;

import java.util.*;
import models.Grafo;
import models.Nodo;

public class BFS {

    public List<Nodo> buscar(Grafo grafo, String inicioId, String finId) {

        Nodo inicio = grafo.nodos.get(inicioId);
        Nodo fin = grafo.nodos.get(finId);

        if (inicio == null || fin == null) return null;
        if (inicio.isBloqueado() || fin.isBloqueado()) return null;

        Queue<Nodo> cola = new LinkedList<>();
        Map<Nodo, Nodo> padres = new HashMap<>();
        Set<Nodo> visitados = new HashSet<>();

        cola.add(inicio);
        visitados.add(inicio);

        while (!cola.isEmpty()) {

            Nodo actual = cola.poll();

            if (actual.equals(fin)) {
                return reconstruirRuta(padres, fin);
            }

            for (Nodo vecino : actual.getVecinos()) {
                if (!visitados.contains(vecino) && !vecino.isBloqueado()) {
                    visitados.add(vecino);
                    padres.put(vecino, actual);
                    cola.add(vecino);
                }
            }
        }
        return null;
    }

    private List<Nodo> reconstruirRuta(Map<Nodo, Nodo> padres, Nodo fin) {
        LinkedList<Nodo> ruta = new LinkedList<>();
        Nodo aux = fin;
        while (aux != null) {
            ruta.addFirst(aux);
            aux = padres.get(aux);
        }
        return ruta;
    }
}