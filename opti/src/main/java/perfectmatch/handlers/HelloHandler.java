package perfectmatch.handlers;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import javax.json.Json;
import javax.json.JsonObject;
import perfectmatch.models.ApiResponse;

public class HelloHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            JsonObject jsonData = Json.createObjectBuilder()
                    .add("greeting", "Hello, World!")
                    .build();

            ApiResponse response = new ApiResponse("Success", jsonData);
            sendResponse(exchange, response.toJsonString(), 200);
        } else {
            sendResponse(exchange, "{ \"message\": \"Method Not Allowed\" }", 405);
        }
    }

    private void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.length());

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
