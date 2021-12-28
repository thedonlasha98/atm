package com.egs.atm.model.dto;

import com.egs.atm.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BalanceDto implements Serializable {

    Currency currency;

    BigDecimal balance;
}
