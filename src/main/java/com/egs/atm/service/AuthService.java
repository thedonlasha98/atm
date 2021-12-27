package com.egs.atm.service;

import com.egs.atm.model.dto.CheckCardDto;
import com.egs.atm.model.request.FingerprintRequest;
import com.egs.atm.model.response.EGSResponse;

public interface AuthService {

    CheckCardDto checkCard(String cardNo, String cvv, String expDate);

    EGSResponse validateCardByPinAndFingerprint(Long id, String pin, String fingerprint);

    void setFingerprint(Long id, FingerprintRequest request);

}
