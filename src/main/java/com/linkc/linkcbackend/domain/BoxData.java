package com.linkc.linkcbackend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BoxData {
    @JsonProperty("all_boxes")
    private List<BoxStripped> allBoxesStripped;
    @JsonProperty("all_boxes_reserved_by")
    private List<Box> allBoxesReservedBy;
    @JsonProperty("all_boxes_reserved_for")
    private List<Box> allBoxesReservedFor;

    public BoxData(List<BoxStripped> allBoxesStripped, List<Box> allBoxesReservedBy, List<Box> allBoxesReservedFor) {
        this.allBoxesStripped = allBoxesStripped;
        this.allBoxesReservedBy = allBoxesReservedBy;
        this.allBoxesReservedFor = allBoxesReservedFor;
    }

    public List<BoxStripped> getAllBoxesStripped() {
        return allBoxesStripped;
    }

    public void setAllBoxesStripped(List<BoxStripped> allBoxesStripped) {
        this.allBoxesStripped = allBoxesStripped;
    }

    public List<Box> getAllBoxesReservedBy() {
        return allBoxesReservedBy;
    }

    public void setAllBoxesReservedBy(List<Box> allBoxesReservedBy) {
        this.allBoxesReservedBy = allBoxesReservedBy;
    }

    public List<Box> getAllBoxesReservedFor() {
        return allBoxesReservedFor;
    }

    public void setAllBoxesReservedFor(List<Box> allBoxesReservedFor) {
        this.allBoxesReservedFor = allBoxesReservedFor;
    }
}
