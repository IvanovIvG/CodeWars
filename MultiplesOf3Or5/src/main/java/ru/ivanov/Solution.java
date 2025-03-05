package ru.ivanov;

/**
 * @author Ivan Ivanov
 **/
public class Solution {
    public int solution(int number) {
        int sum = 0;
        for(int i=3; i<number; i=i+3){
            sum += i;
        }
        for(int i=5; i<number; i=i+5){
            sum += i;
        }
        for(int i=15; i<number; i=i+15){
            sum -= i;
        }
        return sum;
    }
}
