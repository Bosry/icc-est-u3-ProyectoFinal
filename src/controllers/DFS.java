package controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import models.Grafo;
import models.Nodo;

public class DFS {

    public List<Nodo> buscarRuta(Grafo grafo, String inicioId, String finId) {
        long startTime = System.nanoTime();

        Nodo inicio = grafo.nodos.get(inicioId);
        Nodo destino = grafo.nodos.get(finId);

        if (inicio == null || destino == null) {
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

            if (actual.equals(destino)) {
                long endTime = System.nanoTime();
                registrarTiempoCVS("DFS", endTime - startTime);
                return reconstruirRuta(padres, destino);
            }

            for (Nodo vecino : actual.vecinos) {
                if (!visitados.contains(vecino)) {
                    padres.put(vecino, actual);
                    pila.push(vecino);
                }
            }
        }
        return null;
    }

    public List<Nodo> reconstruirRuta(Map<Nodo, Nodo> padres, Nodo fin) {
        LinkedList<Nodo> ruta = new LinkedList<>();
        Nodo aux = fin;
        while (aux != null) {
            ruta.addFirst(aux);
            aux = padres.get(aux);
        }
        return ruta;
    }

    public void registrarTiempoCVS(String algoritmo, long tiempoNano) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(algoritmo, true)))) {
            out.println(algoritmo + ", " + tiempoNano);
        } catch (IOException e) {
            System.err.println("Error al escribir CSV: " + e.getMessage());
        }
    }
}
