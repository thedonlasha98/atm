package com.egs.atm.service;

import com.egs.atm.model.request.FingerprintRequest;
import com.egs.atm.model.response.EGSResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AtmServiceImpl implements AuthService, CardService, OperationService {

    @Value("${egs.app.bank.host}")
    private String bankServiceHostUrl;

    private final RestTemplate restTemplate;

    public AtmServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @HystrixCommand(fallbackMethod = "getFailurePageForCheckCard",
            commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
    })
    @Override
    public EGSResponse checkCard(String cardNo, String cvv, String expDate) {
        String url = String.format("%s/api/auth/card?card_no=%s&cvv=%s&exp_date=%s",
                bankServiceHostUrl, cardNo, cvv, expDate);

        ResponseEntity response = restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                EGSResponse.class);

        return (EGSResponse) response.getBody();

    }

    @Override
    public EGSResponse validateCardByPinAndFingerprint(Long id, String pin, String fingerprint) {
        String url = String.format("%s/api/auth/card/%s/validate-credential?pin=%s&fingerprint=%s",
                bankServiceHostUrl, id, pin, fingerprint);

        ResponseEntity response = restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                EGSResponse.class);

        return (EGSResponse) response.getBody();
    }

    @Override
    public void setFingerprint(Long id, FingerprintRequest request) {
        String url = String.format("%s/api/auth/card/%s/set-fingerprint",
                bankServiceHostUrl, id);

        restTemplate.exchange(url,
                HttpMethod.PATCH,
                new HttpEntity(request),
                EGSResponse.class);

    }

    private EGSResponse getFailurePageForCheckCard(String cardNo, String cvv, String expDate) {
        return new EGSResponse("Internal Server Error!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
