package com.pawasa.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @NotEmpty(message = "Username cannot be empty.")
    private String username;

    @NotEmpty(message = "Password cannot be empty.")
    private String password;

    @NotEmpty(message = "Email cannot be empty.")
    @Email(message = "Email should be valid.")
    private String email;

    @NotEmpty(message = "First Name cannot be empty.")
    @Column(name = "first_name")
    private String firstName;
    @NotEmpty(message = "Last Name cannot be empty.")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "Phone Number cannot be empty.")
    @Column(name = "phone")
    private String phoneNumber;

    @NotEmpty(message = "Address cannot be empty.")
    private String address;

    @Column(name = "dob")
    private Date dob;

    private String image;

    @Column(name = "role_id")
    private Long roleID;

    public User(String username, String password, String email, String firstName, String lastName, String phoneNumber, String address, Date dob, String image, Long roleID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dob = dob;
        this.image = image;
        this.roleID = roleID;
    }
}
