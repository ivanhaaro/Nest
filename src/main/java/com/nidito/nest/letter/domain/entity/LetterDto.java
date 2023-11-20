package com.nidito.nest.letter.domain.entity;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;
import com.nidito.nest.shared.Views;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LetterDto {
    
    @JsonView(Views.Retrieve.class)
    private UUID id;

    @JsonView({Views.Retrieve.class, Views.Create.class})
    private String title;

    @JsonView({Views.Retrieve.class, Views.Create.class})
    private String text;

    @JsonView({Views.Retrieve.class, Views.Create.class})
    private Date date;  

    @JsonView({Views.Retrieve.class, Views.Create.class})
    private UUID originUserId;

    @JsonView({Views.Retrieve.class, Views.Create.class})
    private UUID receiverUserId;

    @JsonView({Views.Retrieve.class})
    private boolean opened;

    public LetterDto(Letter letter) {

        this.id = letter.getId();
        this.title = letter.getTitle();
        this.text = letter.getText();
        this.date = letter.getDate();
        this.opened = letter.isOpened();
        this.originUserId = letter.getOrigin().getId();
        this.receiverUserId = letter.getReceiver().getId();
    }
}
