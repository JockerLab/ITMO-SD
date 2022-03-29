package ru.shaldin.eventsourcing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.shaldin.eventsourcing.aggregates.ManagerAggregate;
import ru.shaldin.eventsourcing.aggregates.TurnstileAggregate;
import ru.shaldin.eventsourcing.commands.CreateClientCommand;
import ru.shaldin.eventsourcing.commands.CreateSubscriptionCommand;
import ru.shaldin.eventsourcing.commands.ExtendSubscriptionCommand;
import ru.shaldin.eventsourcing.commands.VisitClientCommand;
import ru.shaldin.eventsourcing.events.CreatedClientEvent;
import ru.shaldin.eventsourcing.events.CreatedSubscriptionEvent;
import ru.shaldin.eventsourcing.events.Event;
import ru.shaldin.eventsourcing.events.ExtendedSubscriptionEvent;
import ru.shaldin.eventsourcing.projections.ManagerProjection;
import ru.shaldin.eventsourcing.projections.ReportProjection;
import ru.shaldin.eventsourcing.projections.TurnstileProjection;
import ru.shaldin.eventsourcing.projectors.ClientProjector;
import ru.shaldin.eventsourcing.projectors.ReportProjector;
import ru.shaldin.eventsourcing.queries.AverageVisitCountQuery;
import ru.shaldin.eventsourcing.queries.AverageVisitDurationQuery;
import ru.shaldin.eventsourcing.queries.IsSubscriptionAvailableQuery;
import ru.shaldin.eventsourcing.repository.ClientReadRepository;
import ru.shaldin.eventsourcing.repository.EventStore;
import ru.shaldin.eventsourcing.repository.ReportEventStore;
import ru.shaldin.eventsourcing.repository.ReportReadRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventSourcingTests {
	private EventStore eventStore;
	private ReportEventStore reportEventStore;
	private ClientReadRepository clientReadRepository;
	private ReportReadRepository reportReadRepository;
	private ClientProjector clientProjector;
	private ReportProjector reportProjector;
	private ManagerProjection managerProjection;
	private ReportProjection reportProjection;
	private TurnstileProjection turnstileProjection;
	private ManagerAggregate managerAggregate;
	private TurnstileAggregate turnstileAggregate;

	@BeforeEach
	public void setUp() {
		eventStore = new EventStore();
		reportEventStore = new ReportEventStore();
		clientReadRepository = new ClientReadRepository();
		reportReadRepository = new ReportReadRepository();
		clientProjector = new ClientProjector(clientReadRepository);
		reportProjector = new ReportProjector(reportReadRepository);
		managerProjection = new ManagerProjection(clientReadRepository);
		reportProjection = new ReportProjection(reportReadRepository);
		turnstileProjection = new TurnstileProjection(clientReadRepository);
		managerAggregate = new ManagerAggregate(eventStore);
		turnstileAggregate = new TurnstileAggregate(reportEventStore);
	}

	@Test
	public void create_client__01() throws Exception {
		int userId = 1;
		CreateClientCommand createClientCommand = new CreateClientCommand(userId, "name");
		List<Event> events = managerAggregate.handleCreateClientCommand(createClientCommand);
		clientProjector.project(userId, events);
		List<Event> clientEvents = eventStore.getClientEvents(1);
		assertEquals(clientEvents, events);
		assertEquals(1, events.size());
		assertEquals("name", ((CreatedClientEvent) events.get(0)).getName());
	}

	@Test
	public void create_subscription__02() throws Exception {
		int subscriptionId = 1;
		CreateSubscriptionCommand createSubscriptionCommand = new CreateSubscriptionCommand(1, 1);
		List<Event> events = managerAggregate.handleCreateSubscriptionCommand(createSubscriptionCommand);
		clientProjector.project(subscriptionId, events);
		List<Event> subscriptionEvents = eventStore.getSubscriptionEvents(1);
		assertEquals(subscriptionEvents, events);
		assertEquals(1, events.size());
		assertEquals(1, ((CreatedSubscriptionEvent) events.get(0)).getSubscriptionId());
	}

	@Test
	public void is_available__03() throws Exception {
		int subscriptionId = 1;
		CreateSubscriptionCommand createSubscriptionCommand = new CreateSubscriptionCommand(1, 1);
		List<Event> events = managerAggregate.handleCreateSubscriptionCommand(createSubscriptionCommand);
		clientProjector.project(subscriptionId, events);
		List<Event> subscriptionEvents = eventStore.getSubscriptionEvents(1);
		boolean result = turnstileProjection.handle(new IsSubscriptionAvailableQuery(subscriptionId));
		assertTrue(result);
	}

	@Test
	public void extend_subscription__04() throws Exception {
		int subscriptionId = 1;
		CreateSubscriptionCommand createSubscriptionCommand = new CreateSubscriptionCommand(1, 1);
		List<Event> events1 = managerAggregate.handleCreateSubscriptionCommand(createSubscriptionCommand);
		clientProjector.project(subscriptionId, events1);
		ExtendSubscriptionCommand extendSubscriptionCommand = new ExtendSubscriptionCommand(subscriptionId);
		List<Event> events2 = managerAggregate.handleExtendSubscription(extendSubscriptionCommand);
		clientProjector.project(subscriptionId, events2);
		List<Event> subscriptionEvents = eventStore.getSubscriptionEvents(1);
		List<Event> events = new ArrayList<>();
		events.addAll(events1);
		events.addAll(events2);
		assertEquals(subscriptionEvents, events);
	}

	@Test
	public void visit__05() throws Exception {
		VisitClientCommand command = new VisitClientCommand(new Date(), new Date());
		List<Event> events = turnstileAggregate.handleSaveVisitCommand(command);
		reportProjector.project(events);
		List<Event> subscriptionEvents = reportEventStore.getEvents(new Date());
		assertEquals(subscriptionEvents, events);
	}

	@Test
	public void avg_count__06() throws Exception {
		VisitClientCommand command = new VisitClientCommand(new Date(), new Date());
		List<Event> events1 = turnstileAggregate.handleSaveVisitCommand(command);
		List<Event> events2 = turnstileAggregate.handleSaveVisitCommand(command);
		reportProjector.project(events1);
		reportProjector.project(events2);
		AverageVisitCountQuery query = new AverageVisitCountQuery();
		double result = reportProjection.handle(query);
		assertEquals(2., result);
	}

	@Test
	public void avg_duration__07() throws Exception {
		VisitClientCommand command = new VisitClientCommand(new Date(), new Date());
		List<Event> events1 = turnstileAggregate.handleSaveVisitCommand(command);
		List<Event> events2 = turnstileAggregate.handleSaveVisitCommand(command);
		reportProjector.project(events1);
		reportProjector.project(events2);
		AverageVisitDurationQuery query = new AverageVisitDurationQuery();
		double result = reportProjection.handle(query);
		assertEquals(0., result);
	}

}
