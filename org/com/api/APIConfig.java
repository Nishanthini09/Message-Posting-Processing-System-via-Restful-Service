package org.com.api;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
import org.com.api.controller.*;

public class APIConfig extends Application {
    public Set<Class<?>> getClasses(){
        Set<Class<?>> classes = new HashSet<>();
        classes.add(MessageController.class);
        classes.add(UserController.class);
        return classes;
    }
}
