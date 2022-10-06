package com.pawasa.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

//    @NotEmpty(message = "Username is required.")
    private String username;

    @NotEmpty(message = "Password is required.")
    private String password;

    @NotEmpty(message = "Email is required.")
    @Email(message = "Email should be valid.")
    private String email;

//    @NotEmpty(message = "First Name is required.")
    @Column(name = "first_name")
    private String firstName;
//    @NotEmpty(message = "Last Name is required.")
    @Column(name = "last_name")
    private String lastName;

//    @NotEmpty(message = "Phone Number is required.")
    @Column(name = "phone")
    private String phoneNumber;

//    @NotEmpty(message = "Address is required.")
    private String address;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "image")
    private String image;

    @Column(name = "role_id")
    private Long roleID;

//    @NotEmpty(message = "OTP cannot be empty.")
    @Column(name = "otp")
    private String otp;

    @Column(name = "otp_requested_time")
    private Date otpRequestedTime;

    public User(Long id, String username, String password, String email, String firstName, String lastName, String phoneNumber, String address, Date dob, String image, Long roleID, String otp, Date otpRequestedTime) {
        this.id = id;
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
        this.otp = otp;
        this.otpRequestedTime = otpRequestedTime;
    }

    public boolean isOTPRequired() {
        if (this.otp == null || this.otp.equals("")) {
            return false;
        }

        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeInMillis = this.otpRequestedTime.getTime();

        if (otpRequestedTimeInMillis + 5 * 60 * 1000 < currentTimeInMillis) {
            return false;
        }
        return true;
    }
}
