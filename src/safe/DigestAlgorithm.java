package safe;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 摘要算法
 */
public enum  DigestAlgorithm implements SafetyAlgorithm {
    MD5("MD5"),SHA1("SHA-1");


    MessageDigest md ;

    String digestName;

    DigestAlgorithm(String digestName) {
        this.digestName = digestName;
        try {
            md =   MessageDigest.getInstance(digestName);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    @Override
    public byte[] encrypt(byte[] input, byte[] key) {
        return md.digest(input);
    }

    @Override
    public byte[] decrypt(byte[] input, byte[] key) {
        throw  new UnsupportedOperationException("摘要算法无法解密");
    }
}
