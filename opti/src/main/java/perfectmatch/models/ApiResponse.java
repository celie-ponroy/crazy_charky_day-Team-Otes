package perfectmatch.models;

import javax.json.Json;
import javax.json.JsonObject;

public class ApiResponse {
    private String message;
    private JsonObject data;

    public ApiResponse(String message, JsonObject data) {
        this.message = message;
        this.data = data;
    }

    public String toJsonString() {
        return Json.createObjectBuilder()
                .add("message", message)
                .add("data", data)
                .build()
                .toString();
    }
}
