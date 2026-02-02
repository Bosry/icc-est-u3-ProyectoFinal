package models;

import java.util.ArrayList;
import java.util.List;

public class Nodo {
    private String id;
    private int x;
    private int y;
    private List<Nodo> vecinos;

    public Nodo(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.vecinos = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void conectar(Nodo n){
        if (!vecinos.contains(n)) {
            vecinos.add(n);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Nodo)) return false;
        Nodo nodo = (Nodo) o;
        return id.equals(nodo.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    @Override
    public String toString(){
        return id;
    }
}
