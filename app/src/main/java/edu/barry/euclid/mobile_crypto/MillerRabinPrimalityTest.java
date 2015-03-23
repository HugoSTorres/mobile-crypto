package edu.barry.euclid.mobile_crypto;

/**
 * Taken from: http://rosettacode.org/wiki/Miller-Rabin_primality_test#Java
 *
 * Created by lukas on 3/7/15.
 */
import java.math.BigInteger;

public class MillerRabinPrimalityTest {
    private static final int CERTAINTY = 1000000;
    public MillerRabinPrimalityTest() {    }

    public Boolean isPrime(BigInteger n){
        return n.isProbablePrime(CERTAINTY);
    }
}