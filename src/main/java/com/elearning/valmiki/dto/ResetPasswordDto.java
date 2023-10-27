package com.elearning.valmiki.dto;

import lombok.Data;

@Data
public class ResetPasswordDto {
    private String resetToken;
    private String newPassword;
}
