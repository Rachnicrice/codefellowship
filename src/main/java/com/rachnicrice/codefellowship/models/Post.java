package com.rachnicrice.codefellowship.models;

import javax.persistence.*;

@Entity
public class Post {
    //instance variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String body;
    String time;

    @ManyToOne
    ApplicationUser user;

    //constructor function
    public Post () {}

    public Post (String body, String time, ApplicationUser user) {
        this.body = body;
        this.time = time;
        this.user = user;
    }

    //instance methods
    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public String getTime() {
        return time;
    }

    public ApplicationUser getUser() {
        return user;
    }
}
