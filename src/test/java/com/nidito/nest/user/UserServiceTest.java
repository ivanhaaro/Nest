package com.nidito.nest.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import com.nidito.nest.user.domain.UserService;
import com.nidito.nest.user.domain.entity.FriendRequest;
import com.nidito.nest.user.domain.entity.User;
import com.nidito.nest.user.domain.entity.UserDto;
import com.nidito.nest.user.infrastructure.UserRepository;
import com.nidito.nest.user.infrastructure.FriendRequestRepository;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FriendRequestRepository friendRequestRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    protected User createUser(UUID uuid, String name, String mail, String password, String username)
    {
        User newUser = new User();
        newUser.setId(uuid);
        newUser.setName(name);
        newUser.setMail(mail);
        newUser.setPassword(password);
        newUser.setUsername(username);
        return newUser;
    }

    @Test
    public void testGetUsers() {
        List<User> expectedUsers = Arrays.asList(
            createUser(UUID.randomUUID(), "Manolo", "manolo@gmail.com", "Manolo123?", "manolobombastic"), 
            createUser(UUID.randomUUID(), "Paco", "paco@gmail.com", "Paco123?", "paquitosanz")
            );
        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.getUsers();

        assertEquals(expectedUsers, actualUsers);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById() {
        UUID userId = UUID.randomUUID();
        User expectedUser = createUser(userId, "Manolo", "manolo@gmail.com", "Manolo123?", "manolobombastic");
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        User actualUser = userService.getUserById(userId);

        assertEquals(expectedUser, actualUser);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testCreateUser() {
        User newUser = createUser(UUID.randomUUID(), "Manolo", "manolo@gmail.com", "Manolo123?", "manolobombastic");
        when(userRepository.save(newUser)).thenReturn(newUser);

        User createdUser = userService.createUser(newUser);

        assertEquals(newUser, createdUser);
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    public void testUpdateUser() {
        UUID userId = UUID.randomUUID();
        User updatedUser = createUser(userId, "Manolo23", "manolo@gmail.com", "Manolo123?", "manolobombastic23");

        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        User resultUser = userService.updateUser(updatedUser, userId);

        assertEquals(updatedUser, resultUser);
        verify(userRepository, times(1)).save(updatedUser);
    }

    @Test
    public void testAddFriend() {
        UUID userId = UUID.randomUUID();
        UUID friendId = UUID.randomUUID();
        User user = createUser(userId, "Manolo", "manolo@gmail.com", "Manolo123?", "manolobombastic");
        User friend = createUser(friendId, "Paco", "paco@gmail.com", "Paco123?", "paquitosanz");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findById(friendId)).thenReturn(Optional.of(friend));
        when(userRepository.save(user)).thenReturn(user);

        User userWithFriend = userService.addFriend(userId, friendId);

        assertTrue(userWithFriend.getFriends().contains(friend));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDeleteFriend() {
        UUID userId = UUID.randomUUID();
        UUID friendId = UUID.randomUUID();
        User user = createUser(userId, "Manolo", "manolo@gmail.com", "Manolo123?", "manolobombastic");
        User friend = createUser(friendId, "Paco", "paco@gmail.com", "Paco123?", "paquitosanz");
        user.getFriends().add(friend);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findById(friendId)).thenReturn(Optional.of(friend));
        when(userRepository.save(user)).thenReturn(user);

        User userWithoutFriend = userService.deleteFriend(userId, friendId);

        assertFalse(userWithoutFriend.getFriends().contains(friend));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDeleteUser() {
        UUID userId = UUID.randomUUID();
        User user = createUser(userId, "Paco", "paco@gmail.com", "Paco123?", "paquitosanz");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }


    @Test
    public void testGetUserByUsername() {
        String username = "testUsername";
        User user = createUser(UUID.randomUUID(), "Paco", "paco@gmail.com", "Paco123?", username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User resultUser = userService.getUserByUsername(username);

        assertEquals(user, resultUser);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testSendFriendRequest() {
        UUID originUserId = UUID.randomUUID();
        UUID friendId = UUID.randomUUID();
        User originUser = createUser(originUserId, "Manolo", "manolo@gmail.com", "Manolo123?", "manolobombastic");
        User friend = createUser(friendId, "Paco", "paco@gmail.com", "Paco123?", "paquitosanz");
        when(userRepository.findById(originUserId)).thenReturn(Optional.of(originUser));
        when(userRepository.findById(friendId)).thenReturn(Optional.of(friend));
        when(friendRequestRepository.save(any(FriendRequest.class))).thenReturn(new FriendRequest());

        String response = userService.sendFriendRequest(originUserId, friendId);

        assertEquals("Request sent successfully", response);
        verify(friendRequestRepository, times(1)).save(any(FriendRequest.class));
    }

    @Test
    public void testGetAllFriendRequests() {
        UUID userId = UUID.randomUUID();
        User originUser = createUser(userId, "Manolo", "manolo@gmail.com", "Manolo123?", "manolobombastic");
        FriendRequest request1 = new FriendRequest();
        request1.setOrigin(originUser);

        FriendRequest request2 = new FriendRequest();
        request2.setOrigin(originUser);

        List<FriendRequest> friendRequests = Arrays.asList(request1, request2);
        when(friendRequestRepository.findByReceiverId(userId)).thenReturn(friendRequests);

        when(userRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(originUser));

        List<UserDto> result = userService.getAllFriendRequests(userId);
        assertEquals(result.get(0).getId(), friendRequests.get(0).getOrigin().getId());
        assertEquals(result.get(1).getId(), friendRequests.get(1).getOrigin().getId());

    }

    @Test
    public void testDeleteFriendRequest() {
        UUID userId = UUID.randomUUID();
        UUID friendId = UUID.randomUUID();
        User user = createUser(userId, "Manolo", "manolo@gmail.com", "Manolo123?", "manolobombastic");
        User friend = createUser(friendId, "Paco", "paco@gmail.com", "Paco123?", "paquitosanz");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findById(friendId)).thenReturn(Optional.of(friend));
        doNothing().when(friendRequestRepository).deleteByOriginAndReceiver(any(User.class), any(User.class));

        String response = userService.deleteFriendRequest(userId, friendId);

        assertEquals("Friend request successfully removed!", response);
        verify(friendRequestRepository, times(1)).deleteByOriginAndReceiver(any(User.class), any(User.class));
    }


}
