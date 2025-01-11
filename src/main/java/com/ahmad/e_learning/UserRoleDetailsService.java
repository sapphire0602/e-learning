package com.ahmad.e_learning;

import com.ahmad.e_learning.config.UserRoleDetails;
import com.ahmad.e_learning.model.User;
import com.ahmad.e_learning.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserRoleDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserRoleDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND !"));
        return UserRoleDetails.buildUserDetails(user);
    }

//    @Override
//    public UserDetails userDetailsService(){
////        return username -> userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found !"));
//        return username ->  userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found !"));
//    }
}
