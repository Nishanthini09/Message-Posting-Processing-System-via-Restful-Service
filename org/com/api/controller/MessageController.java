package org.com.api.controller;

import org.com.api.service.MessageService;
import org.com.api.utils.LogConfig;
import org.json.JSONObject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.logging.Logger;
import org.com.api.service.ProcessingService;

@Path("/message")
public class MessageController {
    private static final Logger log = LogConfig.getLogger();
    private final MessageService service = new MessageService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postMessage(@HeaderParam("userId") int userId,
                                @HeaderParam("password") String password,
                                String body) {
        log.info("data added started!!");
        JSONObject res = new JSONObject();
        if (password == null || userId == 0) {
            res.put("error", "Missing credentials");
            return Response.status(Response.Status.UNAUTHORIZED).entity(res.toString()).build();
        }
        JSONObject req = new JSONObject(body);
        String content = req.getString("content");
        String type = req.getString("type");
        boolean priority = req.has("priority") && req.getString("priority").equalsIgnoreCase("yes");

        boolean ok = service.postMessage(userId, content, type, priority);

        res.put("status", ok ? "queued" : "failed");
        return Response.ok(res.toString()).build();
    }
}
