package com.tomasdev.akhanta.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
@AllArgsConstructor
public class TokenUserQuery {

    private String token;

    private UserInToken user;

    private boolean revoked;

    private boolean expired;

    @Getter @Setter
    @AllArgsConstructor
    public static class UserInToken {
        private String username;
        private String email;
        private Integer active;
        private String role;
    }

}
