package main;

import dependencies.classes.*;
import dependencies.ui.ElevatorUI;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Simulador Elevator Manager ===");

        int numElevadores = 3;
        int numPisos = 10;
        int tiempoMovimiento = 1000;
        int tiempoEspera = 2000;

        ConfigSimulador config = new ConfigSimulador(numPisos, tiempoMovimiento, tiempoEspera);

        List<Elevator> elevadores = new ArrayList<>();
        for (int i = 1; i <= numElevadores; i++) {
            Elevator e = new Elevator(i, config);
            Thread elvTr = new Thread(e, "Elevator-" + i);
            elvTr.start();
            elevadores.add(e);
        }

        ElevatorManager manager = new ElevatorManager(elevadores, config);
        new Thread(manager, "ElevatorManager").start();

        ElevatorUI ui = new ElevatorUI(manager, config);
        ui.setVisible(true);
    }
}
