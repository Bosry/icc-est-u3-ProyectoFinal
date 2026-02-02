package controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
        long startTime = System.nanoTime();

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

        while(!cola.isEmpty()){
            Nodo actual = cola.poll();

            if (actual.equals(destino)) {
                long endTime = System.nanoTime();
                registrarTiempoCVS("CVS", endTime - startTime);
                return reconstruir(padres, destino);
            }

            for (Nodo vecino : actual.vecinos) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    padres.put(vecino, actual);
                    cola.add(vecino);
                }
            }
        }

        return null;
    }

    private List<Nodo> reconstruir(Map<Nodo, Nodo> padres, Nodo destino){
        List<Nodo> ruta = new ArrayList<>();
        Nodo aux = destino;
        while (aux != null) { 
            ruta.add(0, aux);
            aux = padres.get(aux);
        }
        return ruta;
    }

    public void registrarTiempoCVS(String algoritmo, long tiempoNano){
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(algoritmo, true)))) {
            out.println(algoritmo + ", " + tiempoNano);
        } catch (IOException e) {
            System.err.println("Error al escribir CSV: " + e.getMessage());
        }
    }
}
