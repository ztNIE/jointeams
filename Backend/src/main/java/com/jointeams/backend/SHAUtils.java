package com.jointeams.backend;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAUtils {

    /**
     * sha1加密
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String sha1(String data) throws NoSuchAlgorithmException {
//加Key
        data += SingletonKey.getKeyValue();
//信息摘要器 算法名称
        MessageDigest md = MessageDigest.getInstance("SHA1");
//把字符串转为字节数组
        byte[] b = data.getBytes();
//使用指定的字节来更新摘要
        md.update(b);
//获取密文 （完成摘要计算）
        byte[] b2 = md.digest();
//获取计算的长度
        int len = b2.length;
////16进制字符串
//        String str = "0123456789abcdef";
////把字符串转为字符串数组
//        char[] ch = str.toCharArray();
        char[] ch = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

//创建一个40位长度的字节数组
        char[] chs = new char[len*2];
//循环20次
        for(int i=0,k=0;i<len;i++) {
//获取摘要计算后的字节数组中的每个字节
            byte b3 = b2[i];
// >>>:无符号右移
// &:按位与
//0xf:0-15的数字
            chs[k++] = ch[b3 >>> 4 & 0xf];
            chs[k++] = ch[b3 & 0xf];
        }

//字符数组转为字符串
        return new String(chs);
    }
}
