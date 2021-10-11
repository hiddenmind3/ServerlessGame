package org.serverlessgame.localapp;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.ClientBuilder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FunctionCaller {
    public static void main(String[] args) {
        ResteasyClient client = (ResteasyClient) ClientBuilder.newClient();
        ResteasyWebTarget target = client.target("https://serverlessgame-zhaw.azurewebsites.net");

        ApiProxy simple = target.proxy(ApiProxy.class);

        long start = System.currentTimeMillis();

        int n = 100;

        ExecutorService es = Executors.newCachedThreadPool();

        for(int i=0; i < n; i++){
            es.execute(new Runnable() {
                @Override
                public void run() {
                    String output = simple.getResponse("1");
                    System.out.println(output);
                }
            });
        }
        es.shutdown();
        try{
            es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (Exception e) {
            System.out.println("Exception");
        }

        System.out.println("time: " + (System.currentTimeMillis() - start)/n);
    }
}
