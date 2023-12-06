package com.nidito.nest.user;

import org.junit.jupiter.api.Test;

import com.nidito.nest.user.domain.entity.FriendRequest;
import com.nidito.nest.user.domain.entity.User;

import static org.junit.jupiter.api.Assertions.*;

class FriendRequestTest {

    @Test
    void testFriendRequestCreation() {
        User user1 = new User(); // assuming default constructor is sufficient for test
        User user2 = new User();
        FriendRequest friendRequest = new FriendRequest(user1, user2);

        assertNotNull(friendRequest.getId());
        assertEquals(user1, friendRequest.getOrigin());
        assertEquals(user2, friendRequest.getReceiver());
    }
}
