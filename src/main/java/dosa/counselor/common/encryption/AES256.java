package dosa.counselor.common.encryption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class AES256 {

    public static String alg; // Algorithm, Cipher Mode, Padding Mode
    private static String key; // SecretKey, 32byte
    private static String iv; // Initialize Vector, 16byte

    @Value("${aes256.alg}")
    public void setAlg(String alg){
        this.alg = alg;
    }

    @Value("${aes256.key}")
    public void setKey(String key){
        this.key = key;
    }

    @Value("${aes256.iv}")
    public void setIv(String iv){
        this.iv = iv;
    }

    // 암호화
    public static String encrypt(String text) {
        try {
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

            byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        }catch (Exception e){
            return text;
        }

    }

    // 복호화
    public static String decrypt(String cipherText) {
        try{
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

            byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
            byte[] decrypted = cipher.doFinal(decodedBytes);
            return new String(decrypted, "UTF-8");
        }catch (Exception e){
            return cipherText;
        }
    }
}
