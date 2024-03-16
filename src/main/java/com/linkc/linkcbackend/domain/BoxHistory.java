package com.linkc.linkcbackend.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("boxhistory")
public class BoxHistory {
    @Id
    public String id;
    public String boxId;
    public String reservedBy;
    public String openedBy;
    public String reservedTime;
    public String openedTime;

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    public String getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(String reservedBy) {
        this.reservedBy = reservedBy;
    }

    public String getOpenedBy() {
        return openedBy;
    }

    public void setOpenedBy(String openedBy) {
        this.openedBy = openedBy;
    }

    public String getReservedTime() {
        return reservedTime;
    }

    public void setReservedTime(String reservedTime) {
        this.reservedTime = reservedTime;
    }

    public String getOpenedTime() {
        return openedTime;
    }

    public void setOpenedTime(String openedTime) {
        this.openedTime = openedTime;
    }
}
