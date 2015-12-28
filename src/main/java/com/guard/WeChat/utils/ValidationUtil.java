package com.guard.WeChat.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 微信请求校验校验工具类
 * @author guard
 * @version 2015年12月25日14:01:13
 */
public class ValidationUtil {
   
   //令牌信息，与接口配置信息中的Token要一致
   private static String token = "guardHH";
   
   public static boolean checkSignature(String signature,String timestamp,String nonce){
	   String[] arr = new String[]{token,timestamp,nonce};
	   // 将token、timestamp、nonce三个参数进行字典序排序
	   Arrays.sort(arr);
	   StringBuilder content = new StringBuilder();
	   for (int i = 0; i < arr.length; i++) {
           content.append(arr[i]);
       }
	   MessageDigest md = null;
       String tmpStr = null;
       
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
       // 将三个参数字符串拼接成一个字符串进行sha1加密
       byte[] digest = md.digest(content.toString().getBytes());
       tmpStr = byteToStr(digest);
       
       content = null;
       System.out.println("tmpStr"+tmpStr);
       // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
       return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
   }  
   
   /**
    * 将字节数组转换为十六进制字符串
    * @author guard
    * @version 2015年12月25日16:19:02
    * @param byteArray
    * @return
    */
   private static String byteToStr(byte[] byteArray) {
       String strDigest = "";
       for (int i = 0; i < byteArray.length; i++) {
           strDigest += byteToHexStr(byteArray[i]);
       }
       return strDigest;
   }
   
   /**
    * 将字节转换为十六进制字符串
    * @author guard
    * @version 2015年12月25日16:19:29
    * @param mByte
    * @return
    */
   private static String byteToHexStr(byte mByte) {
       char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
               'B', 'C', 'D', 'E', 'F' };
       char[] tempArr = new char[2];
       tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
       tempArr[1] = Digit[mByte & 0X0F];

       String s = new String(tempArr);
       return s;
   }
   
   /**
    * 字典序排序算法
    * @author guard
    * @version 2015年12月25日16:24:08
    * @param a
    */
   public static void sort(String a[]) {
       for (int i = 0; i < a.length - 1; i++) {
           for (int j = i + 1; j < a.length; j++) {
               if (a[j].compareTo(a[i]) < 0) {
                   String temp = a[i];
                   a[i] = a[j];
                   a[j] = temp;
               }
           }
       }
   }
   
}
