package ru.shaldin.sd.refactoring.servlet;

import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.shaldin.sd.refactoring.TestUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.shaldin.sd.refactoring.TestUtils.getResponseByUri;

class GetProductsServletTest {
    private static final int PORT = 8081;
    private static final String REQUEST = "/get-products";
    private static final Server SERVER = new Server(PORT);
    private static final HttpClient client = HttpClient.newHttpClient();

    @BeforeAll
    static void startServerAndFillDB() throws Exception {
        TestUtils.createDatabase();
        TestUtils.loadContextToServerByServlet(SERVER, new GetProductsServlet(), REQUEST);
        TestUtils.runServer(SERVER);
        String sql = "INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"apple\", 1), (\"orange\", 2)";
        TestUtils.makeRequestToDatabase(sql);
    }

    @Test
    void getItemsTest() {
        String response = getResponseByUri(client, "http://localhost:" + PORT + REQUEST);
        String expected = "<html><body>\r\n" +
                          "apple\t1</br>\r\n" +
                          "orange\t2</br>\r\n" +
                          "</body></html>";
        assertEquals(expected, response.trim());
    }

    @AfterAll
    static void stopServer() throws Exception {
        TestUtils.clearDatabase();
        TestUtils.stopServer(SERVER);
    }
}