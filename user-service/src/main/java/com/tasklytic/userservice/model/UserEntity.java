package com.tasklytic.userservice.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;  // For ignoring the field in API responses
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String mobileNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // Roles can now be dynamic

    @Column(nullable = false)
    private String department; // IT, HR, Sales, etc.

    @Column
    private String profilePictureUrl;

    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column
    private Timestamp updatedAt;

    @Enumerated(EnumType.STRING) // Store as string in database
    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private boolean isEmailVerified = false; // Whether the email is verified or not

    @JsonIgnore // This ensures the isAdmin field is not exposed via the API
    @Column(nullable = false)
    private boolean isAdmin = false; // Default value is false, set internally for admin users only

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
