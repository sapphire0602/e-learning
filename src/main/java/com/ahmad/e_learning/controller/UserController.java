package com.ahmad.e_learning.controller;

import com.ahmad.e_learning.dto.UserDto;
import com.ahmad.e_learning.exception.AlreadyExistsException;
import com.ahmad.e_learning.exception.ResourceNotFoundException;
import com.ahmad.e_learning.model.User;
import com.ahmad.e_learning.request.CreateUserRequest;
import com.ahmad.e_learning.request.UpdateUserRequest;
import com.ahmad.e_learning.response.ApiResponse;
import com.ahmad.e_learning.service.user.IUserService;
import com.ahmad.e_learning.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/all")
    public ResponseEntity<ApiResponse> getAllUsers(){
        try {
            List<User> users = userService.getAllUsers();
            List<UserDto> userDto = userService.getConvertedUsers(users);
            return ResponseEntity.ok(new ApiResponse("SUCCESS , USERS RETRIEVED SUCCESSFULLY !" , userDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error : USERS NOT FOUND!" , INTERNAL_SERVER_ERROR));
        }
    }


    @GetMapping("/user/email")
    public ResponseEntity<ApiResponse> getUserByEmail(@RequestParam("email") String email){
        try {
            User user = userService.getUserByEmail(email);
            UserDto userDto = userService.convertToDto(user);
            return ResponseEntity.ok(new ApiResponse("SUCCESS , USER RETRIEVED SUCCESSFULLY !" , userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage() , null));
        }
    }

    @GetMapping("/user/{userId}/id")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertToDto(user);
            return ResponseEntity.ok(new ApiResponse("SUCCESS , USER RETRIEVED SUCCESSFULLY !" , userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("USER WITH ID : " + userId + "NOT FOUND" , null));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> addUser(@RequestBody CreateUserRequest request){
        try {
            User user = userService.createUser(request);
            UserDto userDto = userService.convertToDto(user);
            return  ResponseEntity.ok(new ApiResponse("SUCCESS , USER CREATED SUCCESSFULLY !" , userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage() , null ));
        }
    }

    @PutMapping("/user/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request , @PathVariable Long userId){
        try {
            User user = userService.updateUSer(request , userId);
            UserDto userDto = userService.convertToDto(user);
            return ResponseEntity.ok(new ApiResponse("SUCCESS , USER UPDATED SUCCESSFULLY !" , userDto) );
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_MODIFIED).body(new ApiResponse(e.getMessage() , null));
        }

    }

//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/user/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("SUCCESS , USER DELETED SUCCESSFULLY !" , null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage() , null));
        }
    }


}
