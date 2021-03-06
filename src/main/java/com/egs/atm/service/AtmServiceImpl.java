package com.egs.atm.service;

import com.egs.atm.model.dto.BalanceDto;
import com.egs.atm.model.dto.CheckCardDto;
import com.egs.atm.model.request.CashRequest;
import com.egs.atm.model.request.FingerprintRequest;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class AtmServiceImpl implements AuthService, CardService, OperationService {

    @Value("${egs.app.bank.host}")
    private String bankServiceHostUrl;

    private final RestTemplate restTemplate;

    public AtmServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @HystrixCommand(commandKey = "bankServiceCommandKey")
    @Override
    public CheckCardDto checkCard(String cardNo, String cvv, String expDate) {
        String url = String.format("%s/api/auth/card?card_no=%s&cvv=%s&exp_date=%s",
                bankServiceHostUrl, cardNo, cvv, expDate);

        ResponseEntity response = restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                CheckCardDto.class);

        return (CheckCardDto) response.getBody();

    }

    @HystrixCommand(commandKey = "bankServiceCommandKey")
    @Override
    public CheckCardDto validateCardByPinAndFingerprint(Long id, String pin, String fingerprint) {
        String url = String.format("%s/api/auth/card/%s/validate-credential?pin=%s&fingerprint=%s",
                bankServiceHostUrl, id, pin, fingerprint);

        ResponseEntity response = restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                CheckCardDto.class);

        return (CheckCardDto) response.getBody();
    }

    @HystrixCommand(commandKey = "bankServiceCommandKey")
    @Override
    public void setFingerprint(Long id, FingerprintRequest request) {
        String url = String.format("%s/api/auth/card/%s/set-fingerprint",
                bankServiceHostUrl, id);

        restTemplate.exchange(url,
                HttpMethod.PATCH,
                new HttpEntity(request),
                ResponseEntity.class);

    }

    @HystrixCommand(commandKey = "bankServiceCommandKey")
    @Override
    public List<BalanceDto> getBalance(Long cardId) {
        String url = String.format("%s/api/card/%s/balance",
                bankServiceHostUrl, cardId);

        ResponseEntity response = restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                List.class);

        return (List<BalanceDto>) response.getBody();
    }

    @HystrixCommand(commandKey = "bankServiceCommandKey")
    @Override
    public void cashDeposit(CashRequest cashRequest) {
        String url = String.format("%s/api/cash/deposit",
                bankServiceHostUrl);

        restTemplate.exchange(url,
                HttpMethod.POST,
                new HttpEntity(cashRequest),
                ResponseEntity.class);
    }

    @HystrixCommand(commandKey = "bankServiceCommandKey")
    @Override
    public void cashWithdrawal(CashRequest cashRequest) {
        String url = String.format("%s/api/cash/withdrawal",
                bankServiceHostUrl);

        restTemplate.exchange(url,
                HttpMethod.POST,
                new HttpEntity(cashRequest),
                ResponseEntity.class);
    }
}
