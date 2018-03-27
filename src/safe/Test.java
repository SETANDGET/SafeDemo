package safe;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

/**
 * 测试加密解密能否正常工作
 */
public class Test {
	
	static String conStr = "KA4uVdw-ccyPY6mfr24n-Jq0fsNNb6UmKz4MYR6SkETPgBhyXvhTyagAhsKxrwqqVbtSMnpzoOm6LYjakyvnA5F11e1Nz4OXYr-HIbU-qZS4UnMiqzTqiSXQgszIk48hR6yVQpGTN0P1ZhErvytucwoKGLy9MYs69avFzJNwdSU=";

	static String con = "KA4uVdw-ccyPY6mfr24n-Jq0fsNNb6UmKz4MYR6SkETPgBhyXvhTyagAhsKxrwqqVbtSMnpzoOm6\nLYjakyvnA5F11e1Nz4OXYr-HIbU-qZS4UnMiqzTqiSXQgszIk48hR6yVQpGTN0P1ZhErvytucwoK\nGLy9MYs69avFzJNwdSU=\n";
	
	public static void main(String[] args) throws Exception {
		
		String content = "[{\"key\":\"finance\",\"name\":\"??\",\"value\":\"10000\"},"
				+ "{\"key\":\"invest\",\"name\":\"??\",\"value\":\"20000\"},{\"key\":\"fund\",\"name\":\"??\",\"value\":\"30000\"}]}";
		
		
		
//		content = "MG_ZXC";
//		String sign = SafetyUtil.encrypt(AsymmetricPrivateAlgorithm.RSA, content, SafetyUtil.getPriKey());
//		
//		System.out.println("密文："+sign);
		
		
//		String str = SafetyUtil.decrypt(AsymmetricPublicAlgorithm.RSA, con, SafetyUtil.getPubKey());
//		
//		System.out.println("解密："+str);
		
////		// 测试摘要算法
		testDigest();
//
//		// 测试对称加密
		//testSymmetric();
//
//		// 测试非对称加密
		//testAsymmetric();

	}

	private static void testAsymmetric() throws Exception {
		System.out.println("Ras 公钥加密，私钥解密");
		String target1 = "这种算法非常可靠，密钥越长，它就越难破解。根据已经披露的文献，目前被破解的最长RSA密钥是768个二进制位。也就是说，长度超过768位的密钥，还无法破解（至少没人公开宣布）。因此可以认为，1024位的RSA密钥基本安全，2048位的密钥极其安全。";
		byte[] encrypt1 = encrypt(AsymmetricPublicAlgorithm.RSA, BytesHelper.toBytes(target1), getPubKey());
		byte[] decrypt1 = decrypt(AsymmetricPrivateAlgorithm.RSA, encrypt1, getPriKey());
		System.out.println(new String(decrypt1).equals(target1));
		
		
		System.out.println("Ras 私钥加密，公钥解密");
		String target2 = "!!!这种算法非常可靠，密钥越长，它就越难破解。根据已经披露的文献，目前被破解的最长RSA密钥是768个二进制位。也就是说，长度超过768位的密钥，还无法破解（至少没人公开宣布）。因此可以认为，1024位的RSA密钥基本安全，2048位的密钥极其安全。";
		byte[] encrypt2 = encrypt(AsymmetricPrivateAlgorithm.RSA, BytesHelper.toBytes(target2), getPriKey());
		byte[] decrypt2 = decrypt(AsymmetricPublicAlgorithm.RSA, encrypt2, getPubKey());
		System.out.println(new String(decrypt2).equals(target2));
	}

	private static void testDigest() throws Exception {
		System.out.println("md5 for [abcdef]");
		System.out.println(BytesHelper.toHex(encrypt(DigestAlgorithm.MD5, BytesHelper.toBytes("abcdef"), null)));
		System.out.println("sha1 for [abcdef]");
		System.out.println(BytesHelper.toHex(encrypt(DigestAlgorithm.SHA1, BytesHelper.toBytes("abcdef"), null)));

	}

	private static void testSymmetric() throws Exception {
		System.out.println("DES 用密钥[12345678]  加密 解密abcedf");
		System.out.println(endecrypt(SymmetricAlgorith.DES, "abcedf", "12345678"));
		System.out.println("3DES 用密钥[123456781234567812345678]  加密 解密abcedf");
		System.out.println(endecrypt(SymmetricAlgorith.TDES, "abcedf", "123456781234567812345678"));
		System.out.println("AES 用密钥[1234567812345678]  加密 解密abcedf");
		System.out.println(endecrypt(SymmetricAlgorith.AES, "abcedf", "1234567812345678"));

	}

