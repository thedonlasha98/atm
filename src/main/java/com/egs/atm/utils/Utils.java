package com.egs.atm.utils;

import com.egs.atm.exception.EGSException;
import org.springframework.http.HttpStatus;

import java.security.SecureRandom;

public class Utils {

    public static final String randomNumberAlphabet = "0123456789";
    public static final String randomStringAlphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String randomNumber(int len) {
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(randomNumberAlphabet.charAt(rnd.nextInt(randomNumberAlphabet.length())));
        }
        return sb.toString();
    }

    public static String randomString(int len) {
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(randomStringAlphabet.charAt(rnd.nextInt(randomStringAlphabet.length())));
        }
        return sb.toString();
    }

    public static void validateCard(String cardNo, String cvv, String expDate) {

        if (cardNo.length() != 16 && cardNo != null) {
            throw new EGSException("Incorrect length of card_no parameter!", HttpStatus.BAD_REQUEST);
        }
        if (cvv.length() != 3 && cvv != null) {
            throw new EGSException("Incorrect length of cvv parameter!", HttpStatus.BAD_REQUEST);
        }
        if (!expDate.matches("(?:0[1-9]|1[0-2])/[0-9]{2}") && expDate != null) {
            throw new EGSException("Incorrect format of exp_date parameter!", HttpStatus.BAD_REQUEST);
        }
    }

    public static void validatePin(String pin) {
        if (pin.length() != 4 && pin != null) {
            throw new EGSException("Incorrect length of pin parameter!", HttpStatus.BAD_REQUEST);
        }
    }
}
