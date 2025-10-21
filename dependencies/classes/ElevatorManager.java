package dependencies.classes;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

public class ElevatorManager implements Runnable {
    private final List<Elevator> elevadores;
    private final ConfigSimulador config;
    private final Logger logger;
    private final Queue<ComandoElevador> requests = new ConcurrentLinkedQueue<>();

    public ElevatorManager(List<Elevator> elevadores, ConfigSimulador config) {
        this.elevadores = elevadores;
        this.config = config;
        this.logger = LoggerFunciones.crearLogger("manager");
    }

    public void agregarRequest(ComandoElevador cmd) {
        requests.offer(cmd);
        logger.info("[Manager] Nueva solicitud: " + cmd);
    }

    public void asignarElevador(int piso, String direccion) {
        Elevator elegido = elegirElevador(piso, direccion);
        if (elegido != null) {
            elegido.agregarComando(piso);
            logger.info("[Manager] Asignado elevador " + elegido.getId() + " al piso " + piso);
        }
    }

    private Elevator elegirElevador(int piso, String direccion) {
        Elevator mejor = null;
        int menorDistancia = Integer.MAX_VALUE;

        for (Elevator e : elevadores) {
            int distancia = Math.abs(e.getPisoActual() - piso);
            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                mejor = e;
            }
        }
        return mejor;
    }

    public void resetSistema() {
        logger.info("[Manager] Reset general solicitado");
        for (Elevator e : elevadores) e.reset();
    }

    @Override
    public void run() {
        while (true) {
            ComandoElevador cmd = requests.poll();
            if (cmd != null) asignarElevador(cmd.getPiso(), cmd.getDireccion());
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }

    public List<Elevator> getElevadores() { return elevadores; }
}
