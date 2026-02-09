package controllers;

import java.util.*;
import models.Grafo;
import models.Nodo;

public class DFS {

    public List<Nodo> buscarRuta(Grafo grafo, String inicioId, String finId) {

        Nodo inicio = grafo.nodos.get(inicioId);
        Nodo fin = grafo.nodos.get(finId);

        if (inicio == null || fin == null) return null;
        if (inicio.isBloqueado() || fin.isBloqueado()) return null;

        Stack<Nodo> pila = new Stack<>();
        Map<Nodo, Nodo> padres = new HashMap<>();
        Set<Nodo> visitados = new HashSet<>();

        pila.push(inicio);

        while (!pila.isEmpty()) {

            Nodo actual = pila.pop();

            if (visitados.contains(actual)) continue;
            visitados.add(actual);

            if (actual.equals(fin)) {
                return reconstruirRuta(padres, fin);
            }

            for (Nodo vecino : actual.getVecinos()) {
                if (!visitados.contains(vecino) && !vecino.isBloqueado()) {
                    padres.put(vecino, actual);
                    pila.push(vecino);
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