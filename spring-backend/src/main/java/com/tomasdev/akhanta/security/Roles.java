package com.tomasdev.akhanta.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Roles {

    public static final String ADMIN = "ADMIN";
    public static final String OWNER = "OWNER";
    public static final String USER = "USER";

    public static List<String> getRoles() {
        return List.of(ADMIN, OWNER, USER);
    }
}
