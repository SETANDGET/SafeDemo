package safe;

/**
 * 安全算法接口，实现接口的算法，应具备加密和解密功能，运行抛出异常
 */
public interface SaftyAlgorithm {
    byte[] encrypt(byte[] input, byte[] key) throws  Exception;
    byte[] decrypt(byte[] input, byte[] key) throws  Exception;
}
