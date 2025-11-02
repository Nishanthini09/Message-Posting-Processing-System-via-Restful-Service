package org.com.api.dao;

import org.com.api.model.User;
import org.com.api.utils.DBUtils;
import org.com.api.utils.LogConfig;

import java.sql.*;
import java.util.logging.Logger;

public class UserDao {
    private static final Logger log = LogConfig.getLogger();

    public boolean signup(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = DBUtils.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            int rows = ps.executeUpdate();
            log.info("User signup success: " + user.getUsername());
            return rows > 0;
        } catch (SQLException e) {
            log.severe("Error in signup: " + e.getMessage());
            return false;
        }
    }

    public User login(int userId, String password) {
        String sql = "SELECT * FROM users WHERE user_id=? AND password=?";
        try (Connection conn = DBUtils.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            log.severe("Login failed: " + e.getMessage());
        }
        return null;
    }
}
