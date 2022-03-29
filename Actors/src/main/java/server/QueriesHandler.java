package server;

import com.xebialabs.restito.server.StubServer;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.glassfish.grizzly.http.Method;

import java.util.Objects;
import java.util.function.Consumer;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.*;

public class QueriesHandler {
    private static final int PORT = 4321;

    public static void processWithStub(Runnable task) {
        withStubServer(s -> {
            handleQueryWithStub(s, "yandex");
            handleQueryWithStub(s, "google");
            handleQueryWithStub(s, "bing");

            task.run();
        });
    }

    private static void handleQueryWithStub(StubServer s, String service) {
        whenHttp(s)
                .match(method(Method.GET), startsWithUri("/" + service), custom(c -> {
                    String q = c.getParameters().get("q")[0];
                    if (Objects.equals(service, "yandex") && q.startsWith("yandex_freeze")
                            || Objects.equals(service, "google") && q.startsWith("google_freeze")
                            || Objects.equals(service, "bing") && q.startsWith("bing_freeze")
                    ) {
                        freeze();
                    }
                    return true;
                }))
                .then(stringContent(generateResponse(service)));
    }

    private static void freeze() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignore) {
            // no action
        }
    }

    private static String generateResponse(String searchApi) {
        JSONArray array = new JSONArray();
        for (int i = 1; i <= 5; i++) {
            JSONObject cur = new JSONObject();
            cur.put(Integer.toString(i), searchApi + "-" + i);
            array.add(cur);
        }
        return array.toString();
    }

    private static void withStubServer(Consumer<StubServer> callback) {
        StubServer stubServer = null;
        try {
            stubServer = new StubServer(PORT).run();
            callback.accept(stubServer);
        } finally {
            if (stubServer != null) {
                stubServer.stop();
            }
        }
    }
}