	public static byte[] encrypt(SafetyAlgorithm alg, byte[] source, byte[] key) throws Exception {

		return alg.encrypt(source, key);

	}

	public static byte[] decrypt(SafetyAlgorithm alg, byte[] source, byte[] key) throws Exception {
		return alg.decrypt(source, key);
	}

	private static boolean endecrypt(SymmetricAlgorith alg, String source, String key) throws Exception {
		return endecrypt(alg, BytesHelper.toBytes(source), BytesHelper.toBytes(key));
	}

	private static boolean endecrypt(SafetyAlgorithm alg, byte[] source, byte[] key) throws Exception {
		byte[] encrypt = alg.encrypt(source, key);
		byte[] decrypt = alg.decrypt(encrypt, key);
		if (source.length != decrypt.length) {
			return false;
		}

		for (int i = 0; i < decrypt.length; i++) {
			if (source[i] != decrypt[i]) {
				return false;
			}
		}

		return true;
	}

	static KeyPair keyPair;
	static {

		try {
			KeyPairGenerator pairGenerator = KeyPairGenerator.getInstance("RSA");
			pairGenerator.initialize(1024, new SecureRandom());
			KeyPair keyPair = pairGenerator.generateKeyPair();
			PrivateKey privateKey = keyPair.getPrivate();
			PublicKey publicKey = keyPair.getPublic();
			System.out.println("随机生成的RSA 1024位私钥 , 可用来替换下方的priKey");
			System.out.println(BytesHelper.toBase64(privateKey.getEncoded()));
			System.out.println("随机生成的RSA 1024位公钥 , 可用来替换下方的pubKey");
			System.out.println(BytesHelper.toBase64(publicKey.getEncoded()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	static String priKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJDLSQs9l8h4X0yVVf39BH7SobkdichHiKbX1oLTYIfSz6ixLTaEeGi8OXVDFk89DCWOZIJEm4R9PLdR4VF-alWljXpu5Sx44ojywebwHrRN8hG-m_NuE-2Qibug8HFgv1b2Ny1NhX3uL2CuoyAO1DtLYlrshRd4cSB7SObj6xgrAgMBAAECgYBzQd57HCReZ7M0ewtgiq-xZGlnuaOb0ZVtehI_QZNtitnSJncfFoPimzIBrc1sdePdYWOQbpx88lQKeRsqi76IDBAsUfLWquPv6zIlLcMvSMdVb2fKSY41JreysR88DUzxyXA14DhFPZF5VrE8_50hrAgVdomf0kRtOkzxx82KgQJBAMUEh1fbWqPsZL8X4GWdMbZg_jK-Tav_3Z8WamgQKO5gxCq0Oz4de9D1tKVyPkSITX0E0ZNrk-66877FdsZ0ODMCQQC8JFR6Z2g9VcyBEgSABbSgU_XzwWmv5ktG2gI7QumT5APdhWIFcaONgkPYTWd_gASH2YtlbluiB9Va1FQFwYgpAkBtlQtCjrtnmcCfmUQywLA55ND-oGLLXFGEfw9IOlTJ4gC6T-zzq6qGm2OS8mw36ihPdTKscJWjn1Cbt3FNCK-JAkAmuguxjmuEhI6t2rmWaJY_kkjEPgG1NWA1W1auQB24VWz-QiBlInkZ_cBdW8F8GyvTktIAtPCjr50ZLy-EZ8vRAkBiOIAxowxVIRVei0r8_g3VD7EvOIlVQVINwRQc7tpj7n7PdyHTtyUAg6RGYzo1y2AiuqKVQ1CLwUge_MKh7VGr";

	static String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQy0kLPZfIeF9MlVX9_QR-0qG5HYnIR4im19aC02CH0s-osS02hHhovDl1QxZPPQwljmSCRJuEfTy3UeFRfmpVpY16buUseOKI8sHm8B60TfIRvpvzbhPtkIm7oPBxYL9W9jctTYV97i9grqMgDtQ7S2Ja7IUXeHEge0jm4-sYKwIDAQAB";

	private static byte[] getPubKey() {
		return BytesHelper.fromBase64(pubKey);
		// return keyPair.getPublic().getEncoded();
	}

	private static byte[] getPriKey() {
		return BytesHelper.fromBase64(priKey);
		// return keyPair.getPrivate().getEncoded();
	}
}
