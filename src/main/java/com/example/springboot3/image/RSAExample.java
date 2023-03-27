package com.example.springboot3.image;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class RSAExample {
	public static void main(String[] args) throws Exception {
        // Generate a key pair
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // Create a cipher object
        Cipher cipher = Cipher.getInstance("RSA");

        // Encrypt the plaintext
        String plaintext = "This is a secret message";
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] ciphertext = cipher.doFinal(plaintext.getBytes());

        // Print the encrypted ciphertext
        System.out.println("Ciphertext: " + new String(ciphertext));

        // Decrypt the ciphertext
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedText = cipher.doFinal(ciphertext);

        // Print the decrypted plaintext
        System.out.println("Decrypted plaintext: " + new String(decryptedText));
    }
}
