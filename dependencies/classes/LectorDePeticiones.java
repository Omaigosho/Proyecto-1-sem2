package dependencies.classes;

import java.util.*;
import java.io.*;

public class LectorDePeticiones {
    public static List<ComandoElevador> leerArchivo(String nombreArchivo){
        List<ComandoElevador> lista = new ArrayList<>();
        try{
            File file = new File(nombreArchivo);
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] Split = line.split(",");

                if (Split.length == 2) {
                    int piso = Integer.parseInt(Split[0].trim());
                    String direccion = Split[1].trim();
                    lista.add(new ComandoElevador(piso, direccion));
                }
            }
            sc.close();
        }
        catch(Exception e){
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return lista;
    }
}