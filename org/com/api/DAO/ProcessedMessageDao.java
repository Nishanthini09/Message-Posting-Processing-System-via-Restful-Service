package org.com.api.dao;

import org.com.api.model.MessageProcessed;
import org.com.api.utils.DBUtils;
import org.com.api.utils.LogConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ProcessedMessageDao {
    private static final Logger log = LogConfig.getLogger();

    public boolean insertProcessed(MessageProcessed msg) {
        String sql = "INSERT INTO message_processed (user_id, content, message_type, processed_time) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtils.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, msg.getUserId());
            ps.setString(2, msg.getContent());
            ps.setString(3, msg.getMessageType());
            ps.setLong(4, msg.getProcessedTime());
            ps.executeUpdate();
            log.info("Inserted processed message for user=" + msg.getUserId());
            return true;
        } catch (SQLException e) {
            log.severe("Insert processed message failed: " + e.getMessage());
        }
        return false;
    }
}
