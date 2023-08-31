package com.linkc.linkcbackend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
public class User{
        @Id
        private String id;
        private String firstName;
        private String lastName;
        private String password;
        @Indexed(unique = true)
        private String email;
        @Indexed(unique = true)
        private String number;

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

        public String getPassword() {
                return password;
        }

        public String getEmail() {
                return email;
        }

        public String getNumber() {
                return number;
        }
}
