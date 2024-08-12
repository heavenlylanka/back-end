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

    public void registerUser(RegisterRequest registerRequest) {
        User user = new User(registerRequest.getEmail(), passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);
    }
}