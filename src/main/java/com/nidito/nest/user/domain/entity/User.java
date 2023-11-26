package com.nidito.nest.user.domain.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.nidito.nest.capsule.domain.entity.Capsule;
import com.nidito.nest.diffusionList.domain.entity.DiffusionList;
import com.nidito.nest.publication.domain.entity.Publication;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_table")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String lastname;
    @Column(unique = true)
    private String mail;
    @Column(unique = true)
    private String username;
    private String password;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Publication> publications = new HashSet<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<DiffusionList> diffusionLists = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
        name = "friends_table",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"))
    private Set<User> friends = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
        name = "feed_table",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "publication_id", referencedColumnName = "id"))
    private Set<Publication> feed = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
        name = "user_capsule",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "capsule_id", referencedColumnName = "id")
    )
    private Set<Capsule> capsules;

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
