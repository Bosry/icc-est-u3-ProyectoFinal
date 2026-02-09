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
    private JButton btnBFS, btnDFS, btnDireccional, btnBidireccional, btnBloquear, btnBorrarNodo, btnBorrarDir, btnLimpiar;
    private String modoActual = ""; 

    public App() {
        grafo = new Grafo();
        grafo.cargarDesdeArchivo("assets/grafo.txt");
        mapaPanel = new MapaPanel(grafo);

        setResizable(false);
        setTitle("Graph Editor Pro - BFS/DFS & Borrado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel pnl = new JPanel(new FlowLayout());
        btnBFS = new JButton("BFS");
        btnDFS = new JButton("DFS");
        btnDireccional = new JButton("Conectar Dir");
        btnBidireccional = new JButton("Conectar Bid");
        btnBloquear = new JButton("Bloquear");
        btnBorrarNodo = new JButton("Borrar Nodo");
        btnBorrarDir = new JButton("Borrar Dir");
        btnLimpiar = new JButton("Limpiar");

        pnl.add(btnBFS); pnl.add(btnDFS);
        pnl.add(new JSeparator(JSeparator.VERTICAL));
        pnl.add(btnDireccional); pnl.add(btnBidireccional);
        pnl.add(btnBloquear); pnl.add(btnBorrarNodo); pnl.add(btnBorrarDir);
        pnl.add(btnLimpiar);
        
        add(pnl, BorderLayout.NORTH);
        add(new JScrollPane(mapaPanel), BorderLayout.CENTER);

        Timer monitor = new Timer(100, e -> {
            if (modoActual.isEmpty()) {
                List<Nodo> sel = mapaPanel.getNodosSeleccionados();
                boolean hayDos = (sel.size() == 2);
                boolean hayAlgo = (!sel.isEmpty());

                btnDireccional.setEnabled(hayDos);
                btnBidireccional.setEnabled(hayDos);
                btnBorrarNodo.setEnabled(true); 
                btnBorrarDir.setEnabled(true);
                btnBFS.setEnabled(true);
                btnDFS.setEnabled(true);
                btnBloquear.setEnabled(true);
            }

            if ((modoActual.equals("BFS") || modoActual.equals("DFS")) 
                && mapaPanel.getInicio() != null && mapaPanel.getFin() != null) {
                
                String algoritmo = modoActual;
                mapaPanel.setEsperandoRojos(false); 
                procesarAlgoritmo(algoritmo);
            }
        });
        monitor.start();

        btnBorrarNodo.addActionListener(e -> {
            if (modoActual.equals("BORRAR_NODO")) {
                List<Nodo> sel = new ArrayList<>(mapaPanel.getNodosSeleccionados());
                if (!sel.isEmpty()) {
                    for (Nodo n : sel) grafo.eliminarNodo(n.getId());
                }
                finalizarModo();
            } else {
                iniciarModo("BORRAR_NODO", btnBorrarNodo, Color.RED);
            }
        });

        btnBorrarDir.addActionListener(e -> {
            if (modoActual.equals("BORRAR_DIR")) {
                List<Nodo> sel = mapaPanel.getNodosSeleccionados();
                if (sel.size() == 2) {
                    sel.get(0).eliminarVecino(sel.get(1));
                    sel.get(1).eliminarVecino(sel.get(0));
                }
                finalizarModo();
            } else {
                iniciarModo("BORRAR_DIR", btnBorrarDir, Color.CYAN);
                mapaPanel.setModoBorrarDireccion(true);
            }
        });

        btnBFS.addActionListener(e -> {
            if (modoActual.equals("BFS")) finalizarModo();
            else { iniciarModo("BFS", btnBFS, Color.YELLOW); mapaPanel.setEsperandoRojos(true); }
        });

        btnDFS.addActionListener(e -> {
            if (modoActual.equals("DFS")) finalizarModo();
            else { iniciarModo("DFS", btnDFS, Color.YELLOW); mapaPanel.setEsperandoRojos(true); }
        });

        btnBloquear.addActionListener(e -> {
            if (modoActual.equals("BLOQUEAR")) {
                mapaPanel.aplicarCambiosBloqueo();
                finalizarModo();
            } else { 
                iniciarModo("BLOQUEAR", btnBloquear, Color.ORANGE); 
                mapaPanel.setModoBloqueo(true); 
            }
        });

        btnDireccional.addActionListener(e -> {
            List<Nodo> sel = mapaPanel.getNodosSeleccionados();
            if (sel.size() == 2) {
                sel.get(1).eliminarVecino(sel.get(0));
                sel.get(0).agregarVecino(sel.get(1));
                grafo.guardarEnArchivo("assets/grafo.txt");
                mapaPanel.limpiarSeleccion();
            }
        });

        btnBidireccional.addActionListener(e -> {
            List<Nodo> sel = mapaPanel.getNodosSeleccionados();
            if (sel.size() == 2) {
                sel.get(0).agregarVecino(sel.get(1));
                sel.get(1).agregarVecino(sel.get(0));
                grafo.guardarEnArchivo("assets/grafo.txt");
                mapaPanel.limpiarSeleccion();
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
    }

    private void finalizarModo() {
        modoActual = "";
        setBotonesHabilitados(true);
        resetColoresBotones();
        mapaPanel.setModoBloqueo(false);
        mapaPanel.setModoBorrarDireccion(false);
        mapaPanel.setEsperandoRojos(false);
        mapaPanel.limpiarSeleccion();
        mapaPanel.limpiarPuntosRuta();
        grafo.guardarEnArchivo("assets/grafo.txt");
    }

    private void setBotonesHabilitados(boolean s) {
        btnBFS.setEnabled(s); btnDFS.setEnabled(s); btnBloquear.setEnabled(s);
        btnBorrarNodo.setEnabled(s); btnBorrarDir.setEnabled(s); btnLimpiar.setEnabled(s);
        btnDireccional.setEnabled(s); btnBidireccional.setEnabled(s);
    }

    private void resetColoresBotones() {
        btnBFS.setBackground(null); btnDFS.setBackground(null);
        btnBloquear.setBackground(null); btnBorrarNodo.setBackground(null);
        btnBorrarDir.setBackground(null);
    }

    private void procesarAlgoritmo(String tipo) {
        long startTime = System.nanoTime();
        List<String> ids = (tipo.equals("BFS")) ? 
            grafo.bfs(mapaPanel.getInicio().getId(), mapaPanel.getFin().getId()) : 
            grafo.dfs(mapaPanel.getInicio().getId(), mapaPanel.getFin().getId());
        long endTime = System.nanoTime();
        
        if (ids != null && !ids.isEmpty()) {
            List<Nodo> camino = new ArrayList<>();
            for (String id : ids) camino.add(grafo.nodos.get(id));
            mapaPanel.setRutaVerde(camino);

            double ms = (endTime - startTime) / 1_000_000.0;
            String[] col = {"Algoritmo", "Tiempo (ms)", "Nodos"};
            Object[][] data = {{ tipo, String.format("%.4f", ms), ids.size() }};
            
            JOptionPane.showMessageDialog(this, new JScrollPane(new JTable(new DefaultTableModel(data, col))), "Ruta Encontrada", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No hay camino disponible.");
        }
        
        modoActual = "";
        setBotonesHabilitados(true);
        resetColoresBotones();
        mapaPanel.setEsperandoRojos(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}