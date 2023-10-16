package com.nidito.nest.publication.domain.entity.dto;
import com.fasterxml.jackson.annotation.JsonView;
import com.nidito.nest.publication.domain.entity.Note;
import com.nidito.nest.publication.domain.entity.Picture;
import com.nidito.nest.shared.Views;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PictureDto extends PublicationDto {


    @JsonView({Views.Retrieve.class, Views.Create.class})
    private String description;

    @JsonView({Views.Retrieve.class, Views.Create.class})
    private String url;

    public PictureDto(Picture picture) {

        super(picture);
        this.description = picture.getDescription();
        this.url = picture.getUrl();
    }

    public Picture toEntity() {

        return new Picture(this);
    }
}

