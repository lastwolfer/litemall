package com.pandax.litemall.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {


    public static String getMd5(String content){
        //先获得正文的字节
        byte[] bytes = content.getBytes();
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            //将正文的字节转换成md5的字节
            StringBuffer sb = new StringBuffer();
            byte[] digestBytes = md5.digest(bytes);
            for (byte digestByte : digestBytes) {
                int i = digestByte & 0xff;

                String s = Integer.toHexString(i);
                if (s.length() == 1){
                    sb.append(0);
                }
                sb.append(s);
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return "";
    }
    //sault盐
    public static String getMd5(String content,String sault){
        //实现了加盐
        content = content + "{!"+ sault + "abc";
        byte[] bytes = content.getBytes();
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            //将正文的字节转换成md5的字节
            StringBuffer sb = new StringBuffer();
            byte[] digestBytes = md5.digest(bytes);
            for (byte digestByte : digestBytes) {
                int i = digestByte & 0xff;

                String s = Integer.toHexString(i);
                if (s.length() == 1){
                    sb.append(0);
                }
                sb.append(s);
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return "";
    }

    public static String getMultiMd5(String content){

        return getMd5(getMd5(getMd5(content)));
    }
    public static String getFileMd5(File file){
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int length = 0;
            MessageDigest md5 = MessageDigest.getInstance("md5");

            while ((length = fileInputStream.read(bytes,0,1024)) > 0){
                md5.update(bytes,0,length);
            }
            byte[] digestBytes = md5.digest();

            StringBuffer sb = new StringBuffer();
            for (byte digestByte : digestBytes) {
                int i = digestByte & 0xff;

                String s = Integer.toHexString(i);
                if (s.length() == 1){
                    sb.append(0);
                }
                sb.append(s);
            }
            return sb.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
