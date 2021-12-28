package com.egs.atm.controller;


import com.egs.atm.model.dto.BalanceDto;
import com.egs.atm.model.dto.Card;
import com.egs.atm.model.request.CashRequest;
import com.egs.atm.service.CardService;
import com.egs.atm.service.OperationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/atm")
public class ATMController {

    private final CardService cardService;

    private final OperationService operationService;

    public ATMController(CardService cardService, OperationService operationService) {
        this.cardService = cardService;
        this.operationService = operationService;
    }

    @GetMapping("/balance")
    ResponseEntity<List<BalanceDto>> getBalance(HttpSession session) {
        Card card = Card.getAuthCard(session, true);

        List<BalanceDto> response = cardService.getBalance(card.getCardId());

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping(value = "/deposit")
    ResponseEntity<Void> cashDeposit(@Valid @RequestBody CashRequest cashRequest, HttpSession session) {
        Card card = Card.getAuthCard(session, true);
        cashRequest.setCardId(card.getCardId());
        operationService.cashDeposit(cashRequest);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping(value = "/withdrawal")
    ResponseEntity<Void> cashWithdrawal(@Valid @RequestBody CashRequest cashRequest, HttpSession session) {
        Card card = Card.getAuthCard(session, true);
        cashRequest.setCardId(card.getCardId());
        operationService.cashWithdrawal(cashRequest);

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
