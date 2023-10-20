package com.nidito.nest.publication.domain.entity;
import com.nidito.nest.publication.domain.entity.dto.NoteDto;

import com.nidito.nest.publication.domain.entity.dto.PictureDto;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Picture extends Publication {

    private String description;
    private String url;

    public Picture(PictureDto pictureDto) {

        super(pictureDto);
        this.description = pictureDto.getDescription();
        this.url = pictureDto.getUrl();
    }

    public PictureDto toDto() {

        return new PictureDto(this);
    }
}
