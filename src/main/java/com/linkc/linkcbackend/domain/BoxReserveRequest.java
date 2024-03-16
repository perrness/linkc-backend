package com.linkc.linkcbackend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BoxReserveRequest {
    private String id;
    @JsonProperty("to_be_opened_by")
    private String toBeOpenedBy;
    @JsonProperty("reserved_to")
    private String reservedTo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToBeOpenedBy() {
        return toBeOpenedBy;
    }

    public void setToBeOpenedBy(String toBeOpenedBy) {
        this.toBeOpenedBy = toBeOpenedBy;
    }

    public String getReservedTo() {
        return reservedTo;
    }

    public void setReservedTo(String reservedTo) {
        this.reservedTo = reservedTo;
    }
}
