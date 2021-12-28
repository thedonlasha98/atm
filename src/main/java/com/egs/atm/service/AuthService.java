package com.egs.atm.service;

import com.egs.atm.model.dto.CheckCardDto;
import com.egs.atm.model.request.FingerprintRequest;

public interface AuthService {

    CheckCardDto checkCard(String cardNo, String cvv, String expDate);

    CheckCardDto validateCardByPinAndFingerprint(Long id, String pin, String fingerprint);

    void setFingerprint(Long id, FingerprintRequest request);

}
