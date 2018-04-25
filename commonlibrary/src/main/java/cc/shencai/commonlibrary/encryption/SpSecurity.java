package cc.shencai.commonlibrary.encryption;


import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 为sp准备的加密算法
 * Created by yss on 2017/8/15
 *
 * @version 1.0.0
 */
public class SpSecurity {

	private final static String encoding = "UTF-8";

	/**
	 * 加密字符串
	 */
	public static String Encrypto(String str) {
		String result = str;
		if (str != null && str.length() > 0) {
			try {
				byte[] encodeByte = symmetricEncrypto(str.getBytes(encoding));
				result = Base64.encodeBytes(encodeByte);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 解密字符串
	 */
	public static String Decrypto(String str) {
		String result = str;
		if (str != null && str.length() > 0) {
			try {
				byte[] encodeByte = Base64.decode(str);
				byte[] decoder = symmetricDecrypto(encodeByte);
				result = new String(decoder, encoding);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 对称加密方法
	 *
	 * @param byteSource
	 *            需要加密的数据
	 * @return 经过加密的数据
	 * @throws Exception
	 */
	public static byte[] symmetricEncrypto(byte[] byteSource) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			int mode = Cipher.ENCRYPT_MODE;
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			byte[] keyData = {  9, 8, 2, 5, 8, 2, 1 ,9};
			DESKeySpec keySpec = new DESKeySpec(keyData);
			Key key = keyFactory.generateSecret(keySpec);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(mode, key);
			byte[] result = cipher.doFinal(byteSource);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			baos.close();
		}
	}

	/**
	 * 对称解密方法
	 *
	 * @param byteSource
	 *            需要解密的数据
	 * @return 经过解密的数据
	 * @throws Exception
	 */
	public static byte[] symmetricDecrypto(byte[] byteSource) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			int mode = Cipher.DECRYPT_MODE;
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			byte[] keyData = { 9, 8, 2, 5, 8, 2, 1,9 };
			DESKeySpec keySpec = new DESKeySpec(keyData);
			Key key = keyFactory.generateSecret(keySpec);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(mode, key);
			byte[] result = cipher.doFinal(byteSource);
			// int blockSize = cipher.getBlockSize();
			// int position = 0;
			// int length = byteSource.length;
			// boolean more = true;
			// while (more) {
			// if (position + blockSize <= length) {
			// baos.write(cipher.update(byteSource, position, blockSize));
			// position += blockSize;
			// } else {
			// more = false;
			// }
			// }
			// if (position < length) {
			// baos.write(cipher.doFinal(byteSource, position, length
			// - position));
			// } else {
			// baos.write(cipher.doFinal());
			// }
			// return baos.toByteArray();
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			baos.close();
		}
	}

	/**
	 * 散列算法
	 *
	 * @param byteSource
	 *            需要散列计算的数据
	 * @return 经过散列计算的数据
	 * @throws Exception
	 */
	public static byte[] hashMethod(byte[] byteSource) throws Exception {
		try {
			MessageDigest currentAlgorithm = MessageDigest.getInstance("SHA-1");
			currentAlgorithm.reset();
			currentAlgorithm.update(byteSource);
			return currentAlgorithm.digest();
		} catch (Exception e) {
			throw e;
		}
	}
}
