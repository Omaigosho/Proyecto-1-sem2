package dependencies.ui;

import dependencies.classes.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;

public class ElevatorUI extends JFrame {
    private final ElevatorManager manager;
    private final ConfigSimulador config;
    private final JTextArea statusArea;

    public ElevatorUI(ElevatorManager manager, ConfigSimulador config) {
        this.manager = manager;
        this.config = config;

        setTitle("Simulador de Elevadores");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        statusArea = new JTextArea();
        statusArea.setEditable(false);
        add(new JScrollPane(statusArea), BorderLayout.CENTER);

        JPanel controls = new JPanel();
        JTextField pisoField = new JTextField(5);
        JComboBox<String> direccionBox = new JComboBox<>(new String[]{"up", "down"});
        JButton callBtn = new JButton("Llamar elevador");
        JButton resetBtn = new JButton("Reset sistema");
        JButton uploadBtn = new JButton("Cargar requerimientos");

        callBtn.addActionListener((ActionEvent e) -> {
            int piso = Integer.parseInt(pisoField.getText());
            String dir = (String) direccionBox.getSelectedItem();
            manager.agregarRequest(new ComandoElevador(piso, dir));
        });

        resetBtn.addActionListener(e -> manager.resetSistema());

        uploadBtn.addActionListener(e -> {
            List<ComandoElevador> cmds = CargaPeticiones.cargarDesdeArchivo("requests.txt");
            for (ComandoElevador c : cmds) {
                manager.agregarRequest(c);
            }
        });

        controls.add(new JLabel("Piso:"));
        controls.add(pisoField);
        controls.add(new JLabel("Dirección:"));
        controls.add(direccionBox);
        controls.add(callBtn);
        controls.add(resetBtn);
        controls.add(uploadBtn);
        add(controls, BorderLayout.SOUTH);

        
        JPanel panelElevadores = new JPanel();
        panelElevadores.setLayout(new GridLayout(1, manager.getElevadores().size()));
        for (Elevator e : manager.getElevadores()) {
            panelElevadores.add(new UIPanelElevador(e, config.getNumPisos()));
        }
        add(panelElevadores, BorderLayout.EAST);

        new Timer(1000, e -> actualizarEstado()).start();
    }

    private void actualizarEstado() {
        StringBuilder sb = new StringBuilder();
        for (Elevator e : manager.getElevadores()) {
            sb.append("Elevador ").append(e.getId())
              .append(" | Piso: ").append(e.getPisoActual())
              .append(" | Dir: ").append(e.getDireccion())
              .append(" | Cola: ").append(e.getColaComandos()).append("\n");
        }
        statusArea.setText(sb.toString());
    }
}
