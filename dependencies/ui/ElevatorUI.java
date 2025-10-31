package dependencies.ui;

import dependencies.classes.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class ElevatorUI extends JFrame {
<<<<<<< Updated upstream
    private final ElevatorManager manager;
    private final ConfigSimulador config;
    private final JTextArea areaTexto;
=======
    private ElevatorManager manager;
    private ConfigSimulador config;
    private JPanel panelPrincipal;
    private List<UIPanelElevador> panelesElevadores = new ArrayList<>();
    private JTextField txtPisoSolicitud;
    private JComboBox<String> cmbDireccion;
>>>>>>> Stashed changes

    public ElevatorUI(ElevatorManager manager, ConfigSimulador config) {
        this.manager = manager;
        this.config = config;

        setTitle("Simulador de Elevadores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 650);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 248, 255));
        setLayout(new BorderLayout(15, 15));

<<<<<<< Updated upstream
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        add(new JScrollPane(areaTexto), BorderLayout.CENTER);

        JPanel controls = new JPanel();
        JTextField pisoField = new JTextField(5);
        String[] dictDirectiones = new String[]{
            "up", "down"
        };

        JComboBox<String> direccionBox = new JComboBox<>(dictDirectiones);
        JButton callBtn = new JButton("Llamar elevador");
        JButton resetBtn = new JButton("Resetear sistema de elevadores");
        JButton uploadBtn = new JButton("Cargar comandos");

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

            int numeroPisos = config.getNumPisos();

            UIPanelElevador panel = new UIPanelElevador(e, numeroPisos);
            panelElevadores.add(panel);

        }

        add(panelElevadores, BorderLayout.EAST);

        Timer estadoTimer = new Timer(500, e -> actualizarEstado());
        estadoTimer.start();
=======
        mostrarPanelConfiguracion();
>>>>>>> Stashed changes
    }

    private void mostrarPanelConfiguracion() {
        JPanel panelConfig = new JPanel(new GridLayout(5, 2, 10, 10));
        panelConfig.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
        panelConfig.setBackground(new Color(245, 250, 255));

        JLabel lblTitulo = new JLabel("Configuración de la Simulación", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(40, 70, 140));

        JTextField txtNumElevadores = new JTextField();
        JTextField txtNumPisos = new JTextField();
        JTextField txtTiempoMov = new JTextField();
        JTextField txtTiempoEsp = new JTextField();

        JButton btnIniciar = new JButton("Iniciar Simulación");
        btnIniciar.setBackground(new Color(70, 130, 180));
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setFocusPainted(false);
        btnIniciar.setFont(new Font("SansSerif", Font.BOLD, 14));

        panelConfig.add(new JLabel("Cantidad de elevadores:"));
        panelConfig.add(txtNumElevadores);
        panelConfig.add(new JLabel("Cantidad de pisos:"));
        panelConfig.add(txtNumPisos);
        panelConfig.add(new JLabel("Tiempo de movimiento (ms):"));
        panelConfig.add(txtTiempoMov);
        panelConfig.add(new JLabel("Tiempo de espera (ms):"));
        panelConfig.add(txtTiempoEsp);
        panelConfig.add(new JLabel(""));
        panelConfig.add(btnIniciar);

        add(lblTitulo, BorderLayout.NORTH);
        add(panelConfig, BorderLayout.CENTER);

        btnIniciar.addActionListener(e -> {
            try {
                int numElevadores = Integer.parseInt(txtNumElevadores.getText());
                int numPisos = Integer.parseInt(txtNumPisos.getText());
                int tiempoMov = Integer.parseInt(txtTiempoMov.getText());
                int tiempoEsp = Integer.parseInt(txtTiempoEsp.getText());

                this.config = new ConfigSimulador(numPisos, tiempoMov, tiempoEsp);
                List<Elevator> elevadores = new ArrayList<>();

                for (int i = 1; i <= numElevadores; i++) {
                    Elevator elevador = new Elevator(i, config);
                    elevadores.add(elevador);
                    new Thread(elevador).start();
                }

                this.manager = new ElevatorManager(elevadores, config);

                iniciarSimulacion();
                new Thread(manager).start();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor ingrese valores válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void iniciarSimulacion() {
        getContentPane().removeAll();
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitulo = new JLabel("Simulación en Progreso", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(40, 70, 140));
        add(lblTitulo, BorderLayout.NORTH);

        panelPrincipal = new JPanel(new GridLayout(1, manager.getElevadores().size(), 10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(235, 243, 252));

        for (Elevator e : manager.getElevadores()) {
<<<<<<< Updated upstream
            sb.append("Elevador ").append(e.getId())
              .append(" | Piso: ").append(e.getPisoActual())
              .append(" | Dir: ").append(e.getDireccion())
              //No entiendo porque no se imprime la cola pero si todo lo demas, ahi lo miras vos abi XDDDDD
              .append(" | Cola: ").append(e.getColaComandos()).append("\n");
        }
        areaTexto.setText(sb.toString());
=======
            UIPanelElevador panel = new UIPanelElevador(e, config, manager);
            panelesElevadores.add(panel);
            panelPrincipal.add(panel);
        }

        add(panelPrincipal, BorderLayout.CENTER);
        add(crearPanelControles(), BorderLayout.SOUTH);

        revalidate();
        repaint();

        Timer timer = new Timer(1000, e -> {
            for (UIPanelElevador panel : panelesElevadores) {
                panel.actualizar();
            }
        });
        timer.start();
    }

    private JPanel crearPanelControles() {
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelControles.setBackground(new Color(240, 248, 255));

        JLabel lblSolicitud = new JLabel("Solicitar elevador al piso:");
        txtPisoSolicitud = new JTextField(5);
        cmbDireccion = new JComboBox<>(new String[]{"Subir", "Bajar"});
        JButton btnSolicitar = new JButton("Llamar");
        JButton btnReset = new JButton("Resetear sistema");
        JButton btnSalir = new JButton("Salir");

        btnSolicitar.setBackground(new Color(60, 179, 113));
        btnReset.setBackground(new Color(255, 165, 0));
        btnSalir.setBackground(new Color(220, 20, 60));
        btnSolicitar.setForeground(Color.WHITE);
        btnReset.setForeground(Color.WHITE);
        btnSalir.setForeground(Color.WHITE);

        panelControles.add(lblSolicitud);
        panelControles.add(txtPisoSolicitud);
        panelControles.add(new JLabel("Dirección:"));
        panelControles.add(cmbDireccion);
        panelControles.add(btnSolicitar);
        panelControles.add(btnReset);
        panelControles.add(btnSalir);

        btnSolicitar.addActionListener(e -> {
            try {
                int piso = Integer.parseInt(txtPisoSolicitud.getText());
                String direccion = cmbDireccion.getSelectedItem().toString().toLowerCase();
                manager.asignarElevador(piso, direccion);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese un número de piso válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnReset.addActionListener(e -> {
            manager.resetSistema();
            JOptionPane.showMessageDialog(this, "Sistema reseteado.", "Información", JOptionPane.INFORMATION_MESSAGE);
        });

        btnSalir.addActionListener(e -> System.exit(0));

        return panelControles;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ElevatorUI frame = new ElevatorUI(null, null);
            frame.setVisible(true);
        });
>>>>>>> Stashed changes
    }
}








