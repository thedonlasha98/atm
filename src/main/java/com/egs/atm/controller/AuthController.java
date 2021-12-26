package com.egs.atm.controller;

import com.egs.atm.model.dto.CheckCardDto;
import com.egs.atm.model.request.FingerprintRequest;
import com.egs.atm.model.response.EGSResponse;
import com.egs.atm.service.AuthService;
import com.egs.atm.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping(path = "/card")
    EGSResponse checkCard(@RequestParam(name = "card_no") String cardNo,
                                        @RequestParam(name = "cvv") String cvv,
                                        @RequestParam(name = "exp_date") String expDate) {

        Utils.validateCard(cardNo, cvv, expDate);

        EGSResponse response = authService.checkCard(cardNo, cvv, expDate);

        return response;
    }

    @GetMapping(path = "/card/{id}/validate-credential")
    EGSResponse<CheckCardDto> validateCredentials(@PathVariable Long id,
                                                  @RequestParam String pin,
                                                  @RequestParam(required = false) String fingerprint) {
        Utils.validatePin(pin);

        EGSResponse response = authService.validateCardByPinAndFingerprint(id, pin, fingerprint);

        return response;
    }

    @PatchMapping(path = "/card{id}/set-fingerprint")
    EGSResponse<Void> setFingerprint(@PathVariable Long id, FingerprintRequest request) {
        authService.setFingerprint(id, request);

        return new EGSResponse<>(HttpStatus.OK);
    }

}
