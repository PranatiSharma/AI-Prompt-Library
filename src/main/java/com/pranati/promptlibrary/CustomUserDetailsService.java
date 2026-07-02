package com.pranati.promptlibrary;

import com.pranati.promptlibrary.entity.User;
import com.pranati.promptlibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        System.out.println("==================================");
        System.out.println("LOGIN ATTEMPT: " + email);
        System.out.println("==================================");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    System.out.println("USER NOT FOUND!");
                    return new UsernameNotFoundException("User not found");
                });

        System.out.println("USER FOUND: " + user.getEmail());
        System.out.println("PASSWORD IN DATABASE: " + user.getPassword());
        System.out.println("ROLE: " + user.getRole());

        return new CustomUserDetails(user);
    }
}