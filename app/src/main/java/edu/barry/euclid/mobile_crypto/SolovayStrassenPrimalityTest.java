package edu.barry.euclid.mobile_crypto;

import java.util.Scanner;
import java.util.Random;

/**
 * Taken from: http://www.sanfoundry.com/java-program-solovay-strassen-primality-test-algorithm/
 * Created by lukas on 3/7/15.
 */


public class SolovayStrassenPrimalityTest {
    private final static int ITERATIONS = 1;

    private int n;
    public SolovayStrassenPrimalityTest(int number){
        this.n = number;
    }
    private long Jacobi(long a, long b)  {
        if (b <= 0 || b % 2 == 0)
            return 0;
        long j = 1L;
        if (a < 0) {
            a = -a;
            if (b % 4 == 3)
                j = -j;
        }
        while (a != 0) {
            while (a % 2 == 0) {
                a /= 2;
                if (b % 8 == 3 || b % 8 == 5)
                    j = -j;
            }

            long temp = a;
            a = b;
            b = temp;

            if (a % 4 == 3 && b % 4 == 3)
                j = -j;
            a %= b;
        }
        if (b == 1)
            return j;
        return 0;
    }
    /** Function to check if prime or not **/
    public boolean isPrime()  {
        /** base case **/
        if (n == 0 || n == 1)
            return false;
        /** base case - 2 is prime **/
        if (n == 2)
            return true;
        /** an even number other than 2 is composite **/
        if (n % 2 == 0)
            return false;

        Random rand = new Random();
        for (int i = 0; i < ITERATIONS; i++)  {
            long r = Math.abs(rand.nextLong());
            long a = r % (n - 1) + 1;
            long jacobian = (n + Jacobi(a, n)) % n;
            long mod = modPow(a, (n - 1)/2, n);
            if(jacobian == 0 || mod != jacobian)
                return false;
        }
        return true;
    }
    /** Function to calculate (a ^ b) % c **/
    private long modPow(long a, long b, long c)  {
        long res = 1;
        for (int i = 0; i < b; i++)
        {
            res *= a;
            res %= c;
        }
        return res % c;
    }
//    /** Main function **/
//    public static void main (String[] args) {
//        Scanner scan = new Scanner(System.in);
//        System.out.println("SolovayStrassen Primality Algorithm Test\n");
//        /** Make an object of SolovayStrassen class **/
//        SolovayStrassenPrimalityTest ss = new SolovayStrassenPrimalityTest();
//        /** Accept number **/
//        System.out.println("Enter number\n");
//        long num = scan.nextLong();
//        /** Accept number of iterations **/
//        System.out.println("\nEnter number of iterations");
//        int k = scan.nextInt();
//        /** check if prime **/
//        boolean prime = ss.isPrime(num, k);
//        if (prime)
//            System.out.println("\n"+ num +" is prime");
//        else
//            System.out.println("\n"+ num +" is composite");
//    }
}