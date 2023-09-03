package com.linkc.linkcbackend.domain;

public class UserData {
    private String firstname;
    private String lastname;
    private String number;
    private String email;

    public UserData(builder builder) {
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.email = builder.email;
        this.number = builder.number;
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

    public static class builder {
        private String firstname;
        private String lastname;
        private String email;
        private String number;

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

        public UserData build() {
            return new UserData(this);
        }
    }
}
