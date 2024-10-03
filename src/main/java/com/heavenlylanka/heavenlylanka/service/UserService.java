package com.heavenlylanka.heavenlylanka.service;
import com.heavenlylanka.heavenlylanka.dto.RegisterRequest;
import com.heavenlylanka.heavenlylanka.entity.User;
import com.heavenlylanka.heavenlylanka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Method to encode the password
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    // Method to verify the raw password against the encoded password
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void registerUser(RegisterRequest registerRequest) {
        User user = new User(registerRequest.getName(),registerRequest.getEmail(), passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);
    }
}