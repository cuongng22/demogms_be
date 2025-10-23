package com.example.demo.table;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Table(name = "users")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String username;
    String password;

    public Users() {
    }

    public Users(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
