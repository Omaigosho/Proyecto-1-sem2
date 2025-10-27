package dependencies.classes;

public abstract class BaseElevator implements Runnable {
    //Amigos, recuerdense de agregar el nombre de las variables donde se utilizen a como estan aqui.
    protected int id;
    protected int pisoElevador;

    protected String direccionElevador;
    protected boolean isActive = true;

    public abstract void agregarComando(int numPiso);
    public abstract void reset();

    public int getId() { 
        return id; 
    }

    public int getPisoElevador() { 
        return pisoElevador; 
    }

    public String getDireccionElevador() { 
        return direccionElevador; 
    }
}
