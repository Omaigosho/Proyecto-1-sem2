package dependencies.classes;

public class ComandoElevador{
    private int piso;
    private String direccion;

    public ComandoElevador(int floor, String direction){
        this.piso = floor;
        this.direccion = direction;
    }

//getters
    public int getPiso(){
        return piso;
    }
    public String getDireccion(){ 
        return direccion; 
    }
    public String toString(){
        return "Piso " + piso + "(" + direccion + ")";
    }
}