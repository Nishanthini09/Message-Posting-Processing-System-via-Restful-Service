package org.com.api.controller;

import org.com.api.model.User;
import org.com.api.service.UserService;
import org.com.api.utils.LogConfig;
import org.json.JSONObject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.logging.Logger;

@Path("/user")
public class UserController {
    private static final Logger log = LogConfig.getLogger();
    private final UserService service = new UserService();

    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signup(String body) {
        JSONObject req = new JSONObject(body);
        String name = req.getString("username");
        String password = req.getString("password");

        boolean ok = service.signup(name, password);
        JSONObject res = new JSONObject();
        res.put("signup", ok ? "success" : "failed");
        return Response.ok(res.toString()).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@HeaderParam("userId") int id, @HeaderParam("password") String password) {
        JSONObject res = new JSONObject();
        if (password == null || id == 0) {
            res.put("error", "Missing userId or password in header");
            return Response.status(Response.Status.BAD_REQUEST).entity(res.toString()).build();
        }

        User user = service.login(id, password);
        if (user != null) {
            res.put("login", "success");
            return Response.ok(res.toString()).build();
        } else {
            res.put("login", "failed");
            return Response.status(Response.Status.UNAUTHORIZED).entity(res.toString()).build();
        }
    }
}
