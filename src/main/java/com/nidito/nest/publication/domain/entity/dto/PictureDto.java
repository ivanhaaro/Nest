package com.nidito.nest.publication.domain.entity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.nidito.nest.publication.domain.entity.Picture;
import com.nidito.nest.shared.Views;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PictureDto extends PublicationDto {

    @JsonView({ Views.Retrieve.class, Views.Create.class })
    private String description;

    @JsonView(Views.Retrieve.class)
    private String url;

    @JsonView(Views.Create.class)
    private MultipartFile image;

    public PictureDto(Picture picture) {
        super(picture);
        this.description = picture.getDescription();
        this.url = picture.getUrl();
    }

    public Picture toEntity() {
        return new Picture(this);
    }
}
