package com.elearning.valmiki.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InstructorDto {
    private String email;
    private String instructorName;
    private String instructorId;
    private String experience;
    private String mobile;
    private String gender;
    private String password;
    private String status;
}