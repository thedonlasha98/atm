package com.egs.atm.model.request;

import com.egs.atm.enums.Currency;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class CashRequest {

    @NotNull
    Long cardId;

    @NotNull
    Currency currency;

    @Positive
    BigDecimal amount;
}
