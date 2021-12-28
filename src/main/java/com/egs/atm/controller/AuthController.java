package com.egs.atm.controller;

import com.egs.atm.model.dto.Card;
import com.egs.atm.model.dto.CheckCardDto;
import com.egs.atm.model.request.FingerprintRequest;
import com.egs.atm.service.AuthService;
import com.egs.atm.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Validated
@RestController
@RequestMapping("api/auth/card")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    ResponseEntity initCard(@RequestParam(name = "card_no") String cardNo,
                         @RequestParam(name = "cvv") String cvv,
                         @RequestParam(name = "exp_date") String expDate,
                         HttpSession session) {
        session.removeAttribute("card");
        Utils.validateCard(cardNo, cvv, expDate);
        CheckCardDto response = authService.checkCard(cardNo, cvv, expDate);
        initSessionCard(session, response.getCardId(), false);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping(path = "/validate-credential")
    ResponseEntity<CheckCardDto> validateCredentials(@RequestParam String pin,
                                                     @RequestParam(required = false) String fingerprint,
                                                     HttpSession session) {
        Utils.validatePin(pin);
        Card card = Card.getAuthCard(session, false);

        CheckCardDto response = authService.validateCardByPinAndFingerprint(card.getCardId(), pin, fingerprint);
        initSessionCard(session, card.getCardId(), true);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PatchMapping(path = "/set-fingerprint")
    ResponseEntity<Void> setFingerprint(@RequestBody FingerprintRequest request, HttpSession session) {
        Card card = Card.getAuthCard(session, true);
        authService.setFingerprint(card.getCardId(), request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "logout")
    ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void initSessionCard(HttpSession session, Long cardId, boolean isAuthorized) {
        Card card = new Card(cardId, isAuthorized);
        session.setAttribute("card", card);
    }

}
