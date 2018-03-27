package safe;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 对称加密算法
 */

public enum SymmetricAlgorith implements SafetyAlgorithm {
    // 8位密钥
    DES("DES", "DES") {
        @Override
        SecretKey getSecretKey(byte[] key) throws Exception {
            return keyFactory.generateSecret(new DESKeySpec(key));
        }
    },
    // 24位密钥
    TDES("DESede", "DESede") {
        @Override
        SecretKey getSecretKey(byte[] key) throws Exception {
            return keyFactory.generateSecret(new DESedeKeySpec(key));
        }
    },
    // 16位密钥
    AES("AES", "AES") {

        @Override
        SecretKey getSecretKey(byte[] key) throws Exception {
//			KeyGenerator kgen = KeyGenerator.getInstance("AES");
//			kgen.init(128, new SecureRandom(key));
//			SecretKey secretKey = kgen.generateKey();
//			byte[] enCodeFormat = secretKey.getEncoded();
//			return new SecretKeySpec(enCodeFormat, "AES");
            return new SecretKeySpec(key, "AES");
        }

    }
    ;

    String algorithmName;
    String algorithmMode;
    // DES算法要求有一个可信任的随机数源
//	SecureRandom sr = new SecureRandom();
    // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
    SecretKeyFactory keyFactory;
    // Cipher对象实际完成加密操作
    Cipher cipher;

    SymmetricAlgorith(String algorithmName, String algorithmMode) {
        this.algorithmName = algorithmName;
        this.algorithmMode = algorithmMode;
        try {
            cipher = Cipher.getInstance(algorithmMode);
            keyFactory = SecretKeyFactory.getInstance(algorithmName);
        } catch (Exception e) {
            //SecretKeyFactory会报告 AES 无此算法，请忽略
//			e.printStackTrace();
        }
    }

    abstract SecretKey getSecretKey(byte[] key) throws Exception;

    @Override
    public byte[] encrypt(byte[] plaintext, byte[] key) throws Exception {
        System.out.println("keysize:" + key.length);
        // 从原始密匙数据创建DESKeySpec对象
        SecretKey securekey = getSecretKey(key);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey);//, sr
        // 正式执行加密操作
        return cipher.doFinal(plaintext);

    }

    @Override
    public byte[] decrypt(byte[] ciphertext, byte[] key) throws Exception {
        // 从原始密匙数据创建DESKeySpec对象
        SecretKey securekey = getSecretKey(key);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey); //, sr
        // 正式执行解密操作
        return cipher.doFinal(ciphertext);
    }
}
