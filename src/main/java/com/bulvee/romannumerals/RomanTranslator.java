package com.bulvee.romannumerals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RomanTranslator {
    private Map<String, Integer> romanNumerals = Map.of("I", 1, "V", 5, "X", 10, "L", 50, "C", 100, "D", 500, "M", 1000);

    private String repeatedRomanNumber;
    private Map<String, Set<String>> allowedDecreasingValues;


    public RomanTranslator() {
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

//            if(i > 1){
//                int indexForValidation = i-2;
//                String twoStepsBefor = inputedCharacters[indexForValidation];
//                if(romanNumerals.get(twoStepsBefor) < romanNumerals.get(analyzingCharacter)){
//                    throw new IllegalArgumentException(roman + " It's not a valid Roman numeral. You can't use " + twoStepsBefor + " at " + indexForValidation + " index.");
//                }
//            }
//            else


//            if(i>1 && inputedCharacters[-1].equals(numberBeingAnalyzed) && romanNumerals.get(inputedCharacters[-2]) < romanNumerals.get(inputedCharacters[1])){
//                throw new IllegalArgumentException(roman + " It's not a valid Roman numeral. You should write Roman numerals in ascending order whenever possible. It not valid: "
//                        + inputedCharacters[-2] + " before " + numberBeingAnalyzed);
//            }


            if (lastRomanNumberAnalyzed != null && romanNumerals.get(lastRomanNumberAnalyzed) >= romanNumerals.get(numberBeingAnalyzed)) {
                decimalNumber += romanNumerals.get(numberBeingAnalyzed);
                lastRomanNumberAnalyzed = numberBeingAnalyzed;

            } else if (lastRomanNumberAnalyzed != null && romanNumerals.get(lastRomanNumberAnalyzed) < romanNumerals.get(numberBeingAnalyzed)) {
                validRomanNumberBeforeCurrent(roman, inputedCharacters, i);
                decimalNumber += romanNumerals.get(numberBeingAnalyzed) - (romanNumerals.get(lastRomanNumberAnalyzed) * 2);
                lastRomanNumberAnalyzed = numberBeingAnalyzed;

            } else if (roman.length() == 1) {
                decimalNumber = romanNumerals.get(numberBeingAnalyzed);

            } else {
                lastRomanNumberAnalyzed = numberBeingAnalyzed;
                decimalNumber = romanNumerals.get(numberBeingAnalyzed);
            }
        }
        return decimalNumber;
    }

    private void validRomanNumberBeforeCurrent(String roman, String[] inputedCharacters, int i) {
        String beforeNumber = i > 0 ? inputedCharacters[i - 1] : null;
        String currentNumber = inputedCharacters[i];

        if (i > 0 && !allowedDecreasingValues.get(beforeNumber).contains(currentNumber)) {
            String allowedValues = allowedDecreasingValues.get(beforeNumber).stream().collect(Collectors.joining(","));
            throw new IllegalArgumentException(roman + " It's not a valid Roman numeral. The numeral " + beforeNumber + " can be only used to subtract these numbers: "  +  allowedValues);
        }
    }

    private void basicValidationsForRomanNumeral(String[] romanNumeral) {
        Map<String, Integer> countRepeatedValues = new HashMap<>();
        Stream<String> stream = Arrays.stream(romanNumeral);

        stream.forEach(roman -> {
            if (!this.romanNumerals.containsKey((roman))) {
                throw new IllegalArgumentException("Invalid Roman numeral");
            } else {
                int newNumber = countRepeatedValues.get(roman) == null ? 1 : (countRepeatedValues.get(roman) + 1);
                countRepeatedValues.put(roman, newNumber);
                if(newNumber > 3) throw new IllegalArgumentException(roman + "It's not a Roman numeral. It's allowed to repeat the same numeral for only three times.");
            }
        });



    }

//    private boolean isValidSubtraction(String valueToBeAnalyzed, String previousValue) {
//        String previusValue = allowedDecreasingValues.get(valueToBeAnalyzed);
//        return previusValue.equals(previousValue);
//    }


}
