package dependencies.ui;

<<<<<<< Updated upstream
import dependencies.classes.Elevator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class UIPanelElevador extends JPanel {
    public UIPanelElevador(Elevator e, int numPisos) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        add(new JLabel("Panel Elevador " + e.getId()));
=======
import dependencies.classes.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class UIPanelElevador extends JPanel {
    private Elevator elevador;
    private JLabel lblTitulo;
    private JLabel lblPiso;
    private JLabel lblCola;
>>>>>>> Stashed changes

    public UIPanelElevador(Elevator e, ConfigSimulador config, ElevatorManager manager) {
        this.elevador = e;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(250, 252, 255));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Título
        lblTitulo = new JLabel("Elevador " + e.getId(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
        lblTitulo.setForeground(new Color(25, 25, 112));
        add(lblTitulo);

        add(Box.createVerticalStrut(10));

        // Información del elevador
        lblPiso = new JLabel("Piso actual: " + e.getPisoActual(), SwingConstants.CENTER);
        lblPiso.setAlignmentX(CENTER_ALIGNMENT);
        lblCola = new JLabel("Cola: " + e.getColaComandos().toString(), SwingConstants.CENTER);
        lblCola.setAlignmentX(CENTER_ALIGNMENT);
        add(lblPiso);
        add(lblCola);

        add(Box.createVerticalStrut(10));
        add(new JSeparator());

        // Botones para cada piso (de mayor a menor)
        for (int i = config.getNumPisos(); i >= 1; i--) {
            int piso = i;
            JButton btn = new JButton("Piso " + piso);
<<<<<<< Updated upstream

            ActionListener pisoListener = (ActionEvent ev) -> e.agregarComando(piso);
            btn.addActionListener(pisoListener);

=======
            btn.setAlignmentX(CENTER_ALIGNMENT);
            btn.setBackground(new Color(70, 130, 180));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new Font("SansSerif", Font.BOLD, 13));

            ActionListener pisoListener = (ActionEvent ev) -> {
                e.agregarComando(piso);
                lblCola.setText("Cola: " + e.getColaComandos().toString());
            };

            btn.addActionListener(pisoListener);
            add(Box.createVerticalStrut(5));
>>>>>>> Stashed changes
            add(btn);
        }

        add(Box.createVerticalStrut(10));
        add(new JSeparator());

        // --- NUEVO BOTÓN: Upload lista de requerimientos ---
        JButton uploadBtn = new JButton("Upload lista de requerimientos");
        uploadBtn.setAlignmentX(CENTER_ALIGNMENT);
        uploadBtn.setBackground(new Color(60, 179, 113));
        uploadBtn.setForeground(Color.WHITE);
        uploadBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        uploadBtn.setFocusPainted(false);

        uploadBtn.addActionListener(evn -> {
            // Solo permite cargar si el sistema está en RESET
            if (manager.estaEnReset()) {
                try {
                    List<ComandoElevador> cmds = CargaPeticiones.cargarDesdeArchivo("requests.txt");
                    for (ComandoElevador c : cmds) {
                        manager.agregarRequest(c);
                    }
                    JOptionPane.showMessageDialog(this,
                            "Lista de requerimientos cargada correctamente.",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Error al cargar requerimientos: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Solo puede cargar requerimientos cuando el sistema está en estado de RESET.",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });

        add(Box.createVerticalStrut(10));
        add(uploadBtn);

        add(Box.createVerticalGlue());
    }

    public void actualizar() {
        lblPiso.setText("Piso actual: " + elevador.getPisoActual());
        lblCola.setText("Cola: " + elevador.getColaComandos().toString());
    }
}









