package org.craftedsw.tripservicekata.trip;

import static org.junit.Assert.*;

import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.hamcrest.generator.HamcrestFactoryWriter;
import org.junit.Before;
import org.junit.Test;

public class TripServiceTest {
	private static final User GUEST = null;
	private static final User UNUSED_USER = null;
	private static final User REGISTERED_USER = new User();
	private static final User ANOTHER_FRIEND = new User();
	private static final Trip TO_BRAZIL = new Trip();
	private static final Trip TO_LONDON = new Trip();
	
	private User loggedInUser;
	private TestableTripService tripService;
	
	@Before public void
	initialize() {
		tripService = new TestableTripService();	
		loggedInUser = REGISTERED_USER;				
	}
	
	@Test(expected = UserNotLoggedInException.class) public void
	should_throw_an_exception_when_user_is_not_logged_in() {
		loggedInUser = GUEST;
		
		tripService.getTripsByUser(UNUSED_USER);
	}

	@Test public void
	should_not_returns_any_trips_when_users_are_not_friends() {
		User friend = new User();
		friend.addFriend(ANOTHER_FRIEND);
		friend.addTrip(TO_BRAZIL);
		
		List<Trip> friendTrips = tripService.getTripsByUser(friend);
		assertEquals(friendTrips.size(), 0);
	}
	
	@Test public void
	should_returns_friend_trips_when_users_are_friends() {
		User friend = new User();
		friend.addFriend(ANOTHER_FRIEND);
		friend.addFriend(loggedInUser);
		friend.addTrip(TO_BRAZIL);
		friend.addTrip(TO_LONDON);
		
		List<Trip> friendTrips = tripService.getTripsByUser(friend);
		assertEquals(friendTrips.size(), 2);		
	}
	
	private class TestableTripService extends TripService {
		@Override
		protected User getLoggedInUser() {
			return loggedInUser;
		}

		@Override
		protected List<Trip> tripsBy(User user) {
			return user.trips();
		}
		
	}
}
