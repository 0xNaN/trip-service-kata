package org.craftedsw.tripservicekata.trip;

import static org.craftedsw.tripservicekata.trip.UserBuilder.aUser;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceTest {
	private static final User GUEST = null;
	private static final User UNUSED_USER = null;
	private static final User REGISTERED_USER = new User();
	private static final User ANOTHER_FRIEND = new User();
	private static final Trip TO_BRAZIL = new Trip();
	private static final Trip TO_LONDON = new Trip();
	
	@Mock private TripDAO tripDAO;
	@InjectMocks @Spy TripService tripService = new TripService();
	
	@Test(expected = UserNotLoggedInException.class) public void
	should_throw_an_exception_when_user_is_not_logged_in() {
		tripService.getFriendTrips(UNUSED_USER, GUEST);
	}

	@Test public void
	should_not_returns_any_trips_when_users_are_not_friends() {
		User friend = aUser()
							.friendsWith(ANOTHER_FRIEND)
							.withTrips(TO_BRAZIL)
							.build();
		
		List<Trip> friendTrips = tripService.getFriendTrips(friend, REGISTERED_USER);
		assertEquals(friendTrips.size(), 0);
	}
	
	@Test public void
	should_returns_friend_trips_when_users_are_friends() {		
		User friend = aUser()
						.friendsWith(ANOTHER_FRIEND, REGISTERED_USER)
						.withTrips(TO_BRAZIL, TO_LONDON)
						.build();
		
		when(tripDAO.tripsBy(friend)).thenReturn(friend.trips());
		
		List<Trip> friendTrips = tripService.getFriendTrips(friend, REGISTERED_USER);
		assertEquals(friendTrips.size(), 2);		
	}
	
}
