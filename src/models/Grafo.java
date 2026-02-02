package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Grafo {
    public Map<String, Nodo> nodos = new HashMap<>();

    public void agregarNodo(String id, int x, int y){
        if (!nodos.containsKey(id)) {
            nodos.put(id, new Nodo(id, x, y));
        }
    }

    public void conectar(String a, String b){
        if (nodos.containsKey(a) && nodos.containsKey(b)) {
            nodos.get(a).conectar(nodos.get(b));
            nodos.get(b).conectar(nodos.get(a));
        }
    }

    public Collection<Nodo> obtenerTodosLosNodos(){
        return nodos.values();
    }

    public void guardarEnArchivo(String ruta){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
        for (Nodo n : nodos.values()) {
            bw.write("N," + n.getId() + "," + n.getX() + "," + n.getY());
            bw.newLine();
        }
        Set<String> conexionesGuardadas = new HashSet<>();
        for (Nodo n : nodos.values()) {
            for (Nodo vecino : n.vecinos) {
                String id1 = n.getId();
                String id2 = vecino.getId();
                String llave = id1.compareTo(id2) < 0 ? id1 + "-" + id2 : id2 + "-" + id1;

                if (!conexionesGuardadas.contains(llave)) {
                    bw.write("A," + id1 + "," + id2);
                    bw.newLine();
                    conexionesGuardadas.add(llave);
                }
            }
        }
        System.out.println("Grafo guardado exitosamente en: " + ruta);
    } catch (IOException e) {
        System.err.println("Error al guardar el grafo: " + e.getMessage());
    }
    }

    public void cargarDesdeArchivo(String ruta){
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes[0].equals("N")) {
                agregarNodo(partes[1], Integer.parseInt(partes[2]), Integer.parseInt(partes[3]));
            } else if (partes[0].equals("A")) {
                conectar(partes[1], partes[2]);
            }
        }
    } catch (IOException e) {
        System.err.println("Error al cargar grafo: " + e.getMessage());
    }
    }
}
