package com.egs.atm.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FingerprintRequest {

    @NotBlank
    @Size(min = 4, max = 4)
    String pin;

    String fingerprint;
}
