package dependencies.classes;

public class ComandoElevador {
    private int piso;
    private String direccion;

    public ComandoElevador(int piso, String direccion) {
        this.piso = piso;
        this.direccion = direccion;
    }

    public int getPiso() { 
        return piso; 
    }

    public String getDireccion() { 
        return direccion; 
    }

    @Override
    public String toString() {
        return "Piso " + piso + " (" + direccion + ")";
    }
}
