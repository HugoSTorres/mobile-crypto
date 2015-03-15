package edu.barry.euclid.mobile_crypto;

import android.util.Log;

// ciphers
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by lukas on 3/15/15.
 */
public class EncryptionHandler {
    // Constant string definitions
    private static final String AES = "AES";
    private static final String TRIPLE_DES = "3DES";
    private static final String BLOWFISH = "Blowfish";

    private final static String HEX = "0123456789ABCDEF";

    private Cipher cipher; // our cipher for the algorithm
    private int keyLenght; //key length of the algorithm
    private String method;

    void EncryptionHandler(String method){
        this.method = method;

        // just checks to ensure that we have an algorithm ready
        if (!(method.equalsIgnoreCase(EncryptionHandler.AES) || method.equalsIgnoreCase(EncryptionHandler.TRIPLE_DES)
                || method.equalsIgnoreCase(EncryptionHandler.BLOWFISH)){
            Log.e("ENCRYPTION_HANDLER", "Wrong encryption method passed");
            return;
        }

        // assigns the cipher
        try {
           if (method.equalsIgnoreCase(EncryptionHandler.AES)) {
               this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // this gets AES
               this.keyLenght = 128;
           } else if (method.equalsIgnoreCase(EncryptionHandler.TRIPLE_DES)) {
               this.cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
               this.keyLenght = 112; // we can change that, it doesn't matter
           } else if (method.equalsIgnoreCase(EncryptionHandler.BLOWFISH)) {
               this.cipher = Cipher.getInstance("Blowfish");
               this.keyLenght = 128;
           } else
                throw new Exception(); //this should never happen
        } catch (Exception e){
            // this should never happen
            Log.e("ENCRYPTION_HANDLER", "Wrong encryption method passed in the cipher assignment");
            return;
        }
    }

    public String encrypt(String seed, String cleartext) throws Exception {
        byte[] rawKey = this.getRawKey(seed.getBytes());
        byte[] result = encrypt(rawKey, cleartext.getBytes());
        return toHex(result);
    }

    private byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        Cipher cipher = this.cipher;

        // in this cases 3DES needs a separate handling for two reasons:
        // 1. it needs IV parameter, which the other two don't
        // 2. it's name is DESede, instead of 3DES

        if (this.method.equalsIgnoreCase(EncryptionHandler.TRIPLE_DES)){
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "DESede");
            IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        } else {
            SecretKeySpec skeySpec = new SecretKeySpec(raw, this.method);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        }
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(seed);
        kgen.init(this.keyLenght, sr); // 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }

    private static String toHex(String text) {
        return toHex(text.getBytes());
    }

    private static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2*buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));
    }
}
