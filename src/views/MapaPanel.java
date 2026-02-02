package views;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import models.Grafo;
import models.Nodo;

public class MapaPanel extends JPanel {

    private BufferedImage mapaImagen;
    private Grafo grafo;
    private List<Nodo> ruta;
    private Nodo nodoInicio;
    private Nodo nodoFin;

    public MapaPanel(Grafo grafo) {
        this.grafo = grafo;
        try {
            this.mapaImagen = ImageIO.read(new File("assets/mapa.png"));
            if (mapaImagen != null) {
                Dimension size = new Dimension(mapaImagen.getWidth(), mapaImagen.getHeight());
                setPreferredSize(size);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (javax.swing.SwingUtilities.isLeftMouseButton(e)) {
                    int x = e.getX();
                    int y = e.getY();
                    String id = "N" + (grafo.obtenerTodosLosNodos().size() + 1);

                    grafo.agregarNodo(id, x, y);
                    grafo.guardarEnArchivo("assets/grafo.txt");
                    repaint();
                    System.out.println("Nodo creado: " + id);

                } else if (javax.swing.SwingUtilities.isRightMouseButton(e)) {
                    String id1 = javax.swing.JOptionPane.showInputDialog("ID Nodo Origen (ej: N1):");
                    String id2 = javax.swing.JOptionPane.showInputDialog("ID Nodo Destino (ej: N2):");

                    if (id1 != null && id2 != null) {
                        grafo.conectar(id1, id2);
                        grafo.guardarEnArchivo("assets/grafo.txt");
                        repaint();
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (mapaImagen != null) {
            g.drawImage(mapaImagen, 0, 0, this.getWidth(), this.getHeight(), null);
        }

        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(2));
        for (Nodo n : grafo.obtenerTodosLosNodos()) {
            for (Nodo vecino : n.vecinos) {
                g2.drawLine(n.getX(), n.getY(), vecino.getX(), vecino.getY());
            }
        }
        if (ruta != null && !ruta.isEmpty()) {
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(5));
            for (int i = 0; i < ruta.size() - 1; i++) {
                Nodo actual = ruta.get(i);
                Nodo siguiente = ruta.get(i + 1);
                g2.drawLine(actual.getX(), actual.getY(), siguiente.getX(), siguiente.getY());
            }
        }
        for (Nodo n : grafo.obtenerTodosLosNodos()) {
            if (n.equals(nodoInicio)) {
                g2.setColor(Color.GREEN);
            } else if (n.equals(nodoFin)) {
                g2.setColor(Color.BLUE);
            } else {
                g2.setColor(Color.BLACK);
            }

            g2.fillOval(n.getX() - 6, n.getY() - 6, 12, 12);
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 10));
            g2.drawString(n.getId(), n.getX() + 8, n.getY());
        }
    }

    public void setRuta(List<Nodo> ruta) {
        this.ruta = ruta;
        repaint();
    }

    public void setPuntos(Nodo inicio, Nodo fin) {
        this.nodoInicio = inicio;
        this.nodoFin = fin;
        repaint();
    }
}
