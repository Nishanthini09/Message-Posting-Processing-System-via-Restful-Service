package org.com.api.service;

import org.com.api.dao.MessageDao;
import org.com.api.model.MessageSub;
import org.com.api.utils.LogConfig;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class MessageService {
    private static final Logger log = LogConfig.getLogger();
    private final MessageDao dao = new MessageDao();

    private static final ConcurrentHashMap<Integer, String> userTableMap = new ConcurrentHashMap<>();
    private static final Map<String, List<String>> typeToSubTables = new HashMap<>();

    static {
        typeToSubTables.put("t1", Arrays.asList("message_t1_sub1", "message_t1_sub2"));
        typeToSubTables.put("t2", Arrays.asList("message_t2_sub1", "message_t2_sub2"));
        typeToSubTables.put("t3", Arrays.asList("message_t3_sub1", "message_t3_sub2"));
        typeToSubTables.put("t4", Arrays.asList("message_t4_sub1", "message_t4_sub2"));
    }

    private String assignSubTable(int userId, String type) {
        if (userTableMap.containsKey(userId))
            return userTableMap.get(userId);

        List<String> subs = typeToSubTables.get(type);
        if (subs == null)
            return null;

        int index = userId % subs.size();
        String table = subs.get(index);
        userTableMap.put(userId, table);
        log.info("User " + userId + " assigned to sub-table: " + table);
        return table;
    }

    public boolean postMessage(int userId, String content, String type, boolean priority) {
        String subTable = assignSubTable(userId, type);
        if (subTable == null) {
            log.warning("Invalid type: " + type);
            return false;
        }
        long postedTime = System.currentTimeMillis();
        boolean ok = dao.insertMessage(userId, content, type, priority, postedTime, subTable);

        if (ok)
            log.info("Message posted successfully by user=" + userId + " into " + subTable);
        return ok;
    }

    public MessageSub getNextMessage(String subTable) {
        return dao.fetchNextMessage(subTable);
    }

    public void deleteMessage(String subTable, int id) {
        dao.deleteMessage(subTable, id);
    }
}
