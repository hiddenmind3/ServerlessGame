package org.serverlessgame.localapp;

import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/api")
public interface ApiProxy {

    @GET
    @Path("/move")
    @Produces("text/plain")
    String getResponse(@QueryParam("dist") String dist);

    @GET
    @Path("/movewithstorage")
    @Produces("text/plain")
    String getResponseWithStorage(@QueryParam("dist") String dist);
}
