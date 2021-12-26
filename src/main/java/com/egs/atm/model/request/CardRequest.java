package com.egs.atm.model.request;

import com.egs.atm.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardRequest {

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @Positive
    int term;

    @NotNull
    Set<Currency> currencies;
}
