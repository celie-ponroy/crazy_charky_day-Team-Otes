package api;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import api.handlers.HelloHandler;
import api.handlers.EchoHandler;

public class Server {
    public static void start(int port) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

            // Ajouter les routes
            server.createContext("/hello", new HelloHandler());
            server.createContext("/echo", new EchoHandler());

            server.setExecutor(null);
            System.out.println("🚀 Serveur démarré: http://localhost:8080");
            server.start();
        } catch (IOException e) {
            System.err.println("Erreur lors du démarrage du serveur : " + e.getMessage());
        }
    }
}
