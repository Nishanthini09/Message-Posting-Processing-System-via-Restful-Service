package org.com.api.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DBUtils {
    private static final Logger log = LogConfig.getLogger();

    private static final String URL = "jdbc:postgresql://localhost:5454/messager";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            log.severe("PostgreSQL Driver not found: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
//        LogConfig.init();
        Connection conn = DriverManager.getConnection(URL, USER, PASS);
        log.info("DB Connection established");
        if (conn == null) {
            log.warning("DB connection returned null — check your credentials or DB server!");
        }
        return conn;
    }
}
