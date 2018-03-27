package safe;

/**
 * Created by teacher on 2016/5/13.
 */
public class SafetyUtil {
    public static String encrypt(SafetyAlgorithm algorithm, String plainText, byte[] key) {
        try {
            byte[] encrypt = algorithm.encrypt(BytesHelper.toBytes(plainText), key);
            return BytesHelper.toBase64(encrypt);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加密错误");
        }
    }

    public static String encrypt(SafetyAlgorithm algorithm, byte[] plainText, byte[] key) {
        try {
            byte[] encrypt = algorithm.encrypt(plainText, key);
            return BytesHelper.toBase64(encrypt);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加密错误");
        }
    }

    public static byte[] encryptBytes(SafetyAlgorithm algorithm, String plainText, byte[] key) {
        try {
            byte[] encrypt = algorithm.encrypt(BytesHelper.toBytes(plainText), key);
            return encrypt;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加密错误");
        }
    }

    public static byte[] encryptBytes(SafetyAlgorithm algorithm, byte[] plainText, byte[] key) {
        try {
            byte[] encrypt = algorithm.encrypt(plainText, key);
            return encrypt;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加密错误");
        }
    }
    public static String decrypt(SafetyAlgorithm algorithm, String decryptText, byte[] key) {
        try {
            byte[] decrypt = algorithm.decrypt(BytesHelper.fromBase64(decryptText), key);
            return BytesHelper.fromBytes(decrypt);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解密错误");
        }
    }


    public static String decrypt(SafetyAlgorithm algorithm, byte[] decryptText, byte[] key) {
        try {
            byte[] decrypt = algorithm.decrypt(decryptText, key);
            return BytesHelper.fromBytes(decrypt);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解密错误");
        }
    }

    public static byte[] decryptBytes(SafetyAlgorithm algorithm, byte[] decryptText, byte[] key) {
        try {
            byte[] decrypt = algorithm.decrypt(decryptText, key);
            return decrypt;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解密错误");
        }
    }

    public static byte[] decryptBytes(SafetyAlgorithm algorithm, String decryptText, byte[] key) {
        try {
            byte[] decrypt = algorithm.decrypt(BytesHelper.fromBase64(decryptText), key);
            return decrypt;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解密错误");
        }
    }

    //私钥不能明文保存到apk
    //private static String priKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJDLSQs9l8h4X0yVVf39BH7SobkdichHiKbX1oLTYIfSz6ixLTaEeGi8OXVDFk89DCWOZIJEm4R9PLdR4VF-alWljXpu5Sx44ojywebwHrRN8hG-m_NuE-2Qibug8HFgv1b2Ny1NhX3uL2CuoyAO1DtLYlrshRd4cSB7SObj6xgrAgMBAAECgYBzQd57HCReZ7M0ewtgiq-xZGlnuaOb0ZVtehI_QZNtitnSJncfFoPimzIBrc1sdePdYWOQbpx88lQKeRsqi76IDBAsUfLWquPv6zIlLcMvSMdVb2fKSY41JreysR88DUzxyXA14DhFPZF5VrE8_50hrAgVdomf0kRtOkzxx82KgQJBAMUEh1fbWqPsZL8X4GWdMbZg_jK-Tav_3Z8WamgQKO5gxCq0Oz4de9D1tKVyPkSITX0E0ZNrk-66877FdsZ0ODMCQQC8JFR6Z2g9VcyBEgSABbSgU_XzwWmv5ktG2gI7QumT5APdhWIFcaONgkPYTWd_gASH2YtlbluiB9Va1FQFwYgpAkBtlQtCjrtnmcCfmUQywLA55ND-oGLLXFGEfw9IOlTJ4gC6T-zzq6qGm2OS8mw36ihPdTKscJWjn1Cbt3FNCK-JAkAmuguxjmuEhI6t2rmWaJY_kkjEPgG1NWA1W1auQB24VWz-QiBlInkZ_cBdW8F8GyvTktIAtPCjr50ZLy-EZ8vRAkBiOIAxowxVIRVei0r8_g3VD7EvOIlVQVINwRQc7tpj7n7PdyHTtyUAg6RGYzo1y2AiuqKVQ1CLwUge_MKh7VGr";

    private static String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQy0kLPZfIeF9MlVX9_QR-0qG5HYnIR4im19aC02CH0s-osS02hHhovDl1QxZPPQwljmSCRJuEfTy3UeFRfmpVpY16buUseOKI8sHm8B60TfIRvpvzbhPtkIm7oPBxYL9W9jctTYV97i9grqMgDtQ7S2Ja7IUXeHEge0jm4-sYKwIDAQAB";

    // 客户端专用，服务端无需次方法
    public static byte[] getPubKey() {
        return BytesHelper.fromBase64(pubKey);
    }

    // 服务端专用，客户端不应该知道私钥
    public static byte[] getPriKey() {
        return /*BytesHelper.fromBase64(priKey);*/null;
    }
}
