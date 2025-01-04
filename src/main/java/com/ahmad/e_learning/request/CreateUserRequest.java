package com.ahmad.e_learning.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.Set;


public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

//    @JsonDeserialize(using = UserRolesSetDeserializer.class)
    private Set<String> roles;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
