import actor.ChildActor;
import actor.MasterActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import api.ApiResponse;
import api.BingApi;
import api.GoogleApi;
import api.YandexApi;
import org.junit.jupiter.api.*;
import scala.concurrent.Await;
import scala.concurrent.Future;
import server.QueriesHandler;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class ActorTest {
    private final static Timeout TIMEOUT = Timeout.create(Duration.ofMinutes(1));
    private static ActorSystem system;
    private ActorRef master;

    @BeforeAll
    public static void createActors() {
        system = ActorSystem.create("MySystem");
    }

    @AfterAll
    public static void terminateSystem() {
        system.terminate();
    }

    @BeforeEach
    public void createMaster() {
        master = system.actorOf(Props.create(MasterActor.class));
    }

    @AfterEach
    public void stopMaster() {
        system.stop(master);
    }


    @Test
    public void test_master_1__get_response() {
        assertEquals("yandex: yandex-1 yandex-2 yandex-3 yandex-4 yandex-5\n" +
                        "google: google-1 google-2 google-3 google-4 google-5\n" +
                        "bing: bing-1 bing-2 bing-3 bing-4 bing-5\n",
                getResponse("hello"));
    }

    @Test
    public void test_master_2__freeze_yandex() {
        assertEquals("google: google-1 google-2 google-3 google-4 google-5\n" +
                        "bing: bing-1 bing-2 bing-3 bing-4 bing-5\n",
                getResponse("yandex_freeze"));
    }

    @Test
    public void test_master_3__freeze_google() {
        assertEquals("yandex: yandex-1 yandex-2 yandex-3 yandex-4 yandex-5\n" +
                        "bing: bing-1 bing-2 bing-3 bing-4 bing-5\n",
                getResponse("google_freeze"));
    }

    @Test
    public void test_master_4__freeze_bing() {
        assertEquals("yandex: yandex-1 yandex-2 yandex-3 yandex-4 yandex-5\n" +
                        "google: google-1 google-2 google-3 google-4 google-5\n",
                getResponse("bing_freeze"));
    }

    @Test
    public void test_child_1__yandex() {
        ActorRef child = system.actorOf(Props.create(ChildActor.class, new YandexApi()));
        ApiResponse res;

        res = getChildRes(child, "yandex");
        assertEquals("yandex", res.getName());
        assertEquals(List.of("yandex-1", "yandex-2", "yandex-3", "yandex-4", "yandex-5"), res.getResponse());
    }

    @Test
    public void test_child_2__google() {
        ActorRef child = system.actorOf(Props.create(ChildActor.class, new GoogleApi()));
        ApiResponse res;

        res = getChildRes(child, "google");
        assertEquals("google", res.getName());
        assertEquals(List.of("google-1", "google-2", "google-3", "google-4", "google-5"), res.getResponse());
    }

    @Test
    public void test_child_3__bing() {
        ActorRef child = system.actorOf(Props.create(ChildActor.class, new BingApi()));
        ApiResponse res;

        res = getChildRes(child, "bing");
        assertEquals("bing", res.getName());
        assertEquals(List.of("bing-1", "bing-2", "bing-3", "bing-4", "bing-5"), res.getResponse());
    }

    private String getResponse(String query) {
        AtomicReference<String> res = new AtomicReference<>("");
        QueriesHandler.processWithStub(() -> {
            Future<Object> future = Patterns.ask(master, query, TIMEOUT);
            try {
                res.set((String) Await.result(future, TIMEOUT.duration()));
            } catch (Exception ignore) {
                // no action
            }
        });
        return res.get();
    }

    private ApiResponse getChildRes(ActorRef childActor, String query) {
        AtomicReference<ApiResponse> res = new AtomicReference<>(null);
        QueriesHandler.processWithStub(() -> {
            Future<Object> future = Patterns.ask(childActor, query, TIMEOUT);
            try {
                res.set((ApiResponse) Await.result(future, TIMEOUT.duration()));
            } catch (Exception ignore) {
                // no action
            }
        });
        return res.get();
    }
}
