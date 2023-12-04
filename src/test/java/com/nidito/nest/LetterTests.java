package com.nidito.nest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.Optional;

import com.nidito.nest.letter.application.LetterController;
import com.nidito.nest.letter.domain.entity.LetterDto;
import com.nidito.nest.user.domain.entity.User;
import com.nidito.nest.user.infrastructure.UserRepository;

import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class LetterTests {

    @Autowired
    private LetterController controller;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mvc;

    @Test
    void contextLoads() {
        assertNotNull(controller);
    } 

    @Test
    void creatingNote() throws Exception {
        
        LetterDto letter = new LetterDto();
        letter.setTitle("MyTitle");
        letter.setText("MyText");
        letter.setOpened(false);
        letter.setDate(new Date());

        User user = getDefaultUser();

        letter.setOriginUserId(user.getId());
        letter.setReceiverUserId(user.getId());

        ResponseEntity<LetterDto> res = controller.createLetter(letter);

        mvc.perform(get("/letter/" + res.getBody().getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("MyTitle")))
                .andExpect(jsonPath("$.text", is("MyText")));
    }

    private User getDefaultUser() {

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
    
}
