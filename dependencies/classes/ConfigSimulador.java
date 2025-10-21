package dependencies.classes;


public class ConfigSimulador{
     private final int numPisos;
    private final int tiempoMovimiento;
    private final int tiempoEspera;

    public ConfigSimulador(int numPisos, int tiempoMovimiento, int tiempoEspera) {
        this.numPisos = numPisos;
        this.tiempoMovimiento = tiempoMovimiento;
        this.tiempoEspera = tiempoEspera;
    }

    public int getNumPisos() { return numPisos; }
    public int getTiempoMovimiento() { return tiempoMovimiento; }
    public int getTiempoEspera() { return tiempoEspera; }
}