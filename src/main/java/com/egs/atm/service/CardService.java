package com.egs.atm.service;

import com.egs.atm.model.dto.BalanceDto;

import java.util.List;

public interface CardService {
    List<BalanceDto> getBalance(Long cardId);

}
