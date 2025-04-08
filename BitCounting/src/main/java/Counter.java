/**
 * @author Ivan Ivanov
 **/
public class Counter {
    public int countBits(int n){
        int bits = 0;
        while(n != 0){
            if(n%2 == 1){
                bits++;
            }
            n = n / 2;
        }
        return bits;
    }
}
