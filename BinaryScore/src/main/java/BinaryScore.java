import java.math.BigInteger;

/**
    * @author Ivan Ivanov
**/
public class BinaryScore {
    public static BigInteger score (BigInteger n) {
        return BigInteger.valueOf(2).pow(n.bitLength()).subtract(BigInteger.valueOf(1));
    }
}
