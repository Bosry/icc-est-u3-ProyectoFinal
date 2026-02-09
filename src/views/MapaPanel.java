package views;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import models.Grafo;
import models.Nodo;

public class MapaPanel extends JPanel {

    private Grafo grafo;
    private BufferedImage mapa;
    private List<Nodo> ruta;
    private Nodo inicio, fin;
    private boolean modoBorrar = false;
    private boolean modoBloquear = false;
    private Set<Nodo> nodosBorrar = new HashSet<>();
    private Set<Nodo> nodosBloquear = new HashSet<>();
    private Nodo nodoConexion = null;

    public MapaPanel(Grafo grafo) {
        this.grafo = grafo;

        try {
            mapa = ImageIO.read(new File("assets/mapa.png"));
            setPreferredSize(new Dimension(mapa.getWidth(), mapa.getHeight()));
        } catch (Exception e) {
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                Nodo n = obtenerNodo(e.getX(), e.getY());

                if (modoBorrar && n != null) {
                    if (!nodosBorrar.add(n)) {
                        nodosBorrar.remove(n);
                    }
                    repaint();
                    return;
                }

                if (modoBloquear && n != null) {
                    if (!nodosBloquear.add(n)) {
                        nodosBloquear.remove(n);
                    }
                    repaint();
                    return;
                }

                if (SwingUtilities.isRightMouseButton(e) && n != null) {
                    if (nodoConexion == null) {
                        nodoConexion = n;
                    } else {
                        grafo.conectar(nodoConexion.getId(), n.getId());
                        nodoConexion = null;
                        grafo.guardarEnArchivo("assets/grafo.txt");
                        repaint();
                    }
                    return;
                }

                if (SwingUtilities.isLeftMouseButton(e) && n == null) {
                    String id = "N" + (grafo.nodos.size() + 1);
                    grafo.agregarNodo(id, e.getX(), e.getY());
                    grafo.guardarEnArchivo("assets/grafo.txt");
                    repaint();
                }
            }
        });
    }

    private Nodo obtenerNodo(int x, int y) {
        for (Nodo n : grafo.obtenerTodosLosNodos()) {
            if (Math.hypot(n.getX() - x, n.getY() - y) <= 8) {
                return n;
            }
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(mapa, 0, 0, null);

        g2.setColor(Color.DARK_GRAY);
        for (Nodo n : grafo.obtenerTodosLosNodos()) {
            for (Nodo v : n.vecinos) {
                g2.drawLine(n.getX(), n.getY(), v.getX(), v.getY());
            }
        }

        if (ruta != null) {
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(4));
            for (int i = 0; i < ruta.size() - 1; i++) {
                g2.drawLine(
                        ruta.get(i).getX(), ruta.get(i).getY(),
                        ruta.get(i + 1).getX(), ruta.get(i + 1).getY()
                );
            }
        }

        for (Nodo n : grafo.obtenerTodosLosNodos()) {

            if (modoBorrar) {
                g2.setColor(nodosBorrar.contains(n) ? Color.RED : Color.LIGHT_GRAY);
            } else if (modoBloquear) {
                g2.setColor(nodosBloquear.contains(n) ? Color.ORANGE : Color.LIGHT_GRAY);
            } else if (n.isBloqueado()) {
                g2.setColor(Color.GRAY);
            } else if (n.equals(inicio)) {
                g2.setColor(Color.GREEN);
            } else if (n.equals(fin)) {
                g2.setColor(Color.BLUE);
            } else {
                g2.setColor(Color.BLACK);
            }

            g2.fillOval(n.getX() - 6, n.getY() - 6, 12, 12);
            g2.drawString(n.getId(), n.getX() + 8, n.getY());
        }
    }

    public void setRuta(List<Nodo> r) {
        ruta = r;
        repaint();
    }

    public void setPuntos(Nodo i, Nodo f) {
        inicio = i;
        fin = f;
        repaint();
    }

    public void activarModoBorrar(boolean b) {
        modoBorrar = b;
        nodosBorrar.clear();
        repaint();
    }

    public void activarModoBloquear(boolean b) {
        modoBloquear = b;
        nodosBloquear.clear();
        repaint();
    }

    public boolean hayNodosBorrar() {
        return !nodosBorrar.isEmpty();
    }

    public boolean hayNodosBloquear() {
        return !nodosBloquear.isEmpty();
    }

    public void confirmarBorrado() {
        for (Nodo n : new HashSet<>(nodosBorrar)) {
            grafo.eliminarNodo(n.getId());
        }
        nodosBorrar.clear();
        grafo.guardarEnArchivo("assets/grafo.txt");
        repaint();
    }

    public void confirmarBloqueo() {
        for (Nodo n : nodosBloquear) {
            n.setBloqueado(true);
        }
        nodosBloquear.clear();
        grafo.guardarEnArchivo("assets/grafo.txt");
        repaint();
    }
}
