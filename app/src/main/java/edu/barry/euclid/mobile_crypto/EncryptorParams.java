package edu.barry.euclid.mobile_crypto;

/**
 * Created by hugo on 4/29/15.
 */

/*
 * Defines the structure to be used for the EncryptionWorker params since they need to be passed into
 * the AsyncTask template as one type.
 */
public class EncryptorParams {
    public EncryptionHandler encryptor;
    public int rounds;

    public EncryptorParams(EncryptionHandler encryptor, int rounds) {
        this.encryptor = encryptor;
        this.rounds = rounds;
    }
}
