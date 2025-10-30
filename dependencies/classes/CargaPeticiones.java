package dependencies.classes;

import java.io.*;
import java.util.*;

public class CargaPeticiones {
    public static List<ComandoElevador> cargarDesdeArchivo(String path){
        List<ComandoElevador> cmds = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 2) {
                    int piso = Integer.parseInt(p[0].trim());
                    String dir = p[1].trim();
                    ComandoElevador newCmd = new ComandoElevador(piso, dir);
                    cmds.add(newCmd);
                }
            }
        } catch (IOException e)  {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
        }
        return cmds;
    }
}
