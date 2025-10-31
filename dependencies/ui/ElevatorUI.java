package dependencies.ui;
import dependencies.classes.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class ElevatorUI extends JFrame {
    private ElevatorManager manager;
    private ConfigSimulador config;
    private JPanel panelPrincipal;
    private List<UIPanelElevador> panelesElevadores = new ArrayList<>();
    private JTextField txtPisoSolicitud;
    private JComboBox<String> cmbDireccion;

    public ElevatorUI(ElevatorManager manager, ConfigSimulador config) {
        this.manager = manager;
        this.config = config;

        setTitle("Simulador de Elevadores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 650);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 248, 255));
        setLayout(new BorderLayout(15, 15));

        mostrarPanelConfiguracion();
    }

    //Primer Panel-pedimos del usuario
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
        btnIniciar.setForeground(Color.BLACK);
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

        btnIniciar.addActionListener(e -> { //chicos, esto es lo que nos faltaba, para recibir del usuario
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
                new Thread(manager).start();

                iniciarSimulacion();

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

    // Este es el panel principal de los elevadores muchá
    private JPanel crearPanelControles() {
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelControles.setBackground(new Color(240, 248, 255));

        JLabel lblSolicitud = new JLabel("Solicitar elevador al piso:");
        txtPisoSolicitud = new JTextField(5);
        cmbDireccion = new JComboBox<>(new String[]{"up", "down"});
        JButton btnSolicitar = new JButton("Llamar");
        JButton btnReset = new JButton("Reiniciar sistema");
        JButton btnCargar = new JButton("Cargar archivo");
        JButton btnSalir = new JButton("Salir");

    
        btnSolicitar.setBackground(new Color(60, 179, 113));
        btnReset.setBackground(new Color(255, 165, 0));
        btnCargar.setBackground(new Color(100, 149, 237));
        btnSalir.setBackground(new Color(220, 20, 60));
        btnSolicitar.setForeground(Color.BLACK);
        btnReset.setForeground(Color.BLACK);
        btnCargar.setForeground(Color.BLACK);
        btnSalir.setForeground(Color.BLACK);

        panelControles.add(lblSolicitud);
        panelControles.add(txtPisoSolicitud);
        panelControles.add(new JLabel("Dirección:"));
        panelControles.add(cmbDireccion);
        panelControles.add(btnSolicitar);
        panelControles.add(btnReset);
        panelControles.add(btnCargar);
        panelControles.add(btnSalir);

        btnSolicitar.addActionListener(e -> {
            try {
                int piso = Integer.parseInt(txtPisoSolicitud.getText());
                String direccion = cmbDireccion.getSelectedItem().toString();
                
                if (piso < 1 || piso > config.getNumPisos()) {
                    JOptionPane.showMessageDialog(this, 
                        "El piso debe estar entre 1 y " + config.getNumPisos(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                ComandoElevador cmd = new ComandoElevador(piso, direccion);
                manager.agregarRequest(cmd);
                
                JOptionPane.showMessageDialog(this, 
                    "Solicitud enviada: Piso " + piso + " (" + direccion + ")", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                txtPisoSolicitud.setText("");
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Ingrese un número de piso válido.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnReset.addActionListener(e -> {
            manager.ReiniciarSistema();
            JOptionPane.showMessageDialog(this, 
                "Sistema reiniciado.", 
                "Información", JOptionPane.INFORMATION_MESSAGE);
        });

        //EPSILON! Ayuda con el botón de cargar porfaaa
        btnCargar.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccionar archivo de peticiones");
            int result = fileChooser.showOpenDialog(this);
            
            if (result == JFileChooser.APPROVE_OPTION) {
                String filepath = fileChooser.getSelectedFile().getAbsolutePath();
                List<ComandoElevador> comandos = LectorDePeticiones.leerArchivo(filepath);
                
                for (ComandoElevador cmd : comandos) {
                    manager.agregarRequest(cmd);
                }
                
                JOptionPane.showMessageDialog(this, 
                    "Se cargaron " + comandos.size() + " peticiones del archivo.", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnSalir.addActionListener(e -> System.exit(0));

        return panelControles;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ElevatorUI frame = new ElevatorUI(null, null);
            frame.setVisible(true);
        });
    }
}









