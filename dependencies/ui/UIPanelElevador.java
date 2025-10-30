package dependencies.ui;

import dependencies.classes.Elevator;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class UIPanelElevador extends JPanel {
    public UIPanelElevador(Elevator e, int numPisos) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        add(new JLabel("Panel Elevador " + e.getId()));

        for (int i = numPisos; i >= 1; i--) {
            int piso = i;
            JButton btn = new JButton("Piso " + piso);
            btn.addActionListener((ActionEvent ev) -> e.agregarComando(piso));
            add(btn);
        }
    }
}
