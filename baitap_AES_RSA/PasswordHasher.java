package baitap_AES_RSA;

import java.security.MessageDigest;
import java.security.MessageDigestSpi;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {
	public static String hashPassword(String password, String algo) {
		try {
			MessageDigest md = MessageDigest.getInstance(algo);
			byte[] hashedBytes = md.digest(password.getBytes());
			
			StringBuilder sb = new StringBuilder();
			for(byte b : hashedBytes) {
				sb.append(String.format("%02x",b));
			}
			return sb.toString();
		}catch(NoSuchAlgorithmException e) {
			throw new RuntimeException("Thuật toán ko hợp lệ", e);
		}
	}
	public static void main(String[] args) {
		String password = "admin789";
		
		String hashedMD5 = hashPassword(password, "MD5");// SHA-256
		
		System.out.println("MD5: "+ hashedMD5);
	}
}
