package controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import models.Grafo;
import models.Nodo;

public class BFS {
    public List<Nodo> buscar(Grafo grafo, String inicioId, String finId){
        Nodo inicio = grafo.nodos.get(inicioId);
        Nodo destino = grafo.nodos.get(finId);

        if (inicio == null || destino == null) {
            return null;
        }

        Queue<Nodo> cola = new LinkedList<>();
        Set<Nodo> visitados = new HashSet<>();
        Map<Nodo, Nodo> padres = new HashMap<>();

        cola.add(inicio);
        visitados.add(inicio);

        

        return null;
    }
}
