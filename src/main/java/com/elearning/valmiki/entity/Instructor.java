package com.elearning.valmiki.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Instructor {
    @Id
    private String email;
    private String instructorName;
    private String instructorId;
    private String experience;
    private String mobile;
    private String gender;
    private String password;
    private String status;
    private String resetToken;
}
