package com.nidito.nest.user.domain;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nidito.nest.user.domain.entity.User;
import com.nidito.nest.user.infrastructure.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {

        return userRepository.findAll();
    }

    public User getUserById(UUID id) {

        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw new EntityNotFoundException("User not found with id " + id); 
        else return user.get();
    }

    public Set<User> getFriends(UUID id) {

        User user = this.getUserById(id);
        return user.getFriends();
    }
 
    public User createUser(User user){

        return userRepository.save(user);
    }

    public User updateUser(User user, UUID id) {
        
        if(!isValidUser(id)) throw new EntityNotFoundException("User not found with id " + id); 
        user.setId(id);
        return userRepository.save(user);
    }

    public User addFriend(UUID userId, UUID friendId) {
        
        User user = this.getUserById(userId);
        User friend = this.getUserById(friendId);
        user.addFriend(friend);
        userRepository.save(user);
        return user;
    }

    public User deleteFriend(UUID userId, UUID friendId) {

        User user = this.getUserById(userId);
        User friend = this.getUserById(friendId);
        user.deleteFriend(friend);
        userRepository.save(user);
        return user;
    }

    public void deleteUser(UUID id) {

        User user = this.getUserById(id);

        for (User friend : user.getFriends()) {
            friend.getFriends().remove(user);
        }     
        user.getFriends().clear();
        userRepository.save(user);  
        userRepository.deleteById(id);
    }

    private boolean isValidUser(UUID id) {

        if(id == null || id.toString().isBlank()) return false;
        return userRepository.existsById(id);  
    }
    
}
