package views;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import models.Grafo;
import models.Nodo;

public class MapaPanel extends JPanel {

    private Grafo grafo;
    private BufferedImage mapa;

    private boolean modoBorrar = false;
    private boolean modoBloquear = false;
    private boolean modoBorrarDireccion = false;

    private Set<Nodo> seleccionados = new HashSet<>();
    private java.util.List<Nodo> direccionSeleccion = new ArrayList<>();

    private java.util.List<Nodo> ruta;
    private Nodo inicio, fin;

    public MapaPanel(Grafo grafo) {
        this.grafo = grafo;

        setFocusable(true);
        requestFocusInWindow();

        try {
            mapa = ImageIO.read(new File("assets/mapa.png"));
            setPreferredSize(new Dimension(mapa.getWidth(), mapa.getHeight()));
        } catch (Exception e) {
            setPreferredSize(new Dimension(800, 600));
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();

                Nodo n = obtenerNodoEn(e.getX(), e.getY());

                if (modoBorrarDireccion && n != null) {
                    if (!direccionSeleccion.contains(n)) direccionSeleccion.add(n);
                    else direccionSeleccion.remove(n);
                    repaint();
                    return;
                }

                if ((modoBorrar || modoBloquear) && n != null) {
                    alternarSeleccion(n);
                    repaint();
                    return;
                }

                if (!modoBorrar && !modoBloquear && !modoBorrarDireccion && SwingUtilities.isLeftMouseButton(e)) {
                    String id = "N" + (grafo.nodos.size() + 1);
                    grafo.agregarNodo(id, e.getX(), e.getY());
                    repaint();
                }
            }
        });
    }

    public void activarModoBorrar(boolean estado) {
        modoBorrar = estado;
        seleccionados.clear();
        repaint();
    }

    public void activarModoBloquear(boolean estado) {
        modoBloquear = estado;
        seleccionados.clear();
        repaint();
    }

    public void activarModoBorrarDireccion(boolean estado) {
        modoBorrarDireccion = estado;
        direccionSeleccion.clear();
        repaint();
    }

    public boolean isModoBorrarDireccion() { return modoBorrarDireccion; }
    public boolean hayNodosBorrar() { return !seleccionados.isEmpty(); }
    public boolean hayNodosBloquear() { return !seleccionados.isEmpty(); }
    public boolean hayDireccionSeleccionada() { return direccionSeleccion.size() == 2; }

    public void confirmarBorrado() {
        if (seleccionados.isEmpty()) return;
        for (Nodo n : new HashSet<>(seleccionados)) {
            grafo.eliminarNodo(n.getId());
        }
        seleccionados.clear();
        repaint();
    }

    public void confirmarBorradoDireccion() {
        if (direccionSeleccion.size() != 2) return;
        Nodo a = direccionSeleccion.get(0);
        Nodo b = direccionSeleccion.get(1);

        a.eliminarVecino(b);
        b.eliminarVecino(a);

        direccionSeleccion.clear();
        repaint();
    }

    public void confirmarBloqueo() {
        if (seleccionados.isEmpty()) return;
        for (Nodo n : new HashSet<>(seleccionados)) {
            n.setBloqueado(!n.isBloqueado());
        }
        seleccionados.clear();
        repaint();
    }

    public void setRuta(java.util.List<Nodo> ruta) {
        this.ruta = ruta;
        repaint();
    }

    public void setPuntos(Nodo i, Nodo f) {
        inicio = i;
        fin = f;
        repaint();
    }

    private void dibujarFlecha(Graphics2D g, int x1, int y1, int x2, int y2) {
        g.drawLine(x1, y1, x2, y2);
        double phi = Math.toRadians(25);
        int barb = 12;
        double dy = y2 - y1;
        double dx = x2 - x1;
        double theta = Math.atan2(dy, dx);
        for (int j = 0; j < 2; j++) {
            double rho = theta + (j == 0 ? phi : -phi);
            double x = x2 - barb * Math.cos(rho);
            double y = y2 - barb * Math.sin(rho);
            g.drawLine(x2, y2, (int) x, (int) y);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (mapa != null) g2.drawImage(mapa, 0, 0, null);

        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(2));
        for (Nodo n : grafo.obtenerTodosLosNodos()) {
            for (Nodo v : n.getVecinos()) {
                dibujarFlecha(g2, n.getX(), n.getY(), v.getX(), v.getY());
            }
        }

        if (ruta != null) {
            g2.setStroke(new BasicStroke(3));
            g2.setColor(Color.RED);
            for (int i = 0; i < ruta.size() - 1; i++) {
                Nodo a = ruta.get(i);
                Nodo b = ruta.get(i + 1);
                g2.drawLine(a.getX(), a.getY(), b.getX(), b.getY());
            }
        }

        if (direccionSeleccion.size() == 2) {
            Nodo a = direccionSeleccion.get(0);
            Nodo b = direccionSeleccion.get(1);
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(a.getX(), a.getY(), b.getX(), b.getY());
        }

        for (Nodo n : grafo.obtenerTodosLosNodos()) {
            if (seleccionados.contains(n)) g2.setColor(Color.GREEN);
            else if (direccionSeleccion.contains(n)) g2.setColor(Color.GREEN);
            else if (n.isBloqueado()) g2.setColor(Color.LIGHT_GRAY);
            else if (n.equals(inicio)) g2.setColor(Color.BLUE);
            else if (n.equals(fin)) g2.setColor(Color.RED);
            else g2.setColor(Color.BLACK);

            g2.fillOval(n.getX() - 6, n.getY() - 6, 12, 12);
            g2.setColor(Color.BLACK);
            g2.drawString(n.getId(), n.getX() + 8, n.getY());
        }
    }

    private Nodo obtenerNodoEn(int x, int y) {
        for (Nodo n : grafo.obtenerTodosLosNodos()) {
            int dx = x - n.getX();
            int dy = y - n.getY();
            if (dx * dx + dy * dy <= 100) return n;
        }
        return null;
    }

    private void alternarSeleccion(Nodo n) {
        if (seleccionados.contains(n)) seleccionados.remove(n);
        else seleccionados.add(n);
    }
}
