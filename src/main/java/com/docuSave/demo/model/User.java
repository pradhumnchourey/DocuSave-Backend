package com.docuSave.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "new_user_tbl")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @NotBlank(message = "Name is required!")
    private String name;
    
    private long phoneNumber;

    @NotBlank(message = "Email is requied!")
    @Email(message = "Invalid email format!")
    private String email;

    @NotBlank(message = "Password is required!")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PdfFile> user_files;

    // public User(String name, long phoneNumber, String email, String password) {
    //     this.name = name;
    //     this.phoneNumber = phoneNumber;
    //     this.email = email;
    //     this.password = password;
    // }
    // public User(int userId2, String name2) {
    //     this.userId = userId2;
    //     this.name = name2;
    // }
}
