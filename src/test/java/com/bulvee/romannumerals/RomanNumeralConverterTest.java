package com.bulvee.romannumerals;


import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
class RomanNumeralConverterTest {

    @Test
    public void testRomanToInt_I() {
        RomanNumeralConverter translator = new RomanNumeralConverter();
        int result = translator.romanToInt("I");
        assertEquals(1, result);
    }

    @Test
    public void testRomanToInt_V() {
        RomanNumeralConverter translator = new RomanNumeralConverter();
        int result = translator.romanToInt("V");
        assertEquals(5, result);
    }

    @Test
    public void testRomanToInt_IV() {
        RomanNumeralConverter translator = new RomanNumeralConverter();
        int result = translator.romanToInt("IV");
        assertEquals(4, result);
    }

    @Test
    public void testRomanToInt_XII() {
        RomanNumeralConverter translator = new RomanNumeralConverter();
        int result = translator.romanToInt("XII");
        assertEquals(12, result);
    }

    @Test
    public void testRomanToInt_MCMXCIV() {
        RomanNumeralConverter translator = new RomanNumeralConverter();
        int result = translator.romanToInt("MCMXCIV");
        assertEquals(1994, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRomanToInt_Invalid_VX() {
        RomanNumeralConverter translator = new RomanNumeralConverter();
        translator.romanToInt("VX");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRomanToInt_Invalid_IL() {
        RomanNumeralConverter translator = new RomanNumeralConverter();
        translator.romanToInt("IL");
    }

}