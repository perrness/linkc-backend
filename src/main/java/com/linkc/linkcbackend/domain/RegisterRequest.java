package com.linkc.linkcbackend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

public class RegisterRequest {
    @NotEmpty(message = "firstname can't be blank")
    private String firstname;
    @NotEmpty(message = "lastname can't be blank")
    private String lastname;
    @NotEmpty(message = "password can't be blank")
    private String password;
    @NotEmpty(message = "email can't be blank")
    private String email;
    @NotEmpty(message = "number can't be blank")
    private String number;
    @JsonProperty("profile_picture_encoded_base64")
    private String profilePictureEncodedBase64;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProfilePictureEncodedBase64() {
        return profilePictureEncodedBase64;
    }

    public void setProfilePictureEncodedBase64(String profilePictureEncodedBase64) {
        this.profilePictureEncodedBase64 = profilePictureEncodedBase64;
    }
}

