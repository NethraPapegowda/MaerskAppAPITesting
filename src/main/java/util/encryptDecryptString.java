package util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class encryptDecryptString {
	
	  private static final String KEY = "1234567890123456"; // 16-byte secret key

	    public static String encrypt(String strToEncrypt) {
	        try {
	            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");
	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException("Error while encrypting", e);
	        }
	    }

	    public static String decrypt(String strToDecrypt) {
	        try {
	            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");
	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	            cipher.init(Cipher.DECRYPT_MODE, secretKey);
	            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException("Error while decrypting", e);
	        }
	    }

}
