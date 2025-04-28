package com.example.assigmentapi.apibackend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        try {
            // Check if password matches confirm password
            if (!user.getPassword().equals(user.getConfirmPassword())) {
                return ResponseEntity.badRequest().body("❌ Passwords do not match");
            }

            // Check if email prefix matches student ID
            String emailPrefix = user.getEmail().split("@")[0];
            if (!emailPrefix.equals(user.getStudentId())) {
                return ResponseEntity.badRequest().body("❌ Student ID must match the prefix of the email");
            }

            // Check for duplicate email
            List<User> existingUsers = userRepository.findByEmail(user.getEmail());
            if (!existingUsers.isEmpty()) {
                return ResponseEntity.badRequest().body("❌ Email already registered");
            }

            userRepository.save(user);
            return ResponseEntity.ok("✅ Registered successfully");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Registration failed: " + e.getMessage());
        }
    }

   @PostMapping("/login")
public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
    try {
        List<User> matchedUsers = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());

        if (matchedUsers.size() == 1) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("userId", matchedUsers.get(0).getId()); // Assuming there's an ID field
            return ResponseEntity.ok(response);
        } else if (matchedUsers.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid email or password"));
        } else {
            return ResponseEntity.status(409).body(Map.of("error", "Multiple users found. Please contact support."));
        }
    } catch (Exception e) {
        return ResponseEntity.status(500).body(Map.of("error", "Login failed: " + e.getMessage()));
    }
}

    @PostMapping("/profile")
    public ResponseEntity<User> createOrUpdateProfile(@RequestBody User userProfile) {
        Optional<User> existing = userRepository.findById(userProfile.getId());

        if (existing.isPresent()) {
            User user = existing.get();

            // Update allowed fields
            user.setName(userProfile.getName());
            user.setGender(userProfile.getGender());
            user.setEmail(userProfile.getEmail());
            user.setStudentId(userProfile.getStudentId());
            user.setLevel(userProfile.getLevel());
            user.setProfilePhoto(userProfile.getProfilePhoto());

            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/user/{studentId}")
public ResponseEntity<?> getUserByStudentId(@PathVariable String studentId) {
    Optional<User> user = userRepository.findByStudentId(studentId);

    if (user.isPresent()) {
        return ResponseEntity.ok(user.get());
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("{\"error\": \"User not found by student ID\"}");
    }
}

    

    
}


