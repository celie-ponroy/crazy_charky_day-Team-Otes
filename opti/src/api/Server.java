package api;

import api.handlers.RunCalculHandler;
import com.sun.net.httpserver.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    public static void start(int port) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

            // Ajout du middleware CORS à chaque requête
            server.createContext("/", new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                        handleCors(exchange);
                        return;
                    }
                    exchange.sendResponseHeaders(404, -1);
                }
            });

            // Ajout de votre route principale avec CORS
            server.createContext("/api/runCalcul", new CorsHandler(new RunCalculHandler()));

            server.setExecutor(null);
            System.out.println("🚀 Serveur démarré : http://localhost:" + port);
            server.start();
        } catch (IOException e) {
            System.err.println("❌ Erreur lors du démarrage du serveur : " + e.getMessage());
        }
    }

    // Méthode pour gérer les requêtes OPTIONS (CORS preflight)
    private static void handleCors(HttpExchange exchange) throws IOException {
        Headers headers = exchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        headers.add("Access-Control-Allow-Headers", "Content-Type, Authorization");
        exchange.sendResponseHeaders(204, -1);
    }
}
