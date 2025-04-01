import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author Ivan Ivanov
 **/
class
RomanNumeralsTest {
    @Test
    public void testToRoman() throws Exception {
        assertEquals(RomanNumerals.toRoman(1), "I");
        assertEquals(RomanNumerals.toRoman(2), "II");
        assertEquals(RomanNumerals.toRoman(86), "LXXXVI");
        assertEquals(RomanNumerals.toRoman(996), "CMXCVI");
        assertEquals(RomanNumerals.toRoman(1666), "MDCLXVI");
        assertEquals(RomanNumerals.toRoman(2000), "MM");
    }

    @Test
    public void testFromRoman() throws Exception {
        assertEquals(RomanNumerals.fromRoman("I"), 1);
        assertEquals(RomanNumerals.fromRoman("II"), 2);
        assertEquals(RomanNumerals.fromRoman("LXXXVI"), 86);
        assertEquals(RomanNumerals.fromRoman("CMXCVI"), 996);
        assertEquals(RomanNumerals.fromRoman("MDCLXVI"), 1666);
        assertEquals(RomanNumerals.fromRoman("MM"), 2000);

    }

}