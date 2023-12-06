package com.nidito.nest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nidito.nest.publication.application.PublicationController;
import com.nidito.nest.publication.domain.entity.dto.NoteDto;
import com.nidito.nest.user.domain.entity.User;

import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class NoteTests extends NestApplicationTests {

    @Autowired
    private PublicationController controller;

    @Test
    void contextLoads() {
        assertNotNull(controller);
    } 

    @Test
    void creatingNote() throws Exception {
        
        NoteDto note = new NoteDto();
        note.setTitle("This is the title");
        note.setMessage("This is the message");
        note.setDate(new Date());

        User user = getDefaultUser();

        note.setOwnerId(user.getId());
        note.setWatchers(List.of(user.getId()));

        ObjectMapper objectMapper = new ObjectMapper();
        String noteJson = objectMapper.writeValueAsString(note);

        mvc.perform(post("/publications/note").contentType(MediaType.APPLICATION_JSON).content(noteJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("This is the title")))
                .andExpect(jsonPath("$.message", is("This is the message")))
                .andExpect(jsonPath("$.ownerId", is(user.getId().toString())));
    }

    @Test
    void gettingNotesSentToMe() throws Exception {
        
        NoteDto note = new NoteDto();
        note.setTitle("This is the title");
        note.setMessage("This is the message");
        note.setDate(new Date());

        User owner = getDefaultUser();
        User watcher = getAuxUser();

        note.setOwnerId(owner.getId());
        note.setWatchers(List.of(watcher.getId()));

        ObjectMapper objectMapper = new ObjectMapper();
        String noteJson = objectMapper.writeValueAsString(note);

        mvc.perform(post("/publications/note").contentType(MediaType.APPLICATION_JSON).content(noteJson));

        mvc.perform(get("/publications/" + watcher.getId().toString() + "/feed"))
            .andExpect(jsonPath("$[0].title", is("This is the title")))
            .andExpect(jsonPath("$[0].message", is("This is the message")))
            .andExpect(jsonPath("$[0].ownerId", is(owner.getId().toString())));

    }
   
}
