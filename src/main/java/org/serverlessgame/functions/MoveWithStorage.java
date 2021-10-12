package org.serverlessgame.functions;

import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import org.serverlessgame.model.Game;

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
                    collectionName = "Items",
                    connectionStringSetting = "AzureCosmosDBConnection")
            final ExecutionContext context) {

        // Parse query parameter
        String query = request.getQueryParameters().get("dist");
        try{
            int dist = Integer.valueOf(request.getBody().orElse(query));
            Game.move(dist);
        } catch (NumberFormatException e) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Query Parameter not a Number").build();
        }


        return request.createResponseBuilder(HttpStatus.OK).body(Game.getPosition()).build();
    }
}
