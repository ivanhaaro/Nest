package com.nidito.nest.user;
import org.junit.jupiter.api.Test;

import com.nidito.nest.user.domain.entity.User;
import com.nidito.nest.user.domain.entity.UserDto;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    @Test
    void testUserDtoCreation() {
        User user = new User();
        UUID testId = UUID.randomUUID();
        user.setId(testId);
        user.setName("Test Name");
        user.setLastname("Test Lastname");
        user.setMail("test@mail.com");
        user.setUsername("testuser");
        user.setPassword("password");

        UserDto userDto = new UserDto(user);

        assertEquals(testId, userDto.getId());
        assertEquals("Test Name", userDto.getName());
        assertEquals("Test Lastname", userDto.getLastname());
        assertEquals("test@mail.com", userDto.getMail());
        assertEquals("testuser", userDto.getUsername());
        assertEquals("password", userDto.getPassword());
    }

}
