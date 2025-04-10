import java.math.BigInteger;

/**
 * @author Ivan Ivanov
 **/
public class Fibonacci {
    public static BigInteger fib(BigInteger n) {
        if(n.compareTo(BigInteger.ZERO) < 0){
            return minusOneExp(n.negate()).multiply(f(n.negate()));
        }
        else{
            return f(n);
        }
    }

    private static BigInteger minusOneExp(final BigInteger n){
        if(n.add(BigInteger.ONE).mod(BigInteger.TWO).equals(BigInteger.ZERO)){
            return BigInteger.ONE;
        }
        else {
            return BigInteger.ONE.negate();
        }
    }

    private static BigInteger f(BigInteger n){
        if (n.compareTo(BigInteger.TWO) <= 0) {
            if(n.compareTo(BigInteger.ZERO) == 0)
                return BigInteger.ZERO;
            return BigInteger.ONE;
        }
        if(n.mod(BigInteger.TWO).equals(BigInteger.ZERO)){
            BigInteger m = n.divide(BigInteger.TWO);
            BigInteger fM1 = f(m.add(BigInteger.ONE));
            BigInteger fM_1 = f(m.subtract(BigInteger.ONE));
            BigInteger fM1Sq = fM1.multiply(fM1);
            BigInteger fM_1Sq = fM_1.multiply(fM_1);
            return fM1Sq.subtract(fM_1Sq);
        }
        else{
            BigInteger m = n.subtract(BigInteger.ONE);
            m = m.divide(BigInteger.TWO);
            BigInteger fM1 = f(m.add(BigInteger.ONE));
            BigInteger fM = f(m);
            BigInteger fM1Sq = fM1.multiply(fM1);
            BigInteger fMSq = fM.multiply(fM);
            return fM1Sq.add(fMSq);
        }
    }
}
