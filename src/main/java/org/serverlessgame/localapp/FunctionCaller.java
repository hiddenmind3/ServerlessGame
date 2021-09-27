package org.serverlessgame.localapp;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.ClientBuilder;

public class FunctionCaller {
    public static void main(String[] args) {
        ResteasyClient client = (ResteasyClient) ClientBuilder.newClient();
        ResteasyWebTarget target = client.target("https://serverlessgame-zhaw.azurewebsites.net");

        ApiProxy simple = target.proxy(ApiProxy.class);
        String output = simple.getResponse("4");
        System.out.println(output);
    }
}
