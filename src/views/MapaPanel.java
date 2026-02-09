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
    private java.util.List<Nodo> seleccionados = new ArrayList<>();
    private java.util.List<Nodo> rutaVerde;
    private Nodo inicio, fin;
    private boolean esperandoRojos = false;
    private boolean modoBloqueoActivo = false;
    private boolean modoBorrarDireccion = false;
    private Set<Nodo> nodosParaBloquear = new HashSet<>();

    public MapaPanel(Grafo grafo) {
        this.grafo = grafo;
        try {
            mapa = ImageIO.read(new File("assets/mapa.png"));
            setPreferredSize(new Dimension(mapa.getWidth(), mapa.getHeight()));
        } catch (Exception e) {
            setPreferredSize(new Dimension(800, 600));
        }
        new javax.swing.Timer(50, e -> repaint()).start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Nodo n = obtenerNodoEn(e.getX(), e.getY());
                if (modoBloqueoActivo && n != null) {
                    if (nodosParaBloquear.contains(n)) nodosParaBloquear.remove(n);
                    else nodosParaBloquear.add(n);
                    return;
                }
                if (SwingUtilities.isRightMouseButton(e) && n != null) {
                    if (seleccionados.contains(n)) {
                        seleccionados.remove(n);
                    } else if (seleccionados.size() < 2) {
                        seleccionados.add(n);
                    }
                    return;
                }
                if (SwingUtilities.isLeftMouseButton(e) && esperandoRojos && n != null) {
                    if (inicio == null) inicio = n;
                    else if (fin == null && n != inicio) fin = n;
                }
            }
        });
    }

    public void aplicarCambiosBloqueo() {
        for (Nodo n : nodosParaBloquear) {
            n.setBloqueado(!n.isBloqueado());
        }
        nodosParaBloquear.clear();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (mapa != null) g2.drawImage(mapa, 0, 0, null);
        for (Nodo n : grafo.obtenerTodosLosNodos()) {
            for (Nodo v : n.getVecinos()) {
                Color colorArista = Color.GRAY;
                float grosor = 1.5f;
                if (modoBorrarDireccion && seleccionados.contains(n) && seleccionados.contains(v)) {
                    colorArista = Color.BLUE;
                    grosor = 3.0f;
                }

                float alpha = (n.isBloqueado() || v.isBloqueado()) ? 0.2f : 1.0f;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                dibujarFlecha(g2, n.getX(), n.getY(), v.getX(), v.getY(), colorArista, grosor);
            }
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        if (rutaVerde != null && rutaVerde.size() > 1) {
            for (int i = 0; i < rutaVerde.size() - 1; i++) {
                dibujarFlecha(g2, rutaVerde.get(i).getX(), rutaVerde.get(i).getY(), 
                             rutaVerde.get(i+1).getX(), rutaVerde.get(i+1).getY(), new Color(0, 200, 0), 3.5f);
            }
        }

        for (Nodo n : grafo.nodos.values()) {
            float alpha = 1.0f;
            if (modoBloqueoActivo) alpha = nodosParaBloquear.contains(n) ? 1.0f : 0.3f;
            else if (n.isBloqueado()) alpha = 0.4f;
            
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

            if (n == inicio || n == fin) g2.setColor(Color.RED);
            else if (seleccionados.contains(n)) g2.setColor(Color.GREEN);
            else g2.setColor(Color.BLACK);

            g2.fillOval(n.getX() - 8, n.getY() - 8, 16, 16);
            g2.setColor(Color.WHITE);
            g2.drawOval(n.getX() - 8, n.getY() - 8, 16, 16);
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 12));
            g2.drawString(n.getId(), n.getX() + 10, n.getY() + 5);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    private void dibujarFlecha(Graphics2D g2, int x1, int y1, int x2, int y2, Color c, float g) {
        double angle = Math.atan2(y2 - y1, x2 - x1);
        int offset = 6;
        int startX = (int) (x1 + offset * Math.sin(angle));
        int startY = (int) (y1 - offset * Math.cos(angle));
        int endX = (int) ((x2 + offset * Math.sin(angle)) - 14 * Math.cos(angle));
        int endY = (int) ((y2 - offset * Math.cos(angle)) - 14 * Math.sin(angle));

        g2.setStroke(new BasicStroke(g));
        g2.setColor(c);
        g2.drawLine(startX, startY, endX, endY);

        Polygon head = new Polygon();
        head.addPoint(endX, endY);
        head.addPoint((int)(endX - 8 * Math.cos(angle - 0.4)), (int)(endY - 8 * Math.sin(angle - 0.4)));
        head.addPoint((int)(endX - 8 * Math.cos(angle + 0.4)), (int)(endY - 8 * Math.sin(angle + 0.4)));
        g2.fill(head);
    }

    public void setModoBorrarDireccion(boolean b) { this.modoBorrarDireccion = b; }
    public void setModoBloqueo(boolean b) { this.modoBloqueoActivo = b; this.nodosParaBloquear.clear(); }
    public void setEsperandoRojos(boolean b) { this.esperandoRojos = b; }
    public void setRutaVerde(java.util.List<Nodo> r) { this.rutaVerde = r; }
    public java.util.List<Nodo> getNodosSeleccionados() { return seleccionados; }
    public void limpiarSeleccion() { seleccionados.clear(); }
    public void limpiarPuntosRuta() { inicio = null; fin = null; rutaVerde = null; }
    public Nodo getInicio() { return inicio; }
    public Nodo getFin() { return fin; }

    private Nodo obtenerNodoEn(int x, int y) {
        for (Nodo n : grafo.nodos.values()) {
            if (Math.hypot(x - n.getX(), y - n.getY()) < 15) return n;
        }
        return null;
    }
}