package controllers;

import java.util.*;
import models.Grafo;
import models.Nodo;

public class DFS {

    private long ultimoTiempo;

    public List<Nodo> buscarRuta(Grafo grafo, String inicioId, String finId) {
        long inicioTiempo = System.nanoTime();

        Nodo inicio = grafo.nodos.get(inicioId);
        Nodo fin = grafo.nodos.get(finId);

        if (inicio == null || fin == null) {
            return null;
        }
        if (inicio.isBloqueado() || fin.isBloqueado()) {
            return null;
        }

        Stack<Nodo> pila = new Stack<>();
        Set<Nodo> visitados = new HashSet<>();
        Map<Nodo, Nodo> padres = new HashMap<>();

        pila.push(inicio);

        while (!pila.isEmpty()) {
            Nodo actual = pila.pop();

            if (visitados.contains(actual)) {
                continue;
            }
            visitados.add(actual);

            if (actual.equals(fin)) {
                ultimoTiempo = System.nanoTime() - inicioTiempo;
                return reconstruir(padres, fin);
            }

            for (Nodo v : actual.vecinos) {
                if (!visitados.contains(v) && !v.isBloqueado()) {
                    padres.put(v, actual);
                    pila.push(v);
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
