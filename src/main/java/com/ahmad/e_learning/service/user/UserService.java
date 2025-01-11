package com.ahmad.e_learning.service.user;

import com.ahmad.e_learning.UserRoleDetailsService;
import com.ahmad.e_learning.config.JwtService;
import com.ahmad.e_learning.config.UserRoleDetails;
import com.ahmad.e_learning.dto.UserDto;
import com.ahmad.e_learning.enums.UserRoles;
import com.ahmad.e_learning.exception.AlreadyExistsException;
import com.ahmad.e_learning.exception.ResourceNotFoundException;
import com.ahmad.e_learning.model.Role;
import com.ahmad.e_learning.model.User;
import com.ahmad.e_learning.repository.RoleRepository;
import com.ahmad.e_learning.repository.UserRepository;
import com.ahmad.e_learning.request.CreateUserRequest;
import com.ahmad.e_learning.request.UpdateUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User with email " + email + "not found ! "));
//        if (userRepository.existsByEmail(email)){
//            return userRepository.findUserByEmail(email);
//        }
//        throw new ResourceNotFoundException("User with email" + email + "  not found");
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user ->
                        !userRepository.existsByEmail(request.getEmail()))
                .map(req ->
                {
                    User user = new User();
                    String encodedPassword = passwordEncoder.encode(request.getPassword());
                    user.setEmail(request.getEmail());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setPassword(encodedPassword);

                    Set<Role> roles = request.getRoles().stream()
                            .map(roleName -> roleRepository.findByName(UserRoles.valueOf(roleName.toUpperCase()))
                                    .orElseThrow(() ->new ResourceNotFoundException("Role Not Found " + roleName)))
                            .collect(Collectors.toSet());
                    user.setRoles(roles);

                    return userRepository.save(user);

//                    UserRoleDetails userRoleDetails = UserRoleDetails.buildUserDetails(user);
//                    String token = jwtService.generateToken(userRoleDetails);
//
//                    savedUser.setToken(token);
//
//                    System.out.println("Token in User Object: " + savedUser.getToken());
//
//                    return savedUser;

                }).orElseThrow(() -> new AlreadyExistsException("USER ALREADY EXISTS!"));
    }

    @Override
    public User updateUSer(UpdateUserRequest request, Long userId) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setFirstName(request.getFirstName());
                    existingUser.setLastName(request.getLastName());
                    existingUser.setEmail(request.getEmail());
                    Set<Role> rolesToSelect = request.getRoles().stream()
                            .map(roleName -> roleRepository.findByName(UserRoles.valueOf(roleName.toUpperCase()))
                                    .orElseThrow(() ->new ResourceNotFoundException("Role Not Found " + roleName)))
                            .collect(Collectors.toSet());
                    existingUser.setRoles(rolesToSelect);
                    return userRepository.save(existingUser);
                }).orElseThrow((() -> new ResourceNotFoundException("USER TO UPDATE NOT FOUND!")));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository :: delete , ()-> new ResourceNotFoundException("UNABLE TO DELETE USER , USER NOT FOUND!"));
    }

    @Override
    public List<UserDto> getConvertedUsers(List<User> users){
        return users.stream().map(this::convertToDto).toList();
    }

    @Override
    public UserDto convertToDto(User user){
        UserDto userDto = modelMapper.map(user , UserDto.class);
        return userDto;
    }
}
