package dependencies.classes;

import java.io.IOException;
import java.util.logging.*;

public class LoggerFunciones {
    public static Logger LogAcciones(String archivoLog) {
        Logger logger = Logger.getLogger(archivoLog);
        logger.setUseParentHandlers(true);

        try {
            FileHandler fh = new FileHandler(archivoLog + ".log", true);
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            logger.addHandler(fh);
        } catch (IOException e) {
            System.err.println("Hubo un error al crear el archivo de log: " + e.getMessage());
        }

        return logger;
    }
}
