import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author Ivan Ivanov
 **/
public class RomanNumerals {
    public static String toRoman(int n) {
        List<RomanNumeral> romanNumerals = RomanNumeral.descendingOrder();
        int leftN = n;
        StringBuilder result = new StringBuilder();
        for(RomanNumeral romanNumeral : romanNumerals){
            int numeralNumber = Math.floorDiv(leftN, romanNumeral.getNumber());
            result.append(String.valueOf(romanNumeral.getCharacter()).repeat(Math.max(0, numeralNumber)));
            leftN -= numeralNumber * romanNumeral.getNumber();
        }
        return result.toString();
    }

    public static int fromRoman(String romanNumeral) {
        int result = 0;
        List<RomanNumeral> numbers = convertToEnum(romanNumeral);
        for (int i = 0; i < numbers.size() - 1; i++) {
            RomanNumeral currentNum = numbers.get(i);
            RomanNumeral nextNum = numbers.get(i+1);
            if(currentNum.isLess(nextNum)){
                result -= currentNum.getNumber();
            }
            else{
                result += currentNum.getNumber();
            }
        }
        result += numbers.getLast().getNumber();
        return result;
    }

    private static List<RomanNumeral> convertToEnum(String romanNumeral) {
        List<RomanNumeral> enums = new ArrayList<>();
        char[] characters = romanNumeral.toCharArray();
        for(char character: characters){
            String characterString = String.valueOf(character);
            RomanNumeral romanNumeralEnum = RomanNumeral.valueOf(characterString);
            enums.add(romanNumeralEnum);
        }
        return enums;
    }
}

enum RomanNumeral {
    M(1000, "M"),
    CM(900, "CM"),
    D(500, "D"),
    CD(400, "CD"),
    C(100, "C"),
    XC(90, "XC"),
    L(50, "L"),
    XL(40, "XL"),
    X(10, "X"),
    IX(9, "IX"),
    V(5, "V"),
    IV(4, "IV"),
    I(1, "I");

    private final int number;
    private final String character;

    RomanNumeral(int number, String character) {
        this.number = number;
        this.character = character;
    }

    public static List<RomanNumeral> descendingOrder(){
        return Arrays.stream(RomanNumeral.values()).
                sorted(Comparator.comparingInt(RomanNumeral::getNumber).reversed()).toList();
    }

    public boolean isLess(RomanNumeral number){
        return this.number < number.number;
    }

    public int getNumber() {
        return number;
    }

    public String getCharacter() {
        return character;
    }
}
