package com.bulvee.romannumerals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RomanNumeralConverter {
    private Map<String, Integer> romanNumerals = Map.of("I", 1, "V", 5, "X", 10, "L", 50, "C", 100, "D", 500, "M", 1000);

    private String repeatedRomanNumber;
    private Map<String, Set<String>> allowedDecreasingValues;

    public RomanNumeralConverter() {
        //only values from key, can be used to decrease. For example can be used V to decrease X.
        allowedDecreasingValues = new HashMap<>();

        //key = current value, value = allowed value to subtraction
        allowedDecreasingValues.put("I", Set.of("V", "X"));
        allowedDecreasingValues.put("X", Set.of("C", "L"));
        allowedDecreasingValues.put("C", Set.of("M", "D"));
    }

    public int romanToInt(String roman) {

        int decimalNumber = 0;

        String[] inputedCharacters = roman.split("");
        int length = inputedCharacters.length;

        String lastRomanNumberAnalyzed = null;
        basicValidationsForRomanNumeral(inputedCharacters);

        for (int i = 0; i < length; i++) {
            String numberBeingAnalyzed = inputedCharacters[i];

            if (isAddition(lastRomanNumberAnalyzed, numberBeingAnalyzed)) {
                decimalNumber += romanNumerals.get(numberBeingAnalyzed);
                lastRomanNumberAnalyzed = numberBeingAnalyzed;

            } else if (isSubtraction(lastRomanNumberAnalyzed, numberBeingAnalyzed)) {
                validRomanNumberBeforeCurrent(roman, inputedCharacters, i);
                decimalNumber += romanNumerals.get(numberBeingAnalyzed) - (romanNumerals.get(lastRomanNumberAnalyzed) * 2);
                lastRomanNumberAnalyzed = numberBeingAnalyzed;

            } else if (isSingleCharacterRomanNumber(roman)) {
                decimalNumber = romanNumerals.get(numberBeingAnalyzed);

            } else {
                lastRomanNumberAnalyzed = numberBeingAnalyzed;
                decimalNumber = romanNumerals.get(numberBeingAnalyzed);
            }
        }
        return decimalNumber;
    }

    private static boolean isSingleCharacterRomanNumber(String roman) {
        return roman.length() == 1;
    }

    private boolean isSubtraction(String lastRomanNumberAnalyzed, String numberBeingAnalyzed) {
        return lastRomanNumberAnalyzed != null && romanNumerals.get(lastRomanNumberAnalyzed) < romanNumerals.get(numberBeingAnalyzed);
    }

    private boolean isAddition(String lastRomanNumberAnalyzed, String numberBeingAnalyzed) {
        return lastRomanNumberAnalyzed != null && romanNumerals.get(lastRomanNumberAnalyzed) >= romanNumerals.get(numberBeingAnalyzed);
    }

    private void validRomanNumberBeforeCurrent(String roman, String[] inputedCharacters, int i) {
        String beforeNumber = i > 0 ? inputedCharacters[i - 1] : null;
        String currentNumber = inputedCharacters[i];

        if (i > 0 && allowedDecreasingValues.get(beforeNumber) == null) {
            throw new IllegalArgumentException(roman + " It's not a valid Roman numeral. The numeral " + beforeNumber + " can not be used to decrease " + currentNumber);
        } else if (i > 0 && !allowedDecreasingValues.get(beforeNumber).contains(currentNumber)) {
            String allowedValues = allowedDecreasingValues.get(beforeNumber).stream().collect(Collectors.joining(","));
            throw new IllegalArgumentException(roman + " It's not a valid Roman numeral. The numeral " + beforeNumber + " can be only used to subtract these numbers: " + allowedValues);
        } else if (i > 0 && i - 2 >= 0 && inputedCharacters[i - 2].equals(beforeNumber)) {
            throw new IllegalArgumentException(roman + " It's not a valid Roman numeral "
                    + inputedCharacters[i - 2] + " can be only used to subtract " + currentNumber + " here"
                    + ". If you want to use " + inputedCharacters[i - 2] + " toggether try to write it after subtraction."
            );
        }
    }

    private void basicValidationsForRomanNumeral(String[] romanNumeral) {
        Map<String, Integer> countRepeatedValues = new HashMap<>();
        Stream<String> stream = Arrays.stream(romanNumeral);
        final String[] previusValue = {""};
        stream.forEach(roman -> {
            if (!this.romanNumerals.containsKey((roman))) {
                throw new IllegalArgumentException("Invalid Roman numeral");
            } else {
                int newNumber = countRepeatedValues.get(roman) != null && previusValue[0].equals(roman) ? (countRepeatedValues.get(roman) + 1) : 1;
                countRepeatedValues.put(roman, newNumber);
                previusValue[0] = roman;
                if (newNumber > 3)
                    throw new IllegalArgumentException(roman + "It's not a Roman numeral. It's allowed to repeat the same numeral for only three times.");
            }
        });


    }
}
