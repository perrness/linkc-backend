package com.linkc.linkcbackend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("boxes")
public class Box {
    @Id
    private String id;
    private String name;
    private String location;
    private Status status;
    @JsonProperty("reserved_by")
    private String reservedBy;
    @JsonProperty("to_be_opened_by")
    private String toBeOpenedBy;
    private String timeBooked;

    public String getTimeBooked() {
        return timeBooked;
    }

    public void setTimeBooked(String timeBooked) {
        this.timeBooked = timeBooked;
    }

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
