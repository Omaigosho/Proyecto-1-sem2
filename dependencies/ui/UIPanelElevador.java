package dependencies.ui;

import dependencies.classes.*;
import javax.swing.*;
import java.awt.*;

public class UIPanelElevador extends JPanel {
    private Elevator elevador;
    private ConfigSimulador config;
    private ElevatorManager manager;
    private JLabel lblTitulo;
    private JLabel lblPiso;
    private JLabel lblDireccion;
    private JLabel lblCola;

    public UIPanelElevador(Elevator e, ConfigSimulador config, ElevatorManager manager) {
        this.elevador = e;
        this.config = config;
        this.manager = manager;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(250, 252, 255));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        lblTitulo = new JLabel("Elevador " + e.getId(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
        lblTitulo.setForeground(new Color(25, 25, 112));
        add(lblTitulo);

        add(Box.createVerticalStrut(10));

        lblPiso = new JLabel("Piso actual: " + e.getPisoActual(), SwingConstants.CENTER);
        lblPiso.setAlignmentX(CENTER_ALIGNMENT);
        lblDireccion = new JLabel("Dirección: " + e.getDireccion(), SwingConstants.CENTER);
        lblDireccion.setAlignmentX(CENTER_ALIGNMENT);
        lblCola = new JLabel("Cola: " + e.getColaComandos().toString(), SwingConstants.CENTER);
        lblCola.setAlignmentX(CENTER_ALIGNMENT);

        add(lblPiso);
        add(lblDireccion);
        add(lblCola);

        add(Box.createVerticalStrut(10));
        add(new JSeparator());

        // Botones de pisos (de mayor a menor)
        for (int i = config.getNumPisos(); i >= 1; i--) {
            int piso = i;
            JButton btn = new JButton("Ir al piso " + piso);
            btn.setAlignmentX(CENTER_ALIGNMENT);
            btn.setBackground(new Color(70, 130, 180));
            btn.setForeground(Color.BLACK);
            btn.setFocusPainted(false);
            btn.setFont(new Font("SansSerif", Font.BOLD, 13));

            btn.addActionListener(ev -> {
                e.agregarComando(piso);
                lblCola.setText("Cola: " + e.getColaComandos().toString());
            });

            add(Box.createVerticalStrut(5));
            add(btn);
        }

        add(Box.createVerticalGlue());
    }

    public void actualizar() {
        lblPiso.setText("Piso actual: " + elevador.getPisoActual());
        lblDireccion.setText("Dirección: " + elevador.getDireccion());
        lblCola.setText("Cola: " + elevador.getColaComandos().toString());
    }
}










