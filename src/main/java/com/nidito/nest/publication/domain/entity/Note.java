package com.nidito.nest.publication.domain.entity;

import com.nidito.nest.publication.domain.entity.dto.NoteDto;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Note extends Publication {
    
    private String title;
    private String message;

    public Note(NoteDto noteDto) {

        super(noteDto);
        this.title = noteDto.getTitle();
        this.message = noteDto.getMessage();
    }

    public NoteDto toDto() {

        return new NoteDto(this);
    }
}
