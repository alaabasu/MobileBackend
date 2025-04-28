package com.example.assigmentapi.apibackend;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank(message = "Name is required")
    private String name;

    private String gender;

    @NotBlank(message = "Email is required")
    @Pattern(
        regexp = "^[0-9]+@stud\\.fci-cu\\.edu\\.eg$",
        message = "Email must follow FCI format"
    )
    private String email;

    @NotBlank(message = "Student ID is required")
    private String studentId;

    private Integer level;

    @NotBlank(message = "Password is required")
    @Pattern(
        regexp = "^(?=.*\\d).{8,}$",
        message = "Password must be at least 8 characters with a number"
    )
    private String password;

    @NotBlank(message = "Confirm Password is required")
    private String confirmPassword;

    @Lob
    private String profilePhoto; // Base64 string or image URL
}
