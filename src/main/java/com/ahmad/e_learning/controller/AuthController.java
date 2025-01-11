package com.ahmad.e_learning.controller;

import com.ahmad.e_learning.config.JwtService;
import com.ahmad.e_learning.config.UserRoleDetails;
import com.ahmad.e_learning.request.LoginRequest;
import com.ahmad.e_learning.response.ApiResponse;
import com.ahmad.e_learning.response.JwtResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/user/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request){
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail() , request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtService.generateTokenForUser(authentication);
            UserRoleDetails userDetails = (UserRoleDetails) authentication.getPrincipal();
            JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), token);
            return ResponseEntity.ok(new ApiResponse("LOGIN SUCCESSFUL !" , jwtResponse));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse( "Authentication failed" + e.getMessage() , null));
        }

    }
}

//ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();
//JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt);
//            return ResponseEntity.ok(new ApiResponse("LOGIN SUCCESSFUL" , jwtResponse));
//        } catch (
//AuthenticationException e) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse( "Authentication failed" + e.getMessage() , null));
//        }
