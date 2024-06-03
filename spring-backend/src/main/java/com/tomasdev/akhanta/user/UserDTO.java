package com.tomasdev.akhanta.user;

import com.tomasdev.akhanta.security.jwt.Token;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;

import java.util.List;

@Getter @Setter @ToString
public class UserDTO {

    private String userId;
    @NotBlank(message = "Ingrese su nombre.")
    private String firstName;
    @NotBlank(message = "Ingrese su apellido.")
    private String lastName;
    @NotBlank(message = "Ingrese su correo electrónico.")
    private String email;
    @NotBlank(message = "Ingrese una contraseña.")
    private String password;
    private String cellphone_number;
    private Integer active;
    private String role;
    private ObjectId cartId;
    private List<Token> tokensList;
}
