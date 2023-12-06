package com.nidito.nest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nidito.nest.letter.application.LetterController;
import com.nidito.nest.letter.domain.entity.LetterDto;
import com.nidito.nest.user.domain.entity.User;

import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class LetterTests extends NestApplicationTests {

    @Autowired
    private LetterController controller;

    @Test
    void contextLoads() {
        assertNotNull(controller);
    } 

    @Test
    void creatingLetter() throws Exception {
        
        LetterDto letter = new LetterDto();
        letter.setTitle("MyTitle");
        letter.setText("MyText");
        letter.setOpened(false);
        letter.setDate(new Date());

        User user = getDefaultUser();

        letter.setOriginUserId(user.getId());
        letter.setReceiverUserId(user.getId());

        ObjectMapper objectMapper = new ObjectMapper();
        String letterJson = objectMapper.writeValueAsString(letter);

        mvc.perform(post("/letter").contentType(MediaType.APPLICATION_JSON).content(letterJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("MyTitle")))
                .andExpect(jsonPath("$.text", is("MyText")))
                .andExpect(jsonPath("$.opened", is(false)));
    }

    @Test
    void openingLetter() throws Exception {
        
        LetterDto letter = new LetterDto();
        letter.setTitle("MyTitle");
        letter.setText("MyText");
        letter.setOpened(false);
        letter.setDate(new Date());

        User user = getDefaultUser();

        letter.setOriginUserId(user.getId());
        letter.setReceiverUserId(user.getId());

        ResponseEntity<LetterDto> res = controller.createLetter(letter);

        mvc.perform(put("/letter/open/" + res.getBody().getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("MyTitle")))
                .andExpect(jsonPath("$.text", is("MyText")))
                .andExpect(jsonPath("$.opened", is(true)));
    }
    
}
