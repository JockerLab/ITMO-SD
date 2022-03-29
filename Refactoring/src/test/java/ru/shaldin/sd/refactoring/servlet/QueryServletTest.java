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

class QueryServletTest {
    private static final int PORT = 8081;
    private static final String REQUEST = "/query";
    private static final Server SERVER = new Server(PORT);
    private static final HttpClient client = HttpClient.newHttpClient();

    @BeforeAll
    static void startServerAndFillDB() throws Exception {
        TestUtils.createDatabase();
        TestUtils.loadContextToServerByServlet(SERVER, new QueryServlet(), REQUEST);
        TestUtils.runServer(SERVER);
        String sql = "INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"apple\", 1), (\"orange\", 2)";
        TestUtils.makeRequestToDatabase(sql);
    }

    @Test
    void sumItemsTest() {
        String response = getResponseByUri(client, "http://localhost:" + PORT + REQUEST + "?command=sum");
        String expected = "<html><body>\r\n" +
                          "Summary price: \r\n" +
                          "3\r\n" +
                          "</body></html>";
        assertEquals(expected, response.trim());
    }

    @Test
    void minItemsTest() {
        String response = getResponseByUri(client, "http://localhost:" + PORT + REQUEST + "?command=min");
        String expected = "<html><body>\r\n" +
                "<h1>Product with min price: </h1>\r\n" +
                "apple\t1</br>\r\n" +
                "</body></html>";
        assertEquals(expected, response.trim());
    }

    @Test
    void maxItemsTest() {
        String response = getResponseByUri(client, "http://localhost:" + PORT + REQUEST + "?command=max");
        String expected = "<html><body>\r\n" +
                "<h1>Product with max price: </h1>\r\n" +
                "orange\t2</br>\r\n" +
                "</body></html>";
        assertEquals(expected, response.trim());
    }

    @Test
    void countItemsTest() {
        String response = getResponseByUri(client, "http://localhost:" + PORT + REQUEST + "?command=count");
        String expected = "<html><body>\r\n" +
                "Number of products: \r\n" +
                "2\r\n" +
                "</body></html>";
        assertEquals(expected, response.trim());
    }

    @AfterAll
    static void stopServer() throws Exception {
        TestUtils.clearDatabase();
        TestUtils.stopServer(SERVER);
    }
}