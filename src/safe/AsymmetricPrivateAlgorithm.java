package safe;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * 非对称的私钥加解密算法
 */
public enum AsymmetricPrivateAlgorithm implements SafetyAlgorithm {
	RSA("RSA","RSA/ECB/PKCS1PADDING"); // ,ECC

	String algorithmName;
	KeyFactory keyFactory;
	Cipher cipher;

	private AsymmetricPrivateAlgorithm(String algorithmName,String mode) {
		this.algorithmName = algorithmName;
		try {
			keyFactory = KeyFactory.getInstance(algorithmName);
			cipher = Cipher.getInstance(mode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public byte[] encrypt(byte[] input, byte[] key) throws Exception {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
		// 取得私钥
		Key privateKey = keyFactory.generatePrivate(keySpec);

		// 对数据加密
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
	
		return splitCipherOperate(cipher, 117, input);
		
//		return cipher.doFinal(input);
	}

	public static byte[] splitCipherOperate(Cipher cipher , int len, byte[] input) throws Exception{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[len];
		int times =0;
		int start = len * times;
		while (start < input.length){
			if(input.length - start <len){
				buf = new byte[input.length - start];
				System.arraycopy(input, start, buf, 0, input.length - start);	
			}else{
				
				System.arraycopy(input, start, buf, 0, len);
			}
			
			baos.write(cipher.doFinal(buf));
			times ++;
			start += len;
		}
		return baos.toByteArray();
	}
	
	
	@Override
	public byte[] decrypt(byte[] input, byte[] key) throws Exception {
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 对数据解密
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		
		return splitCipherOperate(cipher, 128, input);
//		return cipher.doFinal(input);
	}
}
