package com.nidito.nest.publication.domain.entity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.nidito.nest.publication.domain.entity.Note;
import com.nidito.nest.shared.Views;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NoteDto extends PublicationDto {
    
    @JsonView({Views.Retrieve.class, Views.Create.class})
    private String title;

    @JsonView({Views.Retrieve.class, Views.Create.class})
    private String message;

    public NoteDto(Note note) {

        super(note);
        this.title = note.getTitle();
        this.message = note.getMessage();
    }

    public Note toEntity() {

        return new Note(this);
    }
}
