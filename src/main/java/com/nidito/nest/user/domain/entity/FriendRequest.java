package com.nidito.nest.user.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Table(name = "friend_requests")
@NoArgsConstructor
public class FriendRequest {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "origin_user_id")
    private User origin;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receiver_user_id")
    private User receiver;

    public FriendRequest(User origin, User receiver) {
        
        this.origin = origin;
        this.receiver = receiver;
    }
}
