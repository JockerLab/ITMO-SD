package api;

import java.util.List;

public class ApiResponse {
    private final String name;
    private final List<String> response;

    public ApiResponse(String name, List<String> response) {
        this.name = name;
        this.response = response;
    }

    public List<String> getResponse() {
        return response;
    }

    public String getName() {
        return name;
    }
}
