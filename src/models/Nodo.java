package models;

import java.util.ArrayList;
import java.util.List;

public class Nodo {

    private String id;
    private int x;
    private int y;
    private boolean bloqueado = false;

    public List<Nodo> vecinos = new ArrayList<>();

    public Nodo(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public void conectar(Nodo n) {
        if (!vecinos.contains(n)) {
            vecinos.add(n);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nodo)) {
            return false;
        }
        return id.equals(((Nodo) o).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return id;
    }
}
