package com.tomasdev.akhanta.exceptions;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component("authenticationEntryPoint")
public class AuthEntryPoint implements AuthenticationEntryPoint {

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(401);
        HashMap<String, Object> map = new HashMap<>();
        map.put("error", authenticationException.getMessage());
        response.getWriter().print(new Gson().toJson(map));
        response.getWriter().flush();
    }
}