package org.com.api.utils;

import java.io.IOException;
import java.nio.file.*;
import java.util.logging.*;

public class LogConfig {

    private static final Logger LOGGER = Logger.getLogger("com.org.library");

    static {
        try {
            String basePath = System.getProperty("catalina.base", ".");

            Path logDir = Paths.get(basePath, "logs", "message_system");
            if (!Files.exists(logDir)) {
                Files.createDirectories(logDir);
            }

            Path logFile = logDir.resolve("message_app.log");
            if (!Files.exists(logFile)) {
                Files.createFile(logFile);
            }

            FileHandler fileHandler = new FileHandler(logFile.toString(), true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.setUseParentHandlers(false);
            for (Handler handler : LOGGER.getHandlers()) {
                LOGGER.removeHandler(handler);
            }
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);
            LOGGER.info("Custom logging initialized successfully at: " + logFile.toString());

        } catch (IOException e) {
            System.err.println("Failed to setup log file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void init() {
        LOGGER.info("Logging system initialized for Message posting Application.");
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
