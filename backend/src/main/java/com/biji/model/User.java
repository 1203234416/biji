package com.biji.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String username;

    public User() {}

    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
