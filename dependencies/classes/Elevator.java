package dependencies.classes;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

public class Elevator extends BaseElevator {
    private final Queue<Integer> colaComandos = new ConcurrentLinkedQueue<>();
    private final ConfigSimulador config;
    private final Logger logger;

    public Elevator(int id, ConfigSimulador config) {
        this.id = id;
        this.pisoActual = 1;
        this.direccion = "up";
        this.config = config;
        this.logger = LoggerFunciones.crearLogger("elevador" + id);
    }

    @Override
    public void agregarComando(int piso) {
        if (piso > 0 && piso <= config.getNumPisos() && piso != pisoActual) {
            colaComandos.offer(piso);
            logger.info("[Elevador " + id + "] Nuevo comando agregado: ir al piso " + piso);
        }
    }

    @Override
    public void reset() {
        colaComandos.clear();
        pisoActual = 1;
        direccion = "up";
        logger.info("[Elevador " + id + "] Reset completado");
    }

    private void moverHacia(int destino) throws InterruptedException {
        if (destino > pisoActual) direccion = "up";
        else if (destino < pisoActual) direccion = "down";

        logger.info("[Elevador " + id + "] Moviéndose " + direccion + " hacia piso " + destino);

        while (pisoActual != destino && activo) {
            Thread.sleep(config.getTiempoMovimiento());
            pisoActual += direccion.equals("up") ? 1 : -1;
            logger.info("[Elevador " + id + "] Piso actual: " + pisoActual);
        }

        logger.info("[Elevador " + id + "] Llegó al piso " + pisoActual);
        Thread.sleep(config.getTiempoEspera());
        logger.info("[Elevador " + id + "] Parada completada en piso " + pisoActual);
    }

    @Override
    public void run() {
        while (activo) {
            try {
                Integer destino = colaComandos.poll();
                if (destino != null) moverHacia(destino);
                else Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public Queue<Integer> getColaComandos() { return colaComandos; }
}
