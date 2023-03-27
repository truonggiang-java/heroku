package com.example.springboot3.image;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AES {
	private static final String SECRET_KEY_ALGORITHM = "AES";
	private static final String CIPHER_TRANSFORMATION = "AES/ECB/PKCS5Padding";

	public static String encrypt(String input, String key) throws Exception {
		byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, SECRET_KEY_ALGORITHM);

		Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec);

		byte[] encryptedBytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	public static String decrypt(String input, String key) throws Exception {
		byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, SECRET_KEY_ALGORITHM);

		Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
		cipher.init(Cipher.DECRYPT_MODE, keySpec);

		byte[] encryptedBytes = Base64.getDecoder().decode(input);
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		return new String(decryptedBytes, StandardCharsets.UTF_8);
	}

	public static void main(String[] args) throws Exception {
		String input = "0985934199";
		String key = "N2IwN5ExYjM4nDdj";

		String encrypted = encrypt(input, key);
		System.out.println("Encrypted: " + encrypted);

		String decrypted = decrypt(encrypted, key);
		System.out.println("Decrypted: " + decrypted);
	}
}
