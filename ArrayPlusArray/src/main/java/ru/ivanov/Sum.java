package ru.ivanov;

import java.util.Arrays;

/**
 * @author Ivan Ivanov
 **/
public class Sum {
    public static int arrayPlusArray(int[] arr1, int[] arr2) {
        return Arrays.stream(arr1).sum() + Arrays.stream(arr2).sum();
    }

}
