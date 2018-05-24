package com.trvqd.androidfingerprient;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.security.MessageDigest;
import java.util.ArrayList;

/**
 * Created by XiaopengWang on 2018/5/23.
 * Email:xiaopeng.wang@qaii.ac.cn
 * QQ:839853185
 * WinXin;wxp19940505
 */

public class DeviceFingerPrient {
    public static String init(){
        String deviceID =
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
        return deviceID;

    }
    public static String getDeviceFingerPrint(Context context) {

        String final_uuid = (String) SharedPreferencesUtils.getParam(context, "uuid", "empty");
        Log.e("TAG","final_uuid："+final_uuid);

        if (final_uuid.trim().equals("empty")){
            Log.e("TAG","写进去了");
            String deviceID = init()+Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
            String md5_totle =  getMD5Code(deviceID);
            final_uuid = md5_totle.substring(0,8)+"-"+md5_totle.substring(8,12)+"-"+md5_totle.substring(12,16)+"-"+md5_totle.substring(16,20)+"-"+md5_totle.substring(20);
            SharedPreferencesUtils.setParam(context, "uuid", final_uuid);
        }


        return final_uuid;
    }

    public static String getDeviceFingerPrint(Context context,String custom) {
        String final_uuid = (String) SharedPreferencesUtils.getParam(context, "uuid", "empty");
        if (final_uuid.trim().equals("empty")) {
            String deviceID = init()
                    +Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID)+","
                    + custom;
            String md5_totle =  getMD5Code(deviceID);
            final_uuid =md5_totle.substring(0,8)+"-"+md5_totle.substring(8,12)+"-"+md5_totle.substring(12,16)+"-"+md5_totle.substring(16,20)+"-"+md5_totle.substring(20);
            SharedPreferencesUtils.setParam(context, "uuid", final_uuid);
        }

        return final_uuid;
    }
    public static String getDeviceFingerPrint(Context context,ArrayList customList) {
        String final_uuid = (String) SharedPreferencesUtils.getParam(context, "uuid", "empty");
        if (final_uuid.trim().equals("empty")) {
            String deviceID = init()
                    +Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID)+","
                    ;
            if (customList.size() !=0){
                for (int i = 0; i < customList.size(); i++) {
                    deviceID += customList.get(i);
                }
            }
            String md5_totle =  getMD5Code(deviceID);
            final_uuid = md5_totle.substring(0,8)+"-"+md5_totle.substring(8,12)+"-"+md5_totle.substring(12,16)+"-"+md5_totle.substring(16,20)+"-"+md5_totle.substring(20);
            SharedPreferencesUtils.setParam(context, "uuid", final_uuid);
        }


        return final_uuid;
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
//    String m_szDevIDShort = "" + //we make this look like a valid IMEI
//            "VERSION_BASE_OS:"+ Build.VERSION.BASE_OS+",\n"+
//            //发布版本或开发版本
////                "VERSION_CODENAME:"+Build.VERSION.CODENAME+",\n"+
//            //The internal value used by the underlying source control to represent this build.
//            "VERSION_INCREMENTAL:"+Build.VERSION.INCREMENTAL+",\n"+
//            //操作系统版本
//            "VERSION_RELEASE:"+Build.VERSION.RELEASE+",\n"+
//            //用户可见安全补丁的级别
//            "VERSION_SECURITY_PATCH:"+Build.VERSION.SECURITY_PATCH+",\n"+
//            //SDK版本
//            "VERSION_SDK:"+Build.VERSION.SDK+",\n"+
//            //预览版本
//            "VERSION_PREVIEW_SDK_INT:"+Build.VERSION.PREVIEW_SDK_INT+",\n"+
//            "VERSION_SDK_INT:"+Build.VERSION.SDK_INT+",\n"+
//            "RADIO:"+Build.RADIO+",\n"+
//            //此设备支持32位ABIs的有序列表。
//            "SUPPORTED_32_BIT_ABIS:"+Build.SUPPORTED_32_BIT_ABIS.toString()+",\n"+
//            ////此设备支持64位ABIs的有序列表。
//            "SUPPORTED_64_BIT_ABIS:"+Build.SUPPORTED_64_BIT_ABIS.toString()+",\n"+
//            //TIME 固件版本推出的日期
//            "TIME:"+Build.TIME+",\n"+
//            //硬件序列号 Either a changelist number, or a label like "M4-rc20".
//            //一个硬件序列号，如果可用。
//
//            "SERIAL:"+Build.SERIAL+",\n"+
//            "SUPPORTED_ABIS:"+Build.SUPPORTED_ABIS+",\n"+
//
////                "getSerial:"+Build.getSerial()+",\n"+
//            "getRadioVersion:"+Build.getRadioVersion()+",\n"+
//            //系统启动程序版本号
//            "BOOTLOADER:"+Build.BOOTLOADER+",\n"+
//            //硬件识别码 A string that uniquely identifies this build.
//
//            "FINGERPRINT:"+Build.FINGERPRINT+",\n"+
//            //硬件名称 硬件名称(来自内核命令行或/proc)。
//            "HARDWARE:"+Build.HARDWARE+",\n"+
//            //主板
//            "BOARD:"+Build.BOARD +",\n"+
//            //系统定制商
//            "BRAND:"+Build.BRAND +",\n"+
//            //cpu指令集
//            "CPU_ABI:"+Build.CPU_ABI +",\n"+
//            //cpu指令集2
//            "CPU_ABI2:"+Build.CPU_ABI2+",\n"+
//            //设置参数
//            "DEVICE:"+Build.DEVICE+",\n"+
//            //显示屏参数
//            "DISPLAY:"+Build.DISPLAY +",\n"+
//            //HOST
//            "HOST:"+Build.HOST +",\n"+
//            //修订版本列表
//            "ID:"+Build.ID +",\n"+
//            //产品/硬件的制造商。
//            "MANUFACTURER:"+Build.MANUFACTURER+",\n"+
//            //版本  The end-user-visible name for the end product.
//
//            "MODEL:"+Build.MODEL +",\n"+
//            //手机制造商  产品的名称
//            "PRODUCT:"+Build.PRODUCT +",\n"+
//            //描述Build的标签
//            "TAGS:"+Build.TAGS +",\n"+
//            //描述Build的标签
//            "TYPE:"+Build.TYPE +",\n"+
//            "USER:"+Build.USER+",\n"+
//            "ANDROID_ID:"+ Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID); //13 digits