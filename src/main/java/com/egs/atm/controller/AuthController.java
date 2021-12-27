package com.egs.atm.controller;

import com.egs.atm.model.dto.Card;
import com.egs.atm.model.dto.CheckCardDto;
import com.egs.atm.model.request.FingerprintRequest;
import com.egs.atm.model.response.EGSResponse;
import com.egs.atm.service.AuthService;
import com.egs.atm.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    EGSResponse initCard(@RequestParam(name = "card_no") String cardNo,
                         @RequestParam(name = "cvv") String cvv,
                         @RequestParam(name = "exp_date") String expDate,
                         HttpSession session) {

        Utils.validateCard(cardNo, cvv, expDate);
        CheckCardDto response = authService.checkCard(cardNo, cvv, expDate);
        initSessionCard(session, response.getCardId(), false);

        return new EGSResponse(response, HttpStatus.OK);
    }

    @GetMapping(path = "/validate-credential")
    EGSResponse<CheckCardDto> validateCredentials(@RequestParam String pin,
                                                  @RequestParam(required = false) String fingerprint,
                                                  HttpSession session) {
        Utils.validatePin(pin);
        Card card = Card.getAuthCard(session, false);

        EGSResponse response = authService.validateCardByPinAndFingerprint(card.getCardId(), pin, fingerprint);
        initSessionCard(session, card.getCardId(), true);

        return response;
    }

    @PatchMapping(path = "/set-fingerprint")
    EGSResponse<Void> setFingerprint(@RequestBody FingerprintRequest request, HttpSession session) {
        Card card = Card.getAuthCard(session, true);
        authService.setFingerprint(card.getCardId(), request);

        return new EGSResponse<>(HttpStatus.OK);
    }

    @PostMapping(path = "logout")
    EGSResponse<Void> logout(HttpSession session) {
        session.invalidate();

        return new EGSResponse<>(HttpStatus.OK);
    }

    private void initSessionCard(HttpSession session, Long cardId, boolean isAuthorized) {
        Card card = new Card(cardId, isAuthorized);
        session.setAttribute("card", card);
    }

}
