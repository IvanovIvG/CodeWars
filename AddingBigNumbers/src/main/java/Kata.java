/**
 * @author Ivan Ivanov
 **/
public class Kata {
    public static String add(String a, String b) {
        int numbersRank = Math.max(a.length(), b.length());
        char[] x = createNumArray(a, numbersRank);
        char[] y = createNumArray(b, numbersRank);
        char[] sum = createSumArray(numbersRank);

        for(int i = 0; i < numbersRank; i++){
            int n = x[i] - '0';
            int m = y[i] - '0';

            int l = n + m + sum[i] - '0';
            if(l >= 10){
                l -= 10;
                sum[i] = Character.forDigit(l, 10);
                sum[i + 1] = '1';
            }
            else {
                sum[i] = Character.forDigit(l, 10);
            }
        }

        return convertToString(sum);
    }


    private static char[] createNumArray(String string, int rank) {
        char[] stringChars = string.toCharArray();
        int numLength = stringChars.length;
        char[] numArray = new char[rank];
        for (int i = 0; i < numLength; i++) {
            numArray[i] = stringChars[numLength - i - 1];
        }
        for(int i = numLength; i < rank; i++){
            numArray[i] = '0';
        }
        return numArray;
    }

    private static char[] createSumArray(int numbersRank) {
        char[] sum = new char[numbersRank + 1];
        for(int i = 0; i < numbersRank+1; i++){
            sum[i] = '0';
        }
        return sum;
    }

    private static String convertToString(char[] numArray) {
        StringBuilder result = new StringBuilder();
        int numRank = findNumRank(numArray);
        for(int i = numRank; i >= 1; i--){
            result.append(numArray[i - 1]);
        }
        return result.toString();
    }

    private static int findNumRank(char[] numArray) {
        int numRank = numArray.length;
        for(int i = numArray.length-1; i >= 0; i--){
            if(numArray[i] != '0')
                break;
            numRank--;
        }
        return numRank;
    }
}
