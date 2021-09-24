package org.serverlessgame.localapp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("https://serverlessgame-zhaw.azurewebsites.net")
public class HttpTriggerFunctionCaller {

    @GET
    @Path("/api/httpexample")
    String getResponse(@PathParam("name") String name);
}
