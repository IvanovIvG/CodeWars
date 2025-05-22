import java.util.List;

/**
 * @author Ivan Ivanov
 **/
public class Vowels {
    public static int getCount(String str) {
        List<Character> vowels = List.of('a', 'e', 'i', 'o', 'u');
        int counter = 0;
        for(char chr: str.toCharArray()){
            if(vowels.contains(chr)){
                counter++;
            }
        }
        return counter;
    }
}
