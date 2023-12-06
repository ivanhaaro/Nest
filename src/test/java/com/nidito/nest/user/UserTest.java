package com.nidito.nest.user;
import org.junit.jupiter.api.Test;

import com.nidito.nest.user.domain.entity.User;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserProperties() {
        // Create a new User object and set its properties
        User user = new User();
        UUID testId = UUID.randomUUID();
        user.setId(testId);
        user.setName("Test Name");
        user.setLastname("Test Lastname");
        user.setMail("test@mail.com");
        user.setUsername("testuser");
        user.setPassword("password");

        // Assert that the getters return the correct values
        assertEquals(testId, user.getId());
        assertEquals("Test Name", user.getName());
        assertEquals("Test Lastname", user.getLastname());
        assertEquals("test@mail.com", user.getMail());
        assertEquals("testuser", user.getUsername());
        assertEquals("password", user.getPassword());
    }

    @Test
    void testAddFriend() {
        User user1 = new User();
        User user2 = new User();
        user1.addFriend(user2);

        assertTrue(user1.getFriends().contains(user2));
        assertTrue(user2.getFriends().contains(user1));
    }

    @Test
    void testDeleteFriend() {
        User user1 = new User();
        User user2 = new User();
        user1.addFriend(user2);
        user1.deleteFriend(user2);

        assertFalse(user1.getFriends().contains(user2));
        assertFalse(user2.getFriends().contains(user1));
    }
}
