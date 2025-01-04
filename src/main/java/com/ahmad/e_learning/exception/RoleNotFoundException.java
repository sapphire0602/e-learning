package com.ahmad.e_learning.exception;

public class RoleNotFoundException extends IllegalArgumentException{
    public RoleNotFoundException(String message){
        super(message);
    }

}
