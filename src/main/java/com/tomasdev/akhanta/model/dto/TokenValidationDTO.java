package com.tomasdev.akhanta.model.dto;

import com.tomasdev.akhanta.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenValidationDTO {

    @Indexed(unique = true)
    private String token;

    private User user;

    private boolean revoked;

    private boolean expired;

}
