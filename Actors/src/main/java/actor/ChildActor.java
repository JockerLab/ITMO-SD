package actor;

import akka.actor.AbstractActor;
import api.ApiResponse;
import api.SearchApi;
import server.QueriesHandler;

public class ChildActor extends AbstractActor {
    private final SearchApi api;

    public ChildActor(SearchApi api) {
        this.api = api;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, msg -> sender().tell(getApiResponseWithStub(msg), self()))
                .build();
    }

    private ApiResponse getApiResponseWithStub(String query) {
        return new ApiResponse(api.getName(), api.requestResponse(query));
    }
}
