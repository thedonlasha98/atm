package com.egs.atm.service;

import com.egs.atm.exception.EGSException;
import com.egs.atm.model.dto.BalanceDto;
import com.egs.atm.model.dto.CheckCardDto;
import com.egs.atm.model.request.CashRequest;
import com.egs.atm.model.request.FingerprintRequest;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AtmServiceImpl implements AuthService, CardService, OperationService {

    @Value("${egs.app.bank.host}")
    private String bankServiceHostUrl;

    private final RestTemplate restTemplate;

    public AtmServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @HystrixCommand(fallbackMethod = "getFailurePageForCheckCard")
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

    @Override
    public void setFingerprint(Long id, FingerprintRequest request) {
        String url = String.format("%s/api/auth/card/%s/set-fingerprint",
                bankServiceHostUrl, id);

        restTemplate.exchange(url,
                HttpMethod.PATCH,
                new HttpEntity(request),
                ResponseEntity.class);

    }

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

    @Override
    public void cashDeposit(CashRequest cashRequest) {
        String url = String.format("%s/api/cash/deposit",
                bankServiceHostUrl);

        restTemplate.exchange(url,
                HttpMethod.POST,
                new HttpEntity(cashRequest),
                ResponseEntity.class);
    }

    @Override
    public void cashWithdrawal(CashRequest cashRequest) {
        String url = String.format("%s/api/cash/withdrawal",
                bankServiceHostUrl);

        restTemplate.exchange(url,
                HttpMethod.POST,
                new HttpEntity(cashRequest),
                ResponseEntity.class);
    }

    private CheckCardDto getFailurePageForCheckCard(String cardNo, String cvv, String expDate, Throwable throwable) {
        try {
            int i = 1 / 0;
            return null;
        } catch (Exception ex) {
            throw new EGSException("Hystrix Exception!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
