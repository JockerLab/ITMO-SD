package ru.shaldin.sd.refactoring.servlet;

import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import ru.shaldin.sd.refactoring.TestUtils;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static ru.shaldin.sd.refactoring.TestUtils.getResponseByUri;

class AddProductServletTest {
    private static final int PORT = 8081;
    private static final String REQUEST = "/add-product";
    private static final Server SERVER = new Server(PORT);
    private static final HttpClient client = HttpClient.newHttpClient();

    @BeforeAll
    static void startServer() throws Exception {
        TestUtils.createDatabase();
        TestUtils.loadContextToServerByServlet(SERVER, new AddProductServlet(), REQUEST);
        TestUtils.runServer(SERVER);
    }

    @Test
    void addOneItemTest() {
        String response = getResponseByUri(client, "http://localhost:" + PORT + REQUEST + "?name=apple&price=1");
        assertEquals("OK", response.trim());
    }

    @AfterAll
    static void stopServer() throws Exception {
        TestUtils.stopServer(SERVER);
    }

    @AfterEach
    void clearDatabase() throws Exception {
        TestUtils.clearDatabase();
    }
}