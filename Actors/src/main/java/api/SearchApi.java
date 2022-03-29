package api;

import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SearchApi {
    public abstract String getName();
    private final int PORT = 4321;

    public List<String> requestResponse(String query) {
        URL url = createURL(query);
        String response = receiveResponse(url);
        return parseResponse(response);
    }

    private URL createURL(String query) {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http");
        builder.setHost("localhost");
        builder.setPort(PORT);
        builder.setPath(getName());
        builder.setParameter("q", query);
        try {
            return builder.build().toURL();
        } catch (MalformedURLException | URISyntaxException ignore) {
            // no action
        }
        return null;
    }

    private String receiveResponse(URL url) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            StringBuilder buffer = new StringBuilder();
            String inputLine;

            while((inputLine = in.readLine()) != null) {
                buffer.append(inputLine);
                buffer.append("\n");
            }
            return buffer.toString();
        } catch (IOException ignore) {
            // no action
        }
        return null;
    }

    private List<String> parseResponse(String response) {
        Map<Integer, String> top = new HashMap<>();
        if (response == null || response.isEmpty()) return List.of();
        JSONArray jsonResponse = new org.json.JSONArray(response);
        for (int i = 0; i < jsonResponse.length(); i++) {
            JSONObject cur = jsonResponse.optJSONObject(i);
            for (String key : cur.keySet()) {
                top.put(Integer.parseInt(key), (String) cur.get(key));
            }
        }
        List<String> res = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            res.add(top.get(i));
        }
        return res;
    }
}
