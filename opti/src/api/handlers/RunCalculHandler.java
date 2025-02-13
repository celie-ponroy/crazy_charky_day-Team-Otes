package api.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import javax.json.Json;
import javax.json.JsonObject;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RunCalculHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendResponse(exchange, 405, "Méthode non autorisée");
            return;
        }

        try {
            // Récupérer le body de la requête
            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            JsonObject json = Json.createReader(new java.io.StringReader(requestBody)).readObject();

            if (!json.containsKey("date")) {
                sendResponse(exchange, 400, "Le paramètre 'date' est requis");
                return;
            }

            String dateStr = json.getString("date");
            if (isValidDate(dateStr)) {

                // TODO: faire le traitmenet du calcul ici

                sendResponse(exchange, 200, "Date valide");
            } else {
                sendResponse(exchange, 400, "Format de date invalide (attendu: yyyy-MM-dd)");
            }
        } catch (Exception e) {
            sendResponse(exchange, 500, "Erreur interne du serveur");
        }
    }

    private boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        JsonObject responseJson = Json.createObjectBuilder()
                .add("message", message)
                .build();

        String response = responseJson.toString();

        // Définir l'encodage UTF-8 dans les headers
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

}
