package safe;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * 非对称的公钥加解密算法
 */

public enum AsymmetricPublicAlgorithm implements SafetyAlgorithm {
    RSA("RSA","RSA/ECB/PKCS1PADDING"); // ,ECC

    String algorithmName;

    private AsymmetricPublicAlgorithm(String algorithmName,String mode) {
        this.algorithmName = algorithmName;
        try {
            keyFactory = KeyFactory.getInstance(algorithmName);
            cipher = Cipher.getInstance(mode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    KeyFactory keyFactory;
    Cipher cipher;

    @Override
    public byte[] encrypt(byte[] input, byte[] key) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
        // 取得私钥
        Key privateKey = keyFactory.generatePublic(keySpec);
        // 对数据加密
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return AsymmetricPrivateAlgorithm.splitCipherOperate(cipher, 117, input);
//		return cipher.doFinal(input);
    }

    @Override
    public byte[] decrypt(byte[] input, byte[] key) throws Exception {
        // 取得私钥
        X509EncodedKeySpec pkcs8KeySpec = new X509EncodedKeySpec(key);
        Key privateKey = keyFactory.generatePublic(pkcs8KeySpec);
        // 对数据解密
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return AsymmetricPrivateAlgorithm.splitCipherOperate(cipher, 128, input);
//		return cipher.doFinal(input);
    }
}