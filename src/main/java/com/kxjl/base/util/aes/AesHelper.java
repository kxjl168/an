package com.kxjl.base.util.aes;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.kxjl.base.util.PropertiesUtil;

public class AesHelper {

	public static String aesPass = PropertiesUtil.getProperty("aes.aesPass");

	/**
	 * AES加密字符串 ,返回密文转16进制
	 * 
	 * @param content
	 * @param password
	 * @return
	 * @author zj
	 * @date 2019年3月18日
	 */
	public static String encrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者

			
			//linux，window处理一致
			SecureRandom secureRandom =  SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password.getBytes());

			
			
			kgen.init(128,secureRandom);// 利用用户密码作为随机数初始化出
																	// 128位的key生产者
			// 加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行

			SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥

			byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回
															// null。

			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥

			Cipher cipher = Cipher.getInstance("AES");// 创建密码器

			byte[] byteContent = content.getBytes("utf-8");

			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化为加密模式的密码器

			byte[] result = cipher.doFinal(byteContent);// 加密

			String rstStr = parseByte2HexStr(result);

			return rstStr;

		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 对 AES加密过并转为16进制的字符串 解密
	 * 
	 * @param content
	 *            AES加密过过的内容
	 * @param password
	 *            加密时的密码
	 * @return 明文
	 */
	public static String decrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
			
			//linux，window处理一致
			SecureRandom secureRandom =  SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password.getBytes());

			
			
			kgen.init(128,secureRandom);// 利用用户密码作为随机数初始化出
			SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
			byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化为解密模式的密码器

			byte[] aesData = parseHexStr2Byte(content);

			byte[] result = cipher.doFinal(aesData);

			String rst = new String(result, "utf-8");

			return rst; // 明文

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public static void main(String[] args) {
		String input = "{\r\n" + "	\"aaa\": \"1\",\r\n" + "	\"aaa2\": \"1\",\r\n" + "	\"aaa3\": \"1\",\r\n"
				+ "	\"aaa4\": \"1\"\r\n" + "}";

		String password = PropertiesUtil.getProperty("aes.aesPass");

		String data = AesHelper.encrypt(input, password);
		System.out.println(data);

		String pri = AesHelper.decrypt(data, password);
		System.out.println(pri);
	}
}
