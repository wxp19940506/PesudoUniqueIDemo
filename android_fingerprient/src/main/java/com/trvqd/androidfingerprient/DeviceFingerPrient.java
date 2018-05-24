package com.trvqd.androidfingerprient;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import java.security.MessageDigest;
import java.util.ArrayList;

/**
 * Created by XiaopengWang on 2018/5/23.
 * Email:xiaopeng.wang@qaii.ac.cn
 * QQ:839853185
 * WinXin;wxp19940505
 */

public class DeviceFingerPrient {
static String  deviceID;
//静态代码块获取系统配置信息
static{
    deviceID =
            Build.VERSION.CODENAME+","
                    + Build.VERSION.INCREMENTAL+","
                    + Build.VERSION.RELEASE+","
                    + Build.VERSION.SDK+","
                    + Build.VERSION.SDK_INT+",";
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
        if (Build.SUPPORTED_32_BIT_ABIS.length!=0){
            for (int i = 0; i <Build.SUPPORTED_32_BIT_ABIS.length ; i++) {
                deviceID += Build.SUPPORTED_32_BIT_ABIS[i] +",";
            }
        }else {
            deviceID += "unkonwn,";

        }
        if (Build.SUPPORTED_64_BIT_ABIS.length!=0){
            for (int i = 0; i <Build.SUPPORTED_64_BIT_ABIS.length ; i++) {
                deviceID += Build.SUPPORTED_64_BIT_ABIS[i] +",";
            }
        }else {
            deviceID += "unkonwn,";

        }

        if (Build.SUPPORTED_ABIS.length!=0){
            for (int i = 0; i <Build.SUPPORTED_ABIS.length ; i++) {
                deviceID += Build.SUPPORTED_ABIS[i] +",";
            }
        }else {
            deviceID += "unkonwn,";

        }

    }else {
        deviceID += "unkonwn,";
    }
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        deviceID +=  Build.VERSION.SECURITY_PATCH+",";
        deviceID +=  Build.VERSION.BASE_OS+",";
    }else {
        deviceID += "unkonwn,";
    }
    deviceID += Build.TIME+","
            + Build.SERIAL+","
            + Build.getRadioVersion()+","
            + Build.BOOTLOADER+","
            + Build.FINGERPRINT+","
            + Build.HARDWARE+","
            + Build.BOARD+","
            + Build.BRAND+","
            + Build.CPU_ABI+","
            + Build.CPU_ABI2+","
            + Build.DEVICE+","
            + Build.HOST+","
            + Build.ID+","
            + Build.MANUFACTURER+","
            + Build.MODEL+","
            + Build.PRODUCT+","
            + Build.TAGS+","
            + Build.TYPE+","
            + Build.USER+","
            + Build.DISPLAY+",";
}
    //使用默认配置生成UUID
    public static String getDeviceFingerPrint(Context context) {
        return getDeviceFingerPrint(context,"");
    }

    //使用默认配置并加入自定义字符串生成UUID
    public static String getDeviceFingerPrint(Context context,String custom) {
        String final_uuid = (String) SharedPreferencesUtils.getParam(context, "uuid", "empty");
        if (final_uuid.trim().equals("empty")) {
            String deviceID = DeviceFingerPrient.deviceID
                    +Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID)+","
                    +Settings.System.getString(context.getContentResolver(), Settings.System.TIME_12_24)+","
                    + custom;
            String md5_totle =  getMD5Code(deviceID);
            final_uuid =md5_totle.substring(0,8)+"-"+md5_totle.substring(8,12)+"-"+md5_totle.substring(12,16)+"-"+md5_totle.substring(16,20)+"-"+md5_totle.substring(20);
            SharedPreferencesUtils.setParam(context, "uuid", final_uuid);
        }

        return final_uuid;
    }

    //使用默认配置并加入自定义数组生成UUID
    public static String getDeviceFingerPrint(Context context,ArrayList<String> customList) {
            if (customList.size() !=0){
                for (int i = 0; i < customList.size(); i++) {
                    deviceID += customList.get(i);
                }
                return DeviceFingerPrient.getDeviceFingerPrint(context,deviceID);
            }else {
                return DeviceFingerPrient.getDeviceFingerPrint(context);
            }
    }

    private static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
    // md5加密
    private static String getMD5Code(String message) {
        String md5Str = "";
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES. O){
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] md5Bytes = md.digest(message.getBytes());
                md5Str = bytes2Hex(md5Bytes);
            }catch (Exception e) {
                md5Str =  MD5(message);
                e.printStackTrace();
            }
        }else {
            md5Str =  MD5(message);
        }


        return md5Str;
    }

    // 2进制转16进制
    private static String bytes2Hex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        int temp;
        try {
            for (int i = 0; i < bytes.length; i++) {
                temp = bytes[i];
                if(temp < 0) {
                    temp += 256;
                }
                if (temp < 16) {
                    result.append("0");
                }
                result.append(Integer.toHexString(temp));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
