package com.bulvee.romannumerals;

public class Main {
    public static void main(String[] args) {
        RomanNumeralConverter romanNumeralConverter = new RomanNumeralConverter();
        String bigTest = "";
        int decimalNumber = romanNumeralConverter.romanToInt(bigTest);
        System.out.println(decimalNumber);
    }
}