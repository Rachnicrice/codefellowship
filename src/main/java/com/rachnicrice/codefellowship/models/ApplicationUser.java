package com.rachnicrice.codefellowship.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
public class ApplicationUser implements UserDetails {
    //instance variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String username;
    String password;
    String firstName;
    String lastName;
    String img;
    String dob;
    String bio;

    @OneToMany(mappedBy = "user")
    List<Post> posts;

    @ManyToMany
    @JoinTable(
            name = "Followers",
            joinColumns = { @JoinColumn(name = "follower_id") },
            inverseJoinColumns = {@JoinColumn(name = "userBeingFollowed_id")}
    )
    List<ApplicationUser> usersIAmFollowing;

    @ManyToMany (mappedBy = "usersIAmFollowing")
    List<ApplicationUser> usersFollowingMe;


    //constructor functions
    public ApplicationUser () {}

    public ApplicationUser (String username, String password, String firstName, String lastName, String dob) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.img = "https://placeimg.com/640/360/animals";
        this.dob = dob;
    }

    //instance methods
    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getDob() {
        return this.dob;
    }

    public String getBio() {
        return this.bio;
    }

    public Long getId() {
        return this.id;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Post> getPosts() {
        return this.posts;
    }

    public String getImg() {
        return this.img;
    }

    public List<ApplicationUser> getUsersIAmFollowing() {
        return this.usersIAmFollowing;
    }

    public List<ApplicationUser> getUsersFollowingMe() {
        return this.usersFollowingMe;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
