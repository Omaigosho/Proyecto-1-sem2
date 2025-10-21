package dependencies.classes;

import java.io.IOException;
import java.util.logging.*;

public class LoggerFunciones {
    public static Logger crearLogger(String nombreArchivo) {
        Logger logger = Logger.getLogger(nombreArchivo);
        logger.setUseParentHandlers(true);

        try {
            FileHandler handler = new FileHandler(nombreArchivo + ".log", true);
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        } catch (IOException e) {
            System.err.println("Error creando log: " + e.getMessage());
        }

        return logger;
    }
}
