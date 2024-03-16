package com.linkc.linkcbackend.services;

import com.linkc.linkcbackend.controllers.AuthenticationController;
import com.linkc.linkcbackend.domain.ParkioAPIKeyResponse;
import com.linkc.linkcbackend.domain.ParqioLoginRequest;
import com.linkc.linkcbackend.domain.ParqioLoginResponse;
import com.linkc.linkcbackend.domain.ParqioSmsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class ParqioService {
    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final String uriBase = "https://api-staging.dropyr.com";
    private RestClient restClient;

    public ParqioService() {
        restClient = RestClient.create();
    }

    public void sendSmsCode(String telephoneNumber) {
        ParqioSmsRequest parqioSmsRequest = new ParqioSmsRequest(telephoneNumber);

        ResponseEntity<Void> response = restClient.post()
                .uri(uriBase + "/api/v1/auth/send-sms-token")
                .contentType(APPLICATION_JSON)
                .body(parqioSmsRequest)
                .retrieve()
                .toBodilessEntity();

        logger.info(response.toString());
    }

    public String getAPIKey(String phone, String token) {
        ParqioLoginRequest parqioLoginRequest = new ParqioLoginRequest(token, phone);
        ParqioLoginResponse response = restClient.post()
                .uri(uriBase + "/api/v1/auth/sms-token-login")
                .body(parqioLoginRequest)
                .retrieve()
                .body(ParqioLoginResponse.class);

        ParkioAPIKeyResponse parkioAPIKeyResponse = restClient.post()
                .uri(uriBase + "/api/v1/auth/api-keys")
                .header("authtoken", response.getData().authtoken)
                .retrieve()
                .body(ParkioAPIKeyResponse.class);


        return parkioAPIKeyResponse.data.getApiKey();
    }

    public void openBox() {
        logger.info("Box opened");
    }
}
