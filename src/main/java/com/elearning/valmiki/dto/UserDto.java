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
public class UserDto {
    private String email;
    private String username;
    private String userid;
    private String mobile;
    private String gender;
    private String profession;
    private String address;
    private String password;
    private String resetToken;
}
