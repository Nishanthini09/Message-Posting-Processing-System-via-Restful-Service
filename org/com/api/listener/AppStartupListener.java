package org.com.api.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.com.api.service.ProcessingService;
import javax.servlet.annotation.WebListener;
import java.util.logging.Logger;
import org.com.api.utils.LogConfig;

@WebListener
public class AppStartupListener implements ServletContextListener {
    private static final Logger log = LogConfig.getLogger();
    private final ProcessingService processingService = new ProcessingService();

    public void contextInitialized(ServletContextEvent sce) {
        LogConfig.init();
        log.info("Application logging configured successfully at startup.");
        processingService.startProcessingThreads();
        log.info("Message processing threads initialized automatically.");
    }
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Application context destroyed.");
    }
}
