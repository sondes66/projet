package com.example.projet.Service;

import com.example.projet.Entity.User;
import com.example.projet.Repository.UserRepository;
import com.example.projet.Security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            return new CustomUserDetails(userOptional.get());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    // Register method
    public User registerUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Update profile method
    public User updateProfile(String username, String newEmail, String profilePicture) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found with username: " + username));
        user.setEmail(newEmail);
        user.setProfilePicture(profilePicture);
        return userRepository.save(user);
    }
}
