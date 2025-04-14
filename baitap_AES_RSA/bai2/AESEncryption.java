package baitap_AES_RSA.bai2;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryption  implements Encryptable{
   private final String secretKey = "1234567890123456";
   private final SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
   
   @Override
	public String encrypt(String plainText) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		byte[] encrypted = cipher.doFinal(plainText.getBytes());
		return Base64.getEncoder().encodeToString(encrypted);
	}

@Override
public String decrypt(String encryptedText) throws Exception {
	Cipher cipher = Cipher.getInstance("AES");
	cipher.init(Cipher.DECRYPT_MODE, keySpec);
	byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
	return new String(decrypted);
}
}
