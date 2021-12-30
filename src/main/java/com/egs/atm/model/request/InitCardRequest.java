package com.egs.atm.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitCardRequest {

    @NotNull
    String cardNo;

    @NotNull
    String cvv;

    @NotNull
    String expDate;
}
