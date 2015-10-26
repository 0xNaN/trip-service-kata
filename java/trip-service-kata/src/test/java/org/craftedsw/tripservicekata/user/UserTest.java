package org.craftedsw.tripservicekata.user;

import static org.junit.Assert.*;

import org.craftedsw.tripservicekata.trip.UserBuilder;
import org.junit.Test;

public class UserTest {
	private static final User BOB = new User();
	private static final User PAUL = new User();

	@Test
	public void 
	should_informs_when_users_are_not_friends() {
		User user = UserBuilder.aUser()
				.friendsWith(BOB)
				.build();
		assertFalse(user.isFriendWith(PAUL));
	}

	@Test
	public void
	should_informs_when_users_are_friends() {
		User user = UserBuilder.aUser()
				.friendsWith(BOB, PAUL)
				.build();
		assertTrue(user.isFriendWith(PAUL));	
	}
}
