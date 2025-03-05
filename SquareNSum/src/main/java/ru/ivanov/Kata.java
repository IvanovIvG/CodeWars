package ru.ivanov;

/**
 * @author Ivan Ivanov
 **/
public class Kata {
    public static int squareSum(int[] n)
    {
        int sum = 0 ;
        for (int j : n) {
            sum += (int) Math.pow(j, 2);
        }
        return sum;
    }
}
