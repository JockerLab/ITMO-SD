package ru.shaldin.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class TestUtils {
    public static String getResponseByUri(HttpClient client, String uri) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();
    }

    public static void makeRequestToDatabase(String sql) throws Exception {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    public static void createDatabase() throws Exception {
        String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)";
        makeRequestToDatabase(sql);
    }

    public static void clearDatabase() throws Exception {
        String sql = "DELETE FROM PRODUCT";
        makeRequestToDatabase(sql);
    }

    public static void loadContextToServerByServlet(Server server, HttpServlet servlet, String REQUEST) throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(servlet), REQUEST);
    }

    public static void runServer(Server server) throws Exception {
        server.start();
    }

    public static void stopServer(Server server) throws Exception {
        server.stop();
    }
}
