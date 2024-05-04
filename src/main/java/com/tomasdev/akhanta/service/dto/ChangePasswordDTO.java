package com.tomasdev.akhanta.service.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {

    private String currentPassword;
    private String repeatedPassword;
    private String newPassword;

}
