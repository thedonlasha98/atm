package com.egs.atm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckCardDto {

    boolean twoFactorAuthNeeded;

    Long cardId;
}
