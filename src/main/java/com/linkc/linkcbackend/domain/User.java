package com.linkc.linkcbackend.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Document("users")
public class User implements UserDetails {
        @Id
        private String id;
        private String firstname;
        private String lastname;
        private String password;
        @Indexed(unique = true)
        private String email;
        @Indexed(unique = true)
        private String number;
        private String profilePictureUri;
        private Role role;
        private String parqioAPIKey;

        private User() {}

        private User(UserBuilder userBuilder) {
                this.firstname = userBuilder.firstname;
                this.lastname = userBuilder.lastname;
                this.password = userBuilder.password;
                this.email = userBuilder.email;
                this.number = userBuilder.number;
                this.profilePictureUri = userBuilder.profilePictureUri;
                this.role = userBuilder.role;
                this.parqioAPIKey = userBuilder.parqioAPIKey;
        }

        public String getId() {
                return id;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(new SimpleGrantedAuthority(role.name()));
        }

        @Override
        public String getUsername() {
                return email;
        }

        @Override
        public String getPassword() {
                return password;
        }

        @Override
        public boolean isAccountNonExpired() {
                return true;
        }

        @Override
        public boolean isAccountNonLocked() {
                return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return true;
        }

        @Override
        public boolean isEnabled() {
                return true;
        }

        public void setFirstname(String firstname) {
                this.firstname = firstname;
        }

        public void setLastname(String lastname) {
                this.lastname = lastname;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public void setNumber(String number) {
                this.number = number;
        }

        public String getFirstname() {
                return firstname;
        }

        public String getLastname() {
                return lastname;
        }

        public String getEmail() {
                return email;
        }

        public String getNumber() {
                return number;
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

        public void setParqioAPIKey(String parqioAPIKey) {
                this.parqioAPIKey = parqioAPIKey;
        }

        public String getParqioAPIKey() {
                return parqioAPIKey;
        }

        public static class UserBuilder {
            private String firstname;
            private String lastname;
            private String password;
            private String email;
            private String number;
            private String profilePictureUri;
            private Role role;
            private String parqioAPIKey;

            public UserBuilder firstname(String firstName) {
                    this.firstname = firstName;
                    return this;
            }

            public UserBuilder lastname(String lastName) {
                    this.lastname = lastName;
                    return this;
            }

            public UserBuilder password(String password){
                    this.password = password;
                    return this;
            }

            public UserBuilder email(String email) {
                    this.email = email;
                    return this;
            }

            public UserBuilder number(String number) {
                    this.number = number;
                    return this;
            }

            public UserBuilder profilePictureUri(String profilePictureUri) {
                    this.profilePictureUri = profilePictureUri;
                    return this;
            }

            public UserBuilder role(Role role) {
                    this.role = role;
                    return this;
            }

            public UserBuilder parqioAPIKey(String parqioAPIKey) {
                    this.parqioAPIKey = parqioAPIKey;
                    return this;
            }

            public User build() {
                return new User(this);
            }
        }
}
