import actor.MasterActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import java.time.Duration;
import scala.concurrent.Await;
import scala.concurrent.Future;
import server.QueriesHandler;


public class SearchAggregator {
    public static void main(String[] args) {
        if (args == null || args.length != 1) {
            System.out.println("Expected one argument.");
            return;
        }

        final String query = args[0];
        QueriesHandler.processWithStub(() -> {
            ActorSystem system = ActorSystem.create("MySystem");
            ActorRef master = system.actorOf(
                    Props.create(MasterActor.class), "master");
            final Timeout timeout = Timeout.create(Duration.ofMinutes(1));
            final Future<Object> future = Patterns.ask(master, query, timeout);
            final String response;
            try {
                response = (String) Await.result(future, timeout.duration());
                System.out.println(response);
            } catch (Exception e) {
                System.err.println("Error occurred while doing request: " + e.getMessage());
            }
            system.stop(master);
            system.terminate();
        });
    }
}
