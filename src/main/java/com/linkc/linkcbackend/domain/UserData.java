package com.linkc.linkcbackend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserData {
    private String firstname;
    private String lastname;
    private String number;
    private String email;
    @JsonProperty("profile_picture_uri")
    private String profilePictureUri;

    public UserData(builder builder) {
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.email = builder.email;
        this.number = builder.number;
        this.profilePictureUri = builder.profilePictureUri;
    }

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePictureUri() {
        return profilePictureUri;
    }

    public void setProfilePictureUri(String profilePictureUri) {
        this.profilePictureUri = profilePictureUri;
    }

    public static class builder {
        private String firstname;
        private String lastname;
        private String email;
        private String number;
        private String profilePictureUri;

        public builder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public builder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public builder email(String email) {
            this.email = email;
            return this;
        }

        public builder number(String number) {
            this.number = number;
            return this;
        }

        public builder profilePictureUri(String profilePictureUri) {
            this.profilePictureUri = profilePictureUri;
            return this;
        }

        public UserData build() {
            return new UserData(this);
        }
    }
}
