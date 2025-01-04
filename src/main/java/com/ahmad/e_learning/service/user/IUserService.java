package com.ahmad.e_learning.service.user;

import com.ahmad.e_learning.dto.UserDto;
import com.ahmad.e_learning.model.User;
import com.ahmad.e_learning.request.CreateUserRequest;
import com.ahmad.e_learning.request.UpdateUserRequest;


import java.util.List;

public interface IUserService {
    List<User> getAllUsers();

    User getUserByEmail(String email);

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUSer(UpdateUserRequest request , Long userId);
    void deleteUser(Long userId);

    List<UserDto> getConvertedUsers(List<User> users);

    UserDto convertToDto(User user);
}
