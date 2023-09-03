package com.linkc.linkcbackend.domain;

public class BoxStripped {
    private String id;
    private String location;
    private Status status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BoxStripped(builder builder) {
        this.id = builder.id;
        this.location = builder.location;
        this.status = builder.status;
    }

    public static class builder {
        private String id;
        private String location;
        private Status status;

        public builder id(String id) {
            this.id = id;
            return this;
        }

        public builder location(String location) {
            this.location = location;
            return this;
        }

        public builder status(Status status) {
            this.status = status;
            return this;
        }

        public BoxStripped build() {
            return new BoxStripped(this);
        }
    }
}
