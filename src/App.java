import controllers.BFS;
import controllers.DFS;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import models.Grafo;
import models.Nodo;
import views.MapaPanel;
import views.TablaTiempos;

public class App extends JFrame {

    private Grafo grafo;
    private MapaPanel mapaPanel;

    private BFS bfs;
    private DFS dfs;

    private JTextField txtInicio, txtDestino;
    private JButton btnBFS, btnDFS, btnTabla;
    private JButton btnBorrar, btnBloquear, btnLimpiar;

    private boolean modoBorrar = false;
    private boolean modoBloquear = false;

    public App() {

        grafo = new Grafo();
        grafo.cargarDesdeArchivo("assets/grafo.txt");

        bfs = new BFS();
        dfs = new DFS();

        setTitle("Simulador BFS / DFS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        mapaPanel = new MapaPanel(grafo);
        add(mapaPanel, BorderLayout.CENTER);

        JPanel top = new JPanel(new FlowLayout());

        txtInicio = new JTextField(6);
        txtDestino = new JTextField(6);
        btnBFS = new JButton("BFS");
        btnDFS = new JButton("DFS");
        btnTabla = new JButton("Tabla tiempos");
        btnBorrar = new JButton("Borrar");
        btnBloquear = new JButton("Bloquear");
        btnLimpiar = new JButton("Limpiar");

        top.add(new JLabel("Inicio:"));
        top.add(txtInicio);
        top.add(new JLabel("Destino:"));
        top.add(txtDestino);

        top.add(btnBFS);
        top.add(btnDFS);
        top.add(btnTabla);
        top.add(btnBorrar);
        top.add(btnBloquear);
        top.add(btnLimpiar);

        add(top, BorderLayout.NORTH);

        configurarEventos();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                grafo.guardarEnArchivo("assets/grafo.txt");
            }
        });
    }

    private void configurarEventos() {

        btnBFS.addActionListener(e -> ejecutarBusqueda(true));
        btnDFS.addActionListener(e -> ejecutarBusqueda(false));

        btnTabla.addActionListener(e ->
            new TablaTiempos(this).setVisible(true)
        );

        btnLimpiar.addActionListener(e -> {
            grafo.limpiar();
            mapaPanel.setRuta(null);
            mapaPanel.setPuntos(null, null);
            grafo.guardarEnArchivo("assets/grafo.txt");
            mapaPanel.repaint();
        });

        btnBorrar.addActionListener(e -> {

            modoBorrar = !modoBorrar;

            if (modoBorrar) {
                bloquearBotones(true);
                mapaPanel.activarModoBorrar(true);
                btnBorrar.setText("Confirmar borrar");
            } else {
                mapaPanel.activarModoBorrar(false);

                if (mapaPanel.hayNodosBorrar()) {
                    mapaPanel.confirmarBorrado();
                }

                bloquearBotones(false);
                btnBorrar.setText("Borrar");
            }
        });

        btnBloquear.addActionListener(e -> {

            modoBloquear = !modoBloquear;

            if (modoBloquear) {
                bloquearBotones(true);
                mapaPanel.activarModoBloquear(true);
                btnBloquear.setText("Confirmar bloqueo");
            } else {
                mapaPanel.activarModoBloquear(false);

                if (mapaPanel.hayNodosBloquear()) {
                    mapaPanel.confirmarBloqueo();
                }

                bloquearBotones(false);
                btnBloquear.setText("Bloquear");
            }
        });
    }
    private void ejecutarBusqueda(boolean esBFS) {

        String ini = txtInicio.getText().trim();
        String fin = txtDestino.getText().trim();

        if (!grafo.nodos.containsKey(ini) || !grafo.nodos.containsKey(fin)) {
            JOptionPane.showMessageDialog(this, "IDs inválidos");
            return;
        }

        List<Nodo> ruta = esBFS
                ? bfs.buscar(grafo, ini, fin)
                : dfs.buscarRuta(grafo, ini, fin);

        if (ruta == null) {
            JOptionPane.showMessageDialog(this, "No se encontró ruta");
        } else {
            mapaPanel.setRuta(ruta);
            mapaPanel.setPuntos(
                    grafo.nodos.get(ini),
                    grafo.nodos.get(fin)
            );
        }
    }

    private void bloquearBotones(boolean bloquear) {
        btnBFS.setEnabled(!bloquear);
        btnDFS.setEnabled(!bloquear);
        btnTabla.setEnabled(!bloquear);
        btnLimpiar.setEnabled(!bloquear);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}