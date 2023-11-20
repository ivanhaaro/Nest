package com.nidito.nest.letter.domain.entity;

import java.util.Date;
import java.util.UUID;

import com.nidito.nest.user.domain.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "letter_table")
@NoArgsConstructor
public class Letter {
 
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String title;
    private String text;
    private Date date;    
    private boolean opened;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "origin_user_id")
    private User origin;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receiver_user_id")
    private User receiver;

    public Letter(LetterDto letterDto) {

        this.id = letterDto.getId();
        this.title = letterDto.getTitle();
        this.text = letterDto.getText();
        this.date = letterDto.getDate();
        this.opened = false;
    }

}
