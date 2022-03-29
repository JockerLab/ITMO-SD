package actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import api.*;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MasterActor extends AbstractActor {
    final private List<SearchApi> searchApis = List.of(
            new YandexApi(),
            new GoogleApi(),
            new BingApi()
    );

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, query -> sender().tell(getResponse(query), self()))
                .build();
    }

    private String getResponse(String query) {
        final List<ActorRef> childActors = new ArrayList<>();
        final StringBuilder response = new StringBuilder();
        for (SearchApi api : searchApis) {
            childActors.add(getContext().actorOf(Props.create(ChildActor.class, api)));
        }
        final Timeout timeout = Timeout.create(Duration.ofSeconds(3));
        final List<Future<Object>> futures = new ArrayList<>();
        for (ActorRef child : childActors) {
            futures.add(Patterns.ask(child, query, timeout));
        }
        for (Future<Object> future : futures) {
            try {
                ApiResponse data = (ApiResponse) Await.result(future, timeout.duration());
                response.append(data.getName()).append(": ")
                        .append(String.join(" ", data.getResponse())).append("\n");
            } catch (final Exception ignored) {
                // no action
            }
        }
        for (ActorRef child : childActors) {
            getContext().stop(child);
        }
        return response.toString();
    }
}
