package baitap_AES_RSA.bai2;

public class EncryptionTest {
	public static void main(String[] args) throws Exception {
		String data = "Hello Encryptable!";

        // AES
        Encryptable aes = new AESEncryption();
        String aesEncrypted = aes.encrypt(data);
        String aesDecrypted = aes.decrypt(aesEncrypted);
        System.out.println("AES Encrypted: " + aesEncrypted);
        System.out.println("AES Decrypted: " + aesDecrypted);

        // RSA
        Encryptable rsa = new RSAEncryption();
        String rsaEncrypted = rsa.encrypt(data);
        String rsaDecrypted = rsa.decrypt(rsaEncrypted);
        System.out.println("RSA Encrypted: " + rsaEncrypted);
        System.out.println("RSA Decrypted: " + rsaDecrypted);
    }
}
