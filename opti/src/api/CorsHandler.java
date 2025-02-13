package api;

import com.sun.net.httpserver.*;

import java.io.IOException;

public class CorsHandler implements HttpHandler {
    private final HttpHandler wrappedHandler;

    public CorsHandler(HttpHandler handler) {
        this.wrappedHandler = handler;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Headers headers = exchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        headers.add("Access-Control-Allow-Headers", "Content-Type, Authorization");

        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        wrappedHandler.handle(exchange);
    }
}
