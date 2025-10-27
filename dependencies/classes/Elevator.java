package dependencies.classes;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

public class Elevator extends BaseElevator {
    
    private Queue<Integer> colaComandos = new ConcurrentLinkedQueue<>();
    private ConfigSimulador config;
    private Logger lg;

    public Elevator(int id, ConfigSimulador config) {
        this.id = id;
        this.pisoElevador = 1;
        this.direccionElevador = "up";
        this.config = config;
        this.lg = LoggerFunciones.LogAcciones("elevador" + id);
    }

    @Override
    public void agregarComando(int numPiso) {
        int pisos = config.getNumPisos();
        if (numPiso > 0 && numPiso <= pisos && numPiso != pisoElevador) {

            colaComandos.offer(numPiso);
            lg.info("[Elevador " + id + "] Agregado el comando: Dirigirse al piso " + numPiso);

        }
    }

    @Override
    public void reset() {
        colaComandos.clear();
        pisoElevador = 1;
        direccionElevador = "up";
        lg.info("[Elevador " + id + "] Elevador Reseteado");
    }

    public Queue<Integer> getColaComandos() { 
        return colaComandos; 
    }

    private void moverHacia(int destino) throws InterruptedException {
        int tiempoSleep = config.getTiempoMovimiento();

        if (destino > pisoElevador) {
            direccionElevador = "up";
        } else if (destino < pisoElevador) {
            direccionElevador = "down";
        }

        lg.info("[Elevador " + id + "] Moviéndose " + direccionElevador + " hacia piso " + destino);

        while (pisoElevador != destino && isActive) {
            Thread.sleep(tiempoSleep);

            if (direccionElevador.equals("up")){
                pisoElevador +=1;
            } else {
                pisoElevador -=1;
            }
            lg.info("[Elevador " + id + "] Piso actual: " + pisoElevador);
        }

        lg.info("[Elevador " + id + "] Llegó al piso " + pisoElevador);
        Thread.sleep(tiempoSleep);
        lg.info("[Elevador " + id + "] Parada completada en piso " + pisoElevador);
    }

    @Override
    public void run() {
        while (isActive) {
            try {
                Integer destinoDeElevador = getColaComandos().poll();

                if (destinoDeElevador != null) {

                    moverHacia(destinoDeElevador);

                }else {

                    Thread.sleep(500);

                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    
}
