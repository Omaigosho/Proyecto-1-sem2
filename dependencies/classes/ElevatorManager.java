package dependencies.classes;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

public class ElevatorManager implements Runnable {
    private List<Elevator> listaElevadores;
    private ConfigSimulador config;
    private Logger lg;
    private Queue<ComandoElevador> requests = new ConcurrentLinkedQueue<>();

    public ElevatorManager(List<Elevator> listaElevadores, ConfigSimulador config) {
        this.listaElevadores = listaElevadores;
        this.config = config;
        this.lg = LoggerFunciones.LogAcciones("manager");
    }

    public void agregarRequest(ComandoElevador cmd) {
        requests.offer(cmd);
        lg.info("[Manager] Nueva solicitud: " + cmd);
    }

    public void asignarElevador(int piso, String direccion) {
        Elevator elegido = elegirElevador(piso, direccion);
        if (elegido != null) {

            elegido.agregarComando(piso);
            lg.info("[Manager] Asignado elevador " + elegido.getId() + " al piso " + piso);
            
        }
    }

    private Elevator elegirElevador(int piso, String direccion) {
        Elevator mejorElevador = null;
        int menorDistancia = 1000;

        for (Elevator elv : getElevadores()) {
            int distancia = elv.getPisoElevador() - piso;
            
            if (Math.abs(distancia) < menorDistancia) {
                menorDistancia = distancia;
                mejorElevador = elv;
            }
        }
        return mejorElevador;
    }

    public void resetSistema() {
        lg.info("[Manager] Reset general solicitado");

        for (Elevator elv : listaElevadores) {
            elv.reset();
        }
    }

    @Override
    public void run() {
        while (true) {
            ComandoElevador cmd = requests.poll();
            if (cmd != null) asignarElevador(cmd.getPiso(), cmd.getDireccion());
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }

    public List<Elevator> getElevadores() { 
        return listaElevadores; 
    }
}
