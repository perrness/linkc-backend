package com.linkc.linkcbackend.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserData {
    private String firstname;
    private String lastname;
    private String number;
    private String email;
    @JsonProperty("profile_picture_uri")
    private String profilePictureUri;
    private String id;
    private Role role;

    public UserData(Builder builder) {
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.email = builder.email;
        this.number = builder.number;
        this.profilePictureUri = builder.profilePictureUri;
        this.id = builder.id;
        this.role = builder.role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static class Builder {
        private String firstname;
        private String lastname;
        private String email;
        private String number;
        private String profilePictureUri;
        private String id;
        private Role role;

        public Builder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public Builder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder number(String number) {
            this.number = number;
            return this;
        }

        public Builder profilePictureUri(String profilePictureUri) {
            this.profilePictureUri = profilePictureUri;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public UserData build() {
            return new UserData(this);
        }
    }
}
