package org.com.api.dao;

import org.com.api.model.MessageSub;
import org.com.api.utils.DBUtils;
import org.com.api.utils.LogConfig;

import java.sql.*;
import java.util.logging.Logger;

public class MessageDao {
    private static final Logger log = LogConfig.getLogger();

    public boolean insertMessage(int userId, String content, String type, boolean priority, long postedTime, String subTable) {
        String mainSql = "INSERT INTO message_main (user_id, type_id, assigned_sub_table, posted_time) VALUES (?, (SELECT type_id FROM message_type WHERE type_name=?), ?, ?)";
        String subSql = "INSERT INTO " + subTable + " (user_id, content, priority, posted_time) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtils.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psMain = conn.prepareStatement(mainSql);
                 PreparedStatement psSub = conn.prepareStatement(subSql)) {

                psMain.setInt(1, userId);
                psMain.setString(2, type);
                psMain.setString(3, subTable);
                psMain.setLong(4, postedTime);
                psMain.executeUpdate();

                psSub.setInt(1, userId);
                psSub.setString(2, content);
                psSub.setBoolean(3, priority);
                psSub.setLong(4, postedTime);
                psSub.executeUpdate();

                conn.commit();
                log.info("Message inserted successfully into " + subTable);
                return true;
            } catch (SQLException e) {
                conn.rollback();
                log.severe("Insert message failed: " + e.getMessage());
            }
        } catch (SQLException e) {
            log.severe("DB connection error: " + e.getMessage());
        }
        return false;
    }

    public MessageSub fetchNextMessage(String subTable) {
        String sql = "SELECT * FROM " + subTable + " ORDER BY priority DESC, posted_time ASC LIMIT 1";

        try (Connection conn = DBUtils.getConnection()){
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new MessageSub(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("content"),
                        rs.getBoolean("priority"),
                        rs.getLong("posted_time")
                );
            }
        } catch (SQLException e) {
            log.severe("Error fetching next message: " + e.getMessage());
        }
        return null;
    }

    public void deleteMessage(String subTable, int id) {
        String sql = "DELETE FROM " + subTable + " WHERE id=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            log.info("Message deleted from " + subTable + " (ID=" + id + ")");
        } catch (SQLException e) {
            log.severe("Error deleting message: " + e.getMessage());
        }
    }
}
