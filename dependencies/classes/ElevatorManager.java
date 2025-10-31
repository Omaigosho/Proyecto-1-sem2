package dependencies.classes;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

public class ElevatorManager implements Runnable {
    private List<Elevator> elevadores;
    private ConfigSimulador config;
    private Logger logger;
    private Queue<ComandoElevador> requests = new ConcurrentLinkedQueue<>();

    public ElevatorManager(List<Elevator> elevators, ConfigSimulador setting) {
        this.elevadores = elevators;
        this.config = setting;
        this.logger = LoggerFunciones.LogAcciones("manager");
    }

    public void ReiniciarSistema() {
        logger.info("[El admin] Reinicio general solicitado");
        for (Elevator e : elevadores){
            e.reset();
        }
    }
    
    public void agregarRequest(ComandoElevador Request) {
        requests.offer(Request);
        logger.info("[El Admin] Nueva solicitud: " + Request);
    }
    
    public void asignarElevador(int piso, String direccion) {
        Elevator ElevChoosedAssing = elegirElevador(piso, direccion);
        if(ElevChoosedAssing != null) {
            ElevChoosedAssing.agregarComando(piso);
            logger.info("[El Admin] Asignado elevador " + ElevChoosedAssing.getId() + " al piso " + piso);
        }
    }
    
    private Elevator elegirElevador(int piso, String direccion) {
        Elevator ElevChoose = null;
        int menorRecorrido = Integer.MAX_VALUE;
        for(Elevator e : elevadores) {
            int distancia = Math.abs(e.getPisoActual() - piso);
            if (distancia < menorRecorrido) {
                menorRecorrido = distancia;
                ElevChoose = e;
            }
        }
        return ElevChoose;
    }
    
    public List<Elevator> getElevadores(){
        return elevadores;
    }

    public void run(){
        while(true){
            ComandoElevador Request = requests.poll();
            if(Request != null){
                asignarElevador(Request.getPiso(), Request.getDireccion());
            }
            try{
                Thread.sleep(500);
            }
            catch(InterruptedException e){
                 Thread.currentThread().interrupt(); 
            }
        }
    }
}