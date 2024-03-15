package com.linkc.linkcbackend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParkioAPIKeyResponseData {
    @JsonProperty("api_key")
    String apiKey;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
