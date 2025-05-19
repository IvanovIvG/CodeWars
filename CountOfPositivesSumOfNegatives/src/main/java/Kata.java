/**
 * @author Ivan Ivanov
 **/
public class Kata {
    public static int[] countPositivesSumNegatives(int[] input) {
        if (input==null || input.length == 0){
            return new int[]{};
        }
        int positivesCounter = 0;
        int negativeSum = 0;
        for (int number: input){
            if (number>0){
                positivesCounter++;
            }
            if (number<0){
                negativeSum += number;
            }
        }
        return new int[] {positivesCounter, negativeSum};
    }
}
