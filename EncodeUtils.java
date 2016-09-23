/**
 * 
 */
package springlol.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类。可以实现MD5、SHA、SHA256、SHA512的加密
 * 
 * @author springlol
 * 
 */
public class EncodeUtils {
    /**
     * 将摘要信息转换为相应的编码
     * 
     * @param code 编码类型
     * @param message 摘要信息
     * @return 相应的编码字符串
     */
    private static String Encode(String code, String message) {
        MessageDigest md;
        String encode = null;
        try {
            md = MessageDigest.getInstance(code);
            encode = byteArrayToHexString(md.digest(message.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encode;
    }

    /**
     * 字节数组转16进制字符串
     * 
     * @param b
     * @return 转换后的16进制字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultStr = new StringBuffer();
        String hex;
        for (int i = 0; i < b.length; i++) {
            hex = Integer.toHexString(b[i] & 0xFF);
            if(hex.length() == 1) {
                hex = '0' + hex;
            }
            resultStr.append(hex);
        }
        return resultStr.toString();
    }

    /**
     * 将摘要信息转换成MD5编码
     * 
     * @param message 摘要信息
     * @return MD5编码之后的字符串
     */
    public static String md5Encode(String message) {
        return Encode("MD5", message);
    }

    /**
     * 将摘要信息转换成SHA编码
     * 
     * @param message 摘要信息
     * @return SHA编码之后的字符串
     */
    public static String shaEncode(String message) {
        return Encode("SHA", message);
    }

    /**
     * 将摘要信息转换成SHA-256编码
     * 
     * @param message 摘要信息
     * @return SHA-256编码之后的字符串
     */
    public static String sha256Encode(String message) {
        return Encode("SHA-256", message);
    }

    /**
     * 将摘要信息转换成SHA-512编码
     * 
     * @param message 摘要信息
     * @return SHA-512编码之后的字符串
     */
    public static String sha512Encode(String message) {
        return Encode("SHA-512", message);
    }
}
