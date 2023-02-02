package com.bulvee.romannumerals;

public class Main {
    public static void main(String[] args) {
        RomanTranslator romanTranslator = new RomanTranslator();
//        String bigTest = "MMCCM"; --> falta criar validação deste cenário
        String bigTest = "MMCCM"; // bug
        int decimalNumber = romanTranslator.romanToInt(bigTest);
        System.out.println(decimalNumber);
    }
}
//999