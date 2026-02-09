package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Nodo {

    private String id;
    private int x;
    private int y;
    private boolean bloqueado = false;

    private List<Nodo> vecinos = new ArrayList<>();
    private List<Nodo> vecinosSalientes = new ArrayList<>();

    public Nodo(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public String getId() { return id; }
    public int getX() { return x; }
    public int getY() { return y; }

    public boolean isBloqueado() { return bloqueado; }
    public void setBloqueado(boolean b) { bloqueado = b; }

    public List<Nodo> getVecinos() {
        List<Nodo> todos = new ArrayList<>(vecinos);
        for (Nodo n : vecinosSalientes) {
            if (!todos.contains(n)) todos.add(n);
        }
        return todos;
    }

    public List<Nodo> getVecinosSalientes() {
        return vecinosSalientes;
    }

    public void agregarVecino(Nodo n) {
        if (!vecinos.contains(n)) vecinos.add(n);
    }

    public void eliminarVecino(Nodo n) {
        vecinos.remove(n);
        vecinosSalientes.remove(n);
    }

    public void conectarA(Nodo n) {
        if (!vecinosSalientes.contains(n)) vecinosSalientes.add(n);
    }

    public void desconectarA(Nodo n) {
        vecinosSalientes.remove(n);
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Nodo)) return false;
        Nodo n = (Nodo) o;
        return id.equals(n.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}