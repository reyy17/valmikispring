package com.elearning.valmiki.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @Column(name = "email")
    private String email;
    @Column(name = "username", unique = true)
    private String username;
    private String userid;
    private String mobile;
    private String gender;
    private String profession;
    private String address;
    private String password;
    private String resetToken;
}
