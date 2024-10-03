package com.heavenlylanka.heavenlylanka.controller;

import com.heavenlylanka.heavenlylanka.dto.UpdateRequest;
import com.heavenlylanka.heavenlylanka.dto.UpdatePasswordRequest;
import com.heavenlylanka.heavenlylanka.entity.User;
import com.heavenlylanka.heavenlylanka.repository.UserRepository;
import com.heavenlylanka.heavenlylanka.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.nio.file.StandardCopyOption;
import org.springframework.util.FileSystemUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;



@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {

        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Map<String, Object> profile = new HashMap<>();
        profile.put("email", user.getEmail());
        profile.put("name", user.getName());

        // Construct the full URL for the profile picture
        String profilePicture = user.getProfilePicture();
        if (profilePicture != null && !profilePicture.isEmpty()) {
            String fullImageUrl = "http://localhost:8080/api/user" + profilePicture;
            profile.put("profileImage", fullImageUrl);
        } else {
            profile.put("profileImage", "http://localhost:3000/assets/user_logo.png");
        }

        return ResponseEntity.ok(profile);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UpdateRequest updateRequest, Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (updateRequest.getName() != null && !updateRequest.getName().isEmpty()) {
            user.setName(updateRequest.getName());
        }

        if (updateRequest.getEmail() != null && !updateRequest.getEmail().isEmpty()) {
            if (!user.getEmail().equals(updateRequest.getEmail())) {
                if (userRepository.findByEmail(updateRequest.getEmail()).isPresent()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is already in use");
                }
                user.setEmail(updateRequest.getEmail());
            }
        }

        userRepository.save(user);
        return ResponseEntity.ok("User updated successfully");
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest, Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (!userService.verifyPassword(updatePasswordRequest.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current password is incorrect");
        }

        user.setPassword(userService.encodePassword(updatePasswordRequest.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("Password updated successfully");
    }
    private final Path rootLocation = Paths.get("uploads");
    @PostMapping("/upload-profile-picture")
    public ResponseEntity<?> uploadProfilePicture(@RequestParam("file") MultipartFile file, Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Ensure that the uploads directory exists
        try {
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not create upload directory.");
        }

        try {
            // Generate a unique file name
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

            // Save the image to the server
            Path destinationFile = rootLocation.resolve(uniqueFilename).normalize();
            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

            // Update the user profile picture URL in the database
            user.setProfilePicture("/uploads/" + uniqueFilename);
            userRepository.save(user);

            return ResponseEntity.ok("Profile picture updated successfully");
        } catch (Exception e) {
            e.printStackTrace();  // Log exception for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload profile picture");
        }
    }
    @GetMapping("/uploads/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> serveFile(@PathVariable String filename) {
        try {
            // Define the root location where your files are stored
            Path file = Paths.get("uploads").resolve(filename).normalize();

            // Check if file exists and is readable
            if (!Files.exists(file) || !Files.isReadable(file)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
            }

            // Determine the file's content type
            String contentType = Files.probeContentType(file);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            // Return the file as a resource
            return ResponseEntity.ok()
                    .header("Content-Type", contentType)
                    .body(Files.readAllBytes(file));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to serve file");
        }
    }
}
