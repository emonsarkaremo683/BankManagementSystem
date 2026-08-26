package com.elitetech_inc.ensarkbank.util;

import com.elitetech_inc.ensarkbank.common.enums.CardNetwork;
import com.elitetech_inc.ensarkbank.common.enums.CardType;
import org.springframework.stereotype.Component;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class CardGenerator {

    private final Random random = new Random();

    // Card number generator
    public String cardGenerator(CardNetwork cd, CardType ct, String acc) {
        final String fixedCode = "683";

        String netCode = switch (cd) {
            case VISA -> "4";
            case MASTERCARD -> "5";
            case null, default -> "6";
        };

        String typeCode = switch (ct) {
            case DEBIT -> "01";
            case CREDIT -> "02";
            default -> "00";
        };


        String randomPart = acc.length() > 7 ? acc.substring(7) : acc;

        String digits = netCode + fixedCode + typeCode + randomPart;

        // Return full 16-digit card number
        return digits + lastDigit(digits);
    }

    // CVV number generator
    public String getCvv() {
        return String.format("%03d", random.nextInt(1000));
    }

    // Check if Card is valid using standard Luhn algorithm
    public Boolean isCardValid(String number1) {
        if (number1 == null || number1.length() != 16) {
            return false;
        }

        final String number = number1.replace(" ", "");

        int sum = IntStream.range(0, number.length())
                .map(i -> {
                    int digit = Character.getNumericValue(number.charAt(i));

                    if ((number.length() - 1 - i) % 2 == 1) {
                        int multiplied = digit * 2;
                        return (multiplied > 9) ? multiplied - 9 : multiplied;
                    }
                    return digit;
                })
                .sum();

        return sum % 10 == 0;
    }

    // Find out the correct 16th check digit
    private String lastDigit(String number) {
        int sum = IntStream.range(0, number.length())
                .map(i -> {
                    int digit = Character.getNumericValue(number.charAt(i));
                    if ((16 - 1 - i) % 2 == 1) {
                        int multiplied = digit * 2;
                        return (multiplied > 9) ? multiplied - 9 : multiplied;
                    }
                    return digit;
                })
                .sum();

        return String.valueOf((10 - (sum % 10)) % 10);
    }
}
