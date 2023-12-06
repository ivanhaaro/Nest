package com.nidito.nest;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.nidito.nest.user.domain.entity.User;
import com.nidito.nest.user.infrastructure.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
class NestApplicationTests {

	@Autowired
    private UserRepository userRepository;

	@Autowired
    protected MockMvc mvc;

	@Test
	void contextLoads() {
	}

	protected User getDefaultUser() {

        Optional<User> res = userRepository.findByUsername("ivanharo");
        if (res.isPresent()) return res.get();

        User user = new User(); 
        user.setName("Ivan");
        user.setLastname("Haro");
        user.setMail("ivanharo@gmail.com");
        user.setUsername("ivanharo");
        user.setPassword("12345");

        return userRepository.save(user);
    }

    protected User getAuxUser() {

        Optional<User> res = userRepository.findByUsername("guillem");
        if (res.isPresent()) return res.get();

        User user = new User(); 
        user.setName("Guillem");
        user.setLastname("Fornet");
        user.setMail("guillem@gmail.com");
        user.setUsername("guillem");
        user.setPassword("12345");

        return userRepository.save(user);
    }

}
