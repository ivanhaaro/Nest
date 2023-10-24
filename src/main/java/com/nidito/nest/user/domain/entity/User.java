package com.nidito.nest.user.domain.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_table", uniqueConstraints={@UniqueConstraint(columnNames={"username", "mail"})})
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String lastname;
    private String mail;
    private String username;
    private String password;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<User> friends = new HashSet<>();

    public User(UserDto userDto) {

        this.id = userDto.getId();
        this.name = userDto.getName();
        this.lastname = userDto.getLastname();
        this.mail = userDto.getMail();
        this.username = userDto.getUsername();
        this.password = userDto.getPassword();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addFriend(User friend) {

        this.getFriends().add(friend);
        friend.getFriends().add(this);
    }

    public void deleteFriend(User friend) {
        
        this.getFriends().remove(friend);
        friend.getFriends().remove(this);
    }
}
