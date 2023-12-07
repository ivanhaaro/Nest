package com.nidito.nest;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.nidito.nest.capsule.application.CapsuleController;
import com.nidito.nest.capsule.domain.entity.CapsuleDto;
import com.nidito.nest.publication.application.PublicationController;
import com.nidito.nest.publication.domain.entity.dto.NoteDto;
import com.nidito.nest.user.domain.entity.User;

@SpringBootTest
public class CapsuleTests extends NestApplicationTests {

    @Autowired
    private CapsuleController capsuleController;

    @Autowired
    private PublicationController publicationController;

    @Test
    void contextLoads() {
        assertNotNull(capsuleController);
    } 

    @Test
    void creatingCapsule() throws Exception {

        CapsuleDto capsule = new CapsuleDto();
        capsule.setTitle("Capsule title");
        capsule.setDescription("This is a capsule");
        capsule.setOpenDate(new Date());

        User user = getDefaultUser();

        capsule.setMembers(List.of(user.getId()));

        ObjectMapper objectMapper = new ObjectMapper();
        String capsuleJson = objectMapper.writeValueAsString(capsule);
        mvc.perform(post("/capsules").contentType(MediaType.APPLICATION_JSON).content(capsuleJson))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.title", is("Capsule title")))
                        .andExpect(jsonPath("$.description", is("This is a capsule")))
                        .andExpect(jsonPath("$.members[0]", is(user.getId().toString())));
    }

    @Test
    void uploadingPublicationToCapsule() throws Exception {
        
        User user = getDefaultUser();

        //First, we must create the capsule
        CapsuleDto capsule = new CapsuleDto();
        capsule.setTitle("Capsule title");
        capsule.setDescription("This is a capsule");
        capsule.setOpenDate(new Date());

        capsule = capsuleController.createCapsule(capsule).getBody();

        //Second, we must create the publication (a note)
        NoteDto note = new NoteDto();
        note.setTitle("This is the title");
        note.setMessage("This is the message");
        note.setDate(new Date());
        note.setOwnerId(user.getId());

        note = (NoteDto) publicationController.createNote(note).getBody();

        //Now, we proceed with the test
        mvc.perform(post("/capsules/" + capsule.getId() + "/publications").param("publicationId", note.getId().toString()));

        mvc.perform(get("/capsules/" + capsule.getId() + "/publications"))
            .andExpect(jsonPath("$[0].title", is("This is the title")))
            .andExpect(jsonPath("$[0].message", is("This is the message")))
            .andExpect(jsonPath("$[0].ownerId", is(user.getId().toString())));
    }

    
}
