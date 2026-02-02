import controllers.BFS;
import controllers.DFS;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import models.Grafo;
import models.Nodo;
import views.MapaPanel;

public class App extends JFrame {
    private Grafo grafo;
    private MapaPanel mapaPanel;
    private BFS bfsController;
    private DFS dfsController;
    private JTextField txtInicio, txtDestino;
    private JButton btnBFS, btnDFS, btnLimpiar;

    public App() {
        grafo = new Grafo();
        grafo.cargarDesdeArchivo("assets/grafo.txt");

        bfsController = new BFS();
        dfsController = new DFS();

        setTitle("Simulador de Rutas - Mapa de Calles");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        mapaPanel = new MapaPanel(grafo);
        add(mapaPanel, BorderLayout.CENTER);
        pack();

        JPanel panelControl = new JPanel();
        panelControl.setLayout(new FlowLayout());

        txtInicio = new JTextField(8);
        txtDestino = new JTextField(8);
        btnBFS = new JButton("Ejecutar BFS");
        btnDFS = new JButton("Ejecutar DFS");
        btnLimpiar = new JButton("Limpiar");

        panelControl.add(new JLabel("Inicio (ID):"));
        panelControl.add(txtInicio);
        panelControl.add(new JLabel("Destino (ID):"));
        panelControl.add(txtDestino);
        panelControl.add(btnBFS);
        panelControl.add(btnDFS);
        panelControl.add(btnLimpiar);

        add(panelControl, BorderLayout.NORTH);

        configurarEventos();

        setLocationRelativeTo(null);
    }

    private void configurarEventos() {
        btnBFS.addActionListener(e -> {
            String inicio = txtInicio.getText().trim();
            String fin = txtDestino.getText().trim();
            
            List<Nodo> ruta = bfsController.buscar(grafo, inicio, fin);
            actualizarMapa(ruta, inicio, fin);
        });

        btnDFS.addActionListener(e -> {
            String inicio = txtInicio.getText().trim();
            String fin = txtDestino.getText().trim();
            
            List<Nodo> ruta = dfsController.buscarRuta(grafo, inicio, fin);
            actualizarMapa(ruta, inicio, fin);
        });

        btnLimpiar.addActionListener(e -> {
            mapaPanel.setRuta(null);
            mapaPanel.setPuntos(null, null);
            txtInicio.setText("");
            txtDestino.setText("");
        });
    }

    private void actualizarMapa(List<Nodo> ruta, String idInicio, String idFin) {
        if (ruta != null) {
            mapaPanel.setRuta(ruta);
            mapaPanel.setPuntos(grafo.nodos.get(idInicio), grafo.nodos.get(idFin));
            JOptionPane.showMessageDialog(this, "Ruta encontrada con éxito.");
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró una ruta o los IDs son inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new App().setVisible(true);
        });
    }
}
