package edu.barry.euclid.mobile_crypto;

/**
 * Taken from: http://rosettacode.org/wiki/Miller-Rabin_primality_test#Java
 *
 * Created by lukas on 3/7/15.
 */
import java.math.BigInteger;

public class MillerRabinPrimalityTest {
    public static void main(String[] args) {
        BigInteger n = new BigInteger(args[0]);
        int certainty = Integer.parseInt(args[1]);
        System.out.println(n.toString() + " is " + (n.isProbablePrime(certainty) ? "probably prime" : "composite"));
    }
}