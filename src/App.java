
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.Grafo;
import models.Nodo;
import views.MapaPanel;

public class App extends JFrame {

    private Grafo grafo;
    private MapaPanel mapaPanel;
    private JButton btnBFS, btnDFS, btnDireccional, btnBidireccional, btnBloquear, btnBorrarNodo, btnLimpiar;
    private String modoActual = "";

    public App() {
        grafo = new Grafo();
        grafo.cargarDesdeArchivo("assets/grafo.txt");
        mapaPanel = new MapaPanel(grafo);

        setResizable(false);
        setTitle("Simulador de Rutas UPS - Proyecto Final");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel pnl = new JPanel(new FlowLayout());
        btnBFS = new JButton("BFS");
        btnDFS = new JButton("DFS");
        btnDireccional = new JButton("Conectar Dir");
        btnBidireccional = new JButton("Conectar Bid");
        btnBloquear = new JButton("Bloquear");
        btnBorrarNodo = new JButton("Borrar Nodo");
        btnLimpiar = new JButton("Limpiar");

        btnDireccional.setEnabled(false);
        btnBidireccional.setEnabled(false);

        pnl.add(btnBFS);
        pnl.add(btnDFS);
        pnl.add(new JSeparator(JSeparator.VERTICAL));
        pnl.add(btnDireccional);
        pnl.add(btnBidireccional);
        pnl.add(btnBloquear);
        pnl.add(btnBorrarNodo);
        pnl.add(btnLimpiar);

        add(pnl, BorderLayout.NORTH);
        add(new JScrollPane(mapaPanel), BorderLayout.CENTER);

        Timer monitor = new Timer(100, e -> {
            List<Nodo> sel = mapaPanel.getNodosSeleccionados();
            if (sel.size() == 2 && modoActual.isEmpty()) {
                modoActual = "CONECTANDO";
                setBotonesHabilitados(false);
                btnDireccional.setEnabled(true);
                btnBidireccional.setEnabled(true);
                btnLimpiar.setEnabled(true);
            }
            if ((modoActual.equals("BFS") || modoActual.equals("DFS"))
                    && mapaPanel.getInicio() != null && mapaPanel.getFin() != null) {
                String alg = modoActual;
                modoActual = "PROCESANDO";
                procesarAlgoritmo(alg);
            }
        });
        monitor.start();

        btnBFS.addActionListener(e -> iniciarModo("BFS", btnBFS, Color.YELLOW));
        btnDFS.addActionListener(e -> iniciarModo("DFS", btnDFS, Color.YELLOW));
        btnBloquear.addActionListener(e -> {
            if (modoActual.equals("BLOQUEAR")) {
                finalizarModo(); 
            }else {
                iniciarModo("BLOQUEAR", btnBloquear, Color.ORANGE);
                mapaPanel.setModoBloqueo(true);
            }
        });
        btnBorrarNodo.addActionListener(e -> {
            if (modoActual.equals("BORRANDO")) {
                List<Nodo> sel = new ArrayList<>(mapaPanel.getNodosSeleccionados());
                for (Nodo n : sel) {
                    grafo.eliminarNodo(n.getId());
                }
                grafo.guardarEnArchivo("assets/grafo.txt");
                finalizarModo();
            } else {
                iniciarModo("BORRANDO", btnBorrarNodo, Color.RED);
                JOptionPane.showMessageDialog(this, "Seleccione nodos y pulse 'Borrar Nodo' para confirmar.");
            }
        });
        btnDireccional.addActionListener(e -> {
            List<Nodo> sel = mapaPanel.getNodosSeleccionados();
            if (sel.size() == 2) {
                sel.get(0).agregarVecino(sel.get(1));
                grafo.guardarEnArchivo("assets/grafo.txt");
                finalizarModo();
            }
        });
        btnBidireccional.addActionListener(e -> {
            List<Nodo> sel = mapaPanel.getNodosSeleccionados();
            if (sel.size() == 2) {
                sel.get(0).agregarVecino(sel.get(1));
                sel.get(1).agregarVecino(sel.get(0));
                grafo.guardarEnArchivo("assets/grafo.txt");
                finalizarModo();
            }
        });
        btnLimpiar.addActionListener(e -> finalizarModo());
        pack();
        setLocationRelativeTo(null);
    }

    private void iniciarModo(String modo, JButton boton, Color color) {
        modoActual = modo;
        setBotonesHabilitados(false);
        boton.setEnabled(true);
        boton.setBackground(color);
        mapaPanel.limpiarPuntosRuta();
        if (modo.equals("BFS") || modo.equals("DFS")) {
            mapaPanel.setEsperandoRojos(true);
        }
    }

    private void finalizarModo() {
        modoActual = "";
        resetColoresBotones();
        mapaPanel.setModoBloqueo(false);
        mapaPanel.setEsperandoRojos(false);
        mapaPanel.limpiarSeleccion();
        mapaPanel.limpiarPuntosRuta();
        setBotonesHabilitados(true);
    }

    private void setBotonesHabilitados(boolean s) {
        btnBFS.setEnabled(s);
        btnDFS.setEnabled(s);
        btnBloquear.setEnabled(s);
        btnBorrarNodo.setEnabled(s);
        btnLimpiar.setEnabled(s);
        btnDireccional.setEnabled(false);
        btnBidireccional.setEnabled(false);
    }

    private void resetColoresBotones() {
        btnBFS.setBackground(null);
        btnDFS.setBackground(null);
        btnBloquear.setBackground(null);
        btnBorrarNodo.setBackground(null);
    }

    private void procesarAlgoritmo(String tipo) {
        List<String> explorados = new ArrayList<>();
        long startTime = System.nanoTime();
        List<String> idsRuta = (tipo.equals("BFS"))
                ? grafo.bfs(mapaPanel.getInicio().getId(), mapaPanel.getFin().getId(), explorados)
                : grafo.dfs(mapaPanel.getInicio().getId(), mapaPanel.getFin().getId(), explorados);
        long endTime = System.nanoTime();
        double ms = (endTime - startTime) / 1_000_000.0;

        if (idsRuta != null) {
            grafo.registrarTiempoCSV(tipo, ms, idsRuta.size());
            new Thread(() -> {
                try {
                    for (String id : explorados) {
                        grafo.nodos.get(id).setExplorado(true);
                        Thread.sleep(100);
                    }
                    List<Nodo> camino = new ArrayList<>();
                    for (String id : idsRuta) {
                        camino.add(grafo.nodos.get(id));
                    }
                    mapaPanel.setRutaVerde(camino);
                    SwingUtilities.invokeLater(() -> {
                        String[] col = {"Métrica", "Valor"};
                        Object[][] data = {
                            {"Algoritmo", tipo},
                            {"Tiempo", String.format("%.4f ms", ms)},
                            {"Nodos Ruta", idsRuta.size()},
                            {"Nodos Explorados", explorados.size()}
                        };
                        DefaultTableModel model = new DefaultTableModel(data, col);
                        JTable tabla = new JTable(model);
                        tabla.setRowHeight(25);
                        tabla.setPreferredScrollableViewportSize(tabla.getPreferredSize());
                        tabla.setFillsViewportHeight(true);
                        JScrollPane scroll = new JScrollPane(tabla);
                        scroll.setPreferredSize(new Dimension(300, tabla.getRowHeight() * (data.length + 1) + 5));
                        JOptionPane.showMessageDialog(this, scroll, "Reporte de Rendimiento", JOptionPane.INFORMATION_MESSAGE);
                        finalizarModo();
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).start();
        } else {
            JOptionPane.showMessageDialog(this, "No hay camino.");
            finalizarModo();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}
