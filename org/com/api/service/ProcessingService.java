package org.com.api.service;

import org.com.api.dao.MessageDao;
import org.com.api.dao.ProcessedMessageDao;
import org.com.api.model.MessageSub;
import org.com.api.model.MessageProcessed;
import org.com.api.utils.LogConfig;

import java.util.*;
import java.util.logging.Logger;

public class ProcessingService {
    private static final Logger log = LogConfig.getLogger();
    private final MessageDao messageDao = new MessageDao();
    private final ProcessedMessageDao processedDao = new ProcessedMessageDao();

    private static final List<Thread> workers = new ArrayList<>();
    private static boolean started = false;

    public synchronized void startProcessingThreads() {
        if (started) {
            log.info("Processing threads already running.");
            return;
        }
        started = true;
        List<String> allSubTables = Arrays.asList(
                "message_t1_sub1","message_t1_sub2",
                "message_t2_sub1","message_t2_sub2",
                "message_t3_sub1","message_t3_sub2",
                "message_t4_sub1","message_t4_sub2"
        );

        for (String subTable : allSubTables) {
            Thread t = new Thread(() -> processSubTable(subTable));
            t.setName("Processor-" + subTable);
            t.setDaemon(true);
            t.start();
            workers.add(t);
        }
        log.info("Started " + workers.size() + " processing threads.");
    }

    private void processSubTable(String subTable) {
        log.info("Processing thread started for " + subTable);
        while (true) {
            try {
                MessageSub msg = messageDao.fetchNextMessage(subTable);
                if (msg == null) {
                    Thread.sleep(20000);
                    continue;
                }
                Thread.sleep(200000);

                long now = System.currentTimeMillis();
                String type = extractType(subTable);

                MessageProcessed processed = new MessageProcessed(msg.getUserId(), msg.getContent(), type, now);

                try{
                    Thread.sleep(20000);
                }
                finally{
                    processedDao.insertProcessed(processed);
                }
                messageDao.deleteMessage(subTable, msg.getId());
                log.info("Processed message from " + subTable + " (user=" + msg.getUserId() + ", priority=" + msg.isPriority() + ")");
            } catch (Exception e) {
                log.severe("Error in " + subTable + " thread: " + e.getMessage());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
            }
        }
    }

    private String extractType(String subTable) {
        try {
            String[] parts = subTable.split("_");
            return parts.length > 1 ? parts[1] : "unknown";
        } catch (Exception e) {
            log.warning("Invalid subTable name: " + subTable);
            return "unknown";
        }
    }
}
