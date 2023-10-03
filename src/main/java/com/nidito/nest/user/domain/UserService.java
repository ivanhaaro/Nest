package com.nidito.nest.user.domain;

import java.util.List;

import com.nidito.nest.user.domain.entity.User;

public interface UserService {
    List<User> getUsers();
    User createUser(User user);
}
