package com.example.xiaopengwang.pesudouniqueidemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.trvqd.androidfingerprient.DeviceFingerPrient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView id1,id2,id3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id1 = findViewById(R.id.text1);
        id2 = findViewById(R.id.text2);
        id3 = findViewById(R.id.text3);
        //使用默认选项
        id1.setText(DeviceFingerPrient.getDeviceFingerPrint(this));
        //使用默认选项加自定义字符串
        id2.setText(DeviceFingerPrient.getDeviceFingerPrint(this,"my name is xiaopeng"));
        //使用默认选项加自定义数组
        ArrayList<String> list = new ArrayList<>();
        list.add("111");
        list.add("222");
        list.add("333");
        id3.setText(DeviceFingerPrient.getDeviceFingerPrint(this,list));
    }


}
