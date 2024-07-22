package com.tomasdev.akhanta.auth;

import lombok.Data;

@Data
public class PasswordChangeDTO {

    private String currentPassword;
    private String repeatedPassword;
    private String newPassword;

}
