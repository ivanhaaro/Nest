package com.nidito.nest.user.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nidito.nest.user.domain.entity.User;
import com.nidito.nest.user.infrastructure.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id)
    {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) return user.get();
        else throw new EntityNotFoundException("No existe un usuario con id " + id); 
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(long id, User user) {
        if(userRepository.countByUsernameAndDifferentId(user.getUsername(), id) != 0)
            throw new EntityNotFoundException("Username already exists"); // TODO EXCEPTION MANAGEMENT
        return userRepository.findById(id)
            .map(existingUser -> {
                existingUser.setName(user.getName());
                existingUser.setLastname(user.getLastname());
                existingUser.setMail(user.getMail());
                existingUser.setUsername(user.getUsername());
                existingUser.setPassword(user.getPassword());
                return userRepository.save(existingUser);
            })
            .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    public void deleteUser(long id)
    {
        if(!userRepository.existsById(id)) throw new EntityNotFoundException("User not found with id " + id);
        userRepository.deleteById(id);
    }
    
}
