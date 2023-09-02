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
        private String firstName;
        private String lastName;
        private String password;
        @Indexed(unique = true)
        private String email;
        @Indexed(unique = true)
        private String number;
        private Role role;

        private User() {}

        private User(UserBuilder userBuilder) {
                this.firstName = userBuilder.firstName;
                this.lastName = userBuilder.lastName;
                this.password = userBuilder.password;
                this.email = userBuilder.email;
                this.number = userBuilder.number;
                this.role = userBuilder.role;
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

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
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

        public String getFirstName() {
                return firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public String getEmail() {
                return email;
        }

        public String getNumber() {
                return number;
        }

        public static class UserBuilder {
            private String firstName;
            private String lastName;
            private String password;
            private String email;
            private String number;
            private Role role;

            public UserBuilder firstname(String firstName) {
                    this.firstName = firstName;
                    return this;
            }

            public UserBuilder lastname(String lastName) {
                    this.lastName = lastName;
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

            public UserBuilder role(Role role) {
                    this.role = role;
                    return this;
            }

            public User build() {
                return new User(this);
            }
        }
}
