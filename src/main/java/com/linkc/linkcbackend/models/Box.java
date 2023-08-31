package com.linkc.linkcbackend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

enum Status{
    available,
    unavailable
}

@Document("boxes")
public class Box {
    @Id
    private String id;
    private String name;
    private String location;
    private Status status;
    private String reservedBy;
    private String toBeOpenedBy;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(String reservedBy) {
        this.reservedBy = reservedBy;
    }

    public String getToBeOpenedBy() {
        return toBeOpenedBy;
    }

    public void setToBeOpenedBy(String toBeOpenedBy) {
        this.toBeOpenedBy = toBeOpenedBy;
    }
}
