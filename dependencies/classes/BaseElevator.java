package dependencies.classes;

public abstract class BaseElevator implements Runnable {
    protected int id;
    protected int pisoActual;
    protected String direccion;
    protected boolean activo = true;

    public abstract void agregarComando(int piso);
    public abstract void reset();

    public int getId() { return id; }
    public int getPisoActual() { return pisoActual; }
    public String getDireccion() { return direccion; }
}
