package com.egs.atm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckCardDto implements Serializable {

    boolean twoFactorAuthNeeded;

    Long cardId;
}
