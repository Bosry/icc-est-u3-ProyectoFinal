package models;

import java.util.*;

public class Nodo {

    private String id;
    private int x, y;
    private boolean bloqueado = false;
    private boolean explorado = false;

    private List<Nodo> vecinos = new ArrayList<>();

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

    public void setBloqueado(boolean b) {
        bloqueado = b;
    }

    public boolean isExplorado() {
        return explorado;
    }

    public void setExplorado(boolean e) {
        this.explorado = e;
    }

    public List<Nodo> getVecinos() {
        return vecinos;
    }

    public void agregarVecino(Nodo n) {
        if (!vecinos.contains(n)) {
            vecinos.add(n);
        }
    }

    public void eliminarVecino(Nodo n) {
        vecinos.remove(n);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nodo)) {
            return false;
        }
        Nodo n = (Nodo) o;
        return id.equals(n.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
