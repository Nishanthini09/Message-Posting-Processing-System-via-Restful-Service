package org.com.api.service;

import org.com.api.dao.UserDao;
import org.com.api.model.User;
import org.com.api.utils.LogConfig;

import java.util.logging.Logger;

public class UserService {
    private static final Logger log = LogConfig.getLogger();
    private final UserDao dao = new UserDao();

    public boolean signup(String username, String password) {
        User u = new User(username, password);
        boolean result = dao.signup(u);
        log.info("Signup request for " + username + " result=" + result);
        return result;
    }

    public User login(int userId, String password) {
        User u = dao.login(userId, password);
        if (u != null) log.info("Login success for userId=" + userId);
        else log.warning("Login failed for userId=" + userId);
        return u;
    }
}
