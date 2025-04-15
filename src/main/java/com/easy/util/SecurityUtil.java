package com.easy.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class SecurityUtil {
	//将对象序列化为字符串，以方便传输和储存
	public static String obj2str(Object obj){
		String objBody = null;
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try{
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			byte[] bytes = baos.toByteArray();
			objBody = new String(bytes);
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if(oos!=null) oos.close();
				if(baos!=null) baos.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return objBody;
	}
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T getObjFromStr(String objBody, Class<T> clazz){
		byte[] bytes=objBody.getBytes();
		ObjectInputStream ois = null;
		T obj = null;
		try{
		   ois=new ObjectInputStream(new ByteArrayInputStream(bytes));
		   obj = (T)ois.readObject();
		}catch(Exception e){
			e.printStackTrace();
		} finally{
			try{
				if(ois!=null) ois.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return obj;
	}
	//MD5 32位的加密
	public static String md5(String plainText){
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
		md.update(plainText.getBytes());
		byte b[] = md.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}
	
	//sql注入关键字检测
	public static boolean sqlCheck (String param){
		if(param==null||!param.isEmpty())
			return false;
		String[] keys = {"select ",";","update ","union ","drop ","delete ","create ",
				"insert ","exec ","systemcolumn ","alter ","</script>"}; 
		for (String k : keys){
			if(param.toLowerCase().indexOf(k)!=-1){
				return true;
			}
		}
		return false;
	}
	
	public static String sqlFilter(String param){
		return (sqlCheck(param))?"":param;
	}

	// 生成随机密码
	public static String getPass(int passLenth) {
		StringBuffer buffer = new StringBuffer(
				"0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*");
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int range = buffer.length();
		for (int i = 0; i < passLenth; i++) {
			// 生成指定范围类的随机数0—字符串长度(包括0、不包括字符串长度)
			sb.append(buffer.charAt(r.nextInt(range)));
		}
		return sb.toString();
	}

	/**
	 * 创建密匙
	 * 
	 * @param algorithm
	 *            加密算法,可用 DES,DESede,Blowfish
	 * @return SecretKey 秘密（对称）密钥
	 * @throws NoSuchAlgorithmException 
	 */
	public static SecretKey createDESSecretKey( byte[] key,String algorithm)
			throws NoSuchAlgorithmException {
		//防止linux下 随机生成key
	    SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
	    secureRandom.setSeed(key);
		// 返回生成指定算法的秘密密钥的 KeyGenerator 对象
		KeyGenerator keygen = KeyGenerator.getInstance(algorithm);
		keygen.init(secureRandom);
		// 生成一个密钥
		return keygen.generateKey();
	}
	
	

	/**
	 * Description 根据键值进行加密
	 * @param data 
	 * @param key  加密键byte数组
	 * @return
	 * @throws Exception
	 */
	public static String encryptToDES(String key, String data) throws Exception {
		byte[] bt;
		bt = encrypt(data.getBytes("UTF-8"), key.getBytes("UTF-8"));
		String strs = Base64.encodeBase64String(bt);
		String ecode = strs.replaceAll("/", "A019z").replaceAll("\\+", "A018z");
		return ecode;
	}

	/**
	 * Description 根据键值进行解密
	 * @param data
	 * @param key  加密键byte数组
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String decryptByDES(String key, String data) throws Exception {
		if (data == null)
			return null;
		String rstr = data.replaceAll("A019z", "/").replaceAll("A018z", "+");
		byte[] buf = Base64.decodeBase64(rstr);
		byte[] bt = decrypt(buf,key.getBytes("UTF-8"));
		return new String(bt);
	}

	/**
	 * Description 根据键值进行加密
	 * @param data
	 * @param key  加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		SecretKey securekey = createDESSecretKey(key,"DES");
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance("DES");

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}
	
	
	/**
	 * Description 根据键值进行解密
	 * @param data
	 * @param key  加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		SecretKey securekey = createDESSecretKey(key,"DES");

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}
	
	public static void main(String[] args) throws Exception {
		String str="http://pay.dgymk679.com";
		String fmt = new String(Base64.encodeBase64(str.getBytes())); //obj2str(bean);
		System.out.println("fmt="+fmt);
		String fmt2 = new String(Base64.decodeBase64(fmt));
		System.out.println("fmt2="+fmt2);
	}

}
