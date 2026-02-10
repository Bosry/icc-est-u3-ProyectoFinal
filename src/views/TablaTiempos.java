package views;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TablaTiempos extends JDialog {

    private DefaultTableModel modelo;

    public TablaTiempos(JFrame parent) {
        super(parent, "Tiempos BFS / DFS", true);
        setSize(400, 300);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        modelo = new DefaultTableModel();
        modelo.addColumn("Algoritmo");
        modelo.addColumn("Tiempo (ns)");

        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        cargarDatos();
    }

    private void cargarDatos() {
        cargarArchivo("BFS");
        cargarArchivo("DFS");
    }

    private void cargarArchivo(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                modelo.addRow(new Object[]{
                        partes[0].trim(),
                        partes[1].trim()
                });
            }
        } catch (IOException e) {
            // si no existe el archivo, no pasa nada
        }
    }
}
