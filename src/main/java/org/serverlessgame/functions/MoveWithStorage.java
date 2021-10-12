package org.serverlessgame.functions;

import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import org.serverlessgame.model.Game;
import org.serverlessgame.model.StaticGame;

/**
 * Azure Functions with HTTP Trigger.
 */
public class MoveWithStorage {
    /**
     * This function listens at endpoint "/api/Move". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/Move
     * 2. curl {your host}/api/Move?name=HTTP%20Query
     */
    @FunctionName("MoveWithStorage")
    public HttpResponseMessage run(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET, HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS)
                    HttpRequestMessage<Optional<String>> request,
            @CosmosDBOutput(name = "serverlessgamecosmosstorage",
                    databaseName = "serverlessgamecosmosstorage",
                    collectionName = "Games",
                    connectionStringSetting = "Cosmos_DB_Connection_String")
                    OutputBinding<String> outputItem,
            @CosmosDBInput(name = "serverlessgamecosmosstorage",
                    databaseName = "serverlessgamecosmosstorage",
                    collectionName = "Games",
                    id = "{Query.position}",
                    partitionKey = "{Query.partitionKeyValue}",
                    connectionStringSetting = "Cosmos_DB_Connection_String")
                    Optional<String> optString,
            final ExecutionContext context) {

        // Parse query parameter
        String dist = request.getQueryParameters().get("dist");

        // Parse query parameter
        String name = request.getBody().orElse(dist);

        // Item list
        context.getLogger().info("Parameters are: " + request.getQueryParameters());

        // Generate random ID
        Game game = new Game();
        game.move(Integer.valueOf(dist));

        // Generate document
        ObjectMapper obj = new ObjectMapper();
        String jsonDocument = null;
        try {
            jsonDocument = obj.writeValueAsString(game);
        } catch (Exception e){
            System.out.println("Exception when converting game to json string");
        }

        context.getLogger().info("Document to be saved: " + jsonDocument);

        // Set outputItem's value to the JSON document to be saved
        outputItem.setValue(jsonDocument);

        return request
                .createResponseBuilder(HttpStatus.OK)
                .body(StaticGame.getPosition())
                .build();
    }
}
