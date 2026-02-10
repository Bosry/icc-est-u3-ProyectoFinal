package models;

import java.util.ArrayList;
import java.util.List;

public class Nodo {

    private String id;
    private int x, y;
    private List<Nodo> vecinos;
    private boolean bloqueado = false;
    private boolean explorado = false;

    public Nodo(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.vecinos = new ArrayList<>();
    }

    public void agregarVecino(Nodo vecino) {
        if (!vecinos.contains(vecino)) {
            vecinos.add(vecino);
        }
    }

    public List<Nodo> getVecinos() {
        return vecinos;
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
        this.bloqueado = b;
    }

    public boolean isExplorado() {
        return explorado;
    }

    public void setExplorado(boolean b) {
        this.explorado = b;
    }
}
