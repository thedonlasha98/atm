package com.egs.atm.service;

import com.egs.atm.model.request.CashRequest;

public interface OperationService {
    void cashDeposit(CashRequest cashRequest);

    void cashWithdrawal(CashRequest cashRequest);
}
