import java.util.*;
import java.io.*;

public class LectorDePeticiones {
    public static List<ComandoElevador> leerArchivo(String nombreArchivo){
        List<ComandoElevador> lista = new ArrayList<>();
        try{
            File file = new File(nombreArchivo);
            Scanner sc = new Scanner(archivo);
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] Split = line.split(",");

                if (partes.length == 2) {
                    int piso = Integer.parseInt(split[0].strip());
                    String direccion = split[1].strip();
                    lista.add(new ComandoElevador(piso, direccion));
                }
            }
        }
        catch(Exception e){
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return lista;
    }
}