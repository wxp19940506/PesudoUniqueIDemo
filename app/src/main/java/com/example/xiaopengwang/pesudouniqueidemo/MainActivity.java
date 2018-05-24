package com.example.xiaopengwang.pesudouniqueidemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.trvqd.androidfingerprient.DeviceFingerPrient;

public class MainActivity extends AppCompatActivity {

    private TextView id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = findViewById(R.id.text);
        id.setText(DeviceFingerPrient.getDeviceFingerPrint(this));
    }


}
