package com.example.assigmentapi.apibackend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByEmailAndPassword(String email, String password);
    List<User> findByEmail(String email);

      // NEW: Find user by studentId
      Optional<User> findByStudentId(String studentId);
    
}
