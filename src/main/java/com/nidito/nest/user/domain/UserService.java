package com.nidito.nest.user.domain;

import java.util.List;
import java.util.UUID;

import com.nidito.nest.user.domain.entity.User;

public interface UserService {
    List<User> getUsers();
    User createUser(User user);
    User getUserById(UUID id);
    User updateUser(UUID id, User user);
    void deleteUser(UUID id);
}
