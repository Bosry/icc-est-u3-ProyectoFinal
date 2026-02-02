package models;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    public void guardarEnArchivo(){

    }

    public void cargarDesdeArchivo(){

    }
}
