package com.ahmad.e_learning.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(SuccessHandler.class);

    public SuccessHandler(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        logger.info("Authentication success completed , proceeding to generate token for form login");
        String token = jwtService.generateTokenForUser(authentication);
        response.setHeader("Authorization", "Bearer " + token);

//        response.setContentType("application/json");
//        response.getWriter().write("{\"token\": \"" + token + "\"}");
    }
}
