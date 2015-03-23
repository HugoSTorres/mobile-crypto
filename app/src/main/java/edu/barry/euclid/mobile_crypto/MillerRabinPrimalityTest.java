package edu.barry.euclid.mobile_crypto;

/**
 * Taken from: http://rosettacode.org/wiki/Miller-Rabin_primality_test#Java
 *
 * Created by lukas on 3/7/15.
 */
import java.math.BigInteger;

public class MillerRabinPrimalityTest {
    private static final int CERTAINTY = 1000000;

    BigInteger n;
    public MillerRabinPrimalityTest(BigInteger number) {
        this.n = number;
    }

    public Boolean isPrime(){
        return this.n.isProbablePrime(CERTAINTY);
    }
}