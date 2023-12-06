package com.nidito.nest.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.nidito.nest.user.domain.UserService;
import com.nidito.nest.user.domain.entity.FriendRequest;
import com.nidito.nest.user.domain.entity.User;
import com.nidito.nest.user.domain.entity.UserDto;
import com.nidito.nest.user.infrastructure.FriendRequestRepository;
import com.nidito.nest.user.infrastructure.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private FriendRequestRepository friendRequestRepository;

    private User user;
    private User friend;

    @BeforeEach void setup()
    {
        user = createUser(UUID.randomUUID(), "John", "Doe", "john.doe@email.com", "johndoe", "password123");
        friend = createUser(UUID.randomUUID(), "Jane", "Smith", "jane.smith@email.com", "janesmith", "password456");
        List<User> users = List.of(user, friend);
        userRepository.saveAll(users);
    }

    @Test
    void testGetUsers() {
        List<User> expectedUsers = Arrays.asList(
            createUser(UUID.randomUUID(), "John", "Doe", "john.doe@email.com", "johndoe", "password123"),
            createUser(UUID.randomUUID(), "Jane", "Smith", "jane.smith@email.com", "janesmith", "password456")
        );
        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> users = userService.getUsers();

        assertEquals(expectedUsers, users);
    }

    @Test
    void testGetUserById() {
        UUID testId = user.getId();
        when(userRepository.findById(testId)).thenReturn(Optional.of(user));

        User expectedUser = userService.getUserById(testId);

        assertEquals(expectedUser, user);

        when(userRepository.findById(testId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(testId));
    }
    
    @Test
    void testGetFriends() {
        UUID testId = UUID.randomUUID();
        User user = new User(); // Initialize with test data and friends
        when(userRepository.findById(testId)).thenReturn(Optional.of(user));
    
        Set<User> friends = userService.getFriends(testId);
    
        assertEquals(user.getFriends(), friends);
    }

    @Test
    void testCreateUser() {
        User newUser = new User(); // Initialize with test data
        when(userRepository.save(newUser)).thenReturn(newUser);
    
        User savedUser = userService.createUser(newUser);
    
        assertEquals(newUser, savedUser);
    }

    @Test
    void testUpdateUser() {
        User updatedUser = createUser(UUID.randomUUID(), "John", "Doe", "john.doe@email.com", "johndoe", "password123");
        when(userRepository.existsById(updatedUser.getId())).thenReturn(true);
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);
    
        User savedUser = userService.updateUser(updatedUser, updatedUser.getId());
    
        assertEquals(updatedUser, savedUser);
    
        when(userRepository.existsById(updatedUser.getId())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> userService.updateUser(updatedUser, updatedUser.getId()));
    }

    @Test
    void testAddFriend() {
    
        User updatedUser = userService.addFriend(user.getId(), friend.getId());
        assertTrue(updatedUser.getFriends().contains(friend));
    }

    @Test
    void testDeleteFriend() {

        user.addFriend(friend);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.findById(friend.getId())).thenReturn(Optional.of(friend));
    
        User updatedUser = userService.deleteFriend(user.getId(), friend.getId());
    
        assertFalse(updatedUser.getFriends().contains(friend));
    }

    @Test
    void testDeleteUser() {
        User expectedUser = createUser(UUID.randomUUID(), "John", "Doe", "john.doe@email.com", "johndoe", "password123");
        when(userRepository.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));
    
        userService.deleteUser(expectedUser.getId());
    
        assertNull(userRepository.findById(expectedUser.getId()));
    }

    @Test
    void testGetUserByUsername() {
        String username = "johndoe";
        User expectedUser = createUser(UUID.randomUUID(), "John", "Doe", "john.doe@email.com", "johndoe", "password123");
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));
    
        User user = userService.getUserByUsername(username);
    
        assertEquals(expectedUser, user);
    
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.getUserByUsername(username));
    }

    @Test
    void testDeleteFriendRequest() {
        userService.sendFriendRequest(user.getId(), friend.getId());
    
        String result = userService.deleteFriendRequest(user.getId(), friend.getId());
    
        assertEquals("Friend request successfully removed!", result);
    }
    

    @Test
    void testGetAllFriendRequests() {
        User user3 = createUser(UUID.randomUUID(), "Alice", "Johnson", "alice.johnson@email.com", "alicejohnson", "password789");
        
        userService.sendFriendRequest(user.getId(), friend.getId());
        userService.sendFriendRequest(user.getId(), user3.getId());

        List<UserDto> usersRequests = userService.getAllFriendRequests(user.getId());
        
        assertEquals(friend, usersRequests.get(0));
        assertEquals(user3, usersRequests.get(1)); 

        FriendRequest f1 = new FriendRequest(user, friend);
        FriendRequest f2 = new FriendRequest(user, user3);
        List<FriendRequest> requests = new ArrayList<>();
        requests.addAll(List.of(f1,f2));
        
        when(friendRequestRepository.findByOriginId(user.getId())).thenReturn(requests);

        
        assertEquals(friend, requests.get(0).getReceiver());
        assertEquals(user3, requests.get(1).getReceiver());
        assertEquals(user, requests.get(0).getOrigin());

        assertEquals(usersRequests.size(), requests.size());
    }


    @Test
    void testSendFriendRequest() {

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.findById(friend.getId())).thenReturn(Optional.of(friend));

        String result = userService.sendFriendRequest(user.getId(), friend.getId());

        assertEquals("Request sent successfully", result);

        when(friendRequestRepository.save(any(FriendRequest.class))).thenThrow(RuntimeException.class);
        result = userService.sendFriendRequest(user.getId(), friend.getId());
        assertTrue(result.startsWith("Error while sending the request"));
    }

    private static User createUser(UUID id, String name, String lastname, String mail, String username, String password) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setLastname(lastname);
        user.setMail(mail);
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

}

