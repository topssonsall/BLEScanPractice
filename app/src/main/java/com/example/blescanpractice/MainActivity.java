package com.example.blescanpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_ENABLE_BT = 1;

    private Button btnScan, btnStopScan, btnStopService;

    //Beacon List (미리 Beacon들의 정보를 세팅하고 담을 리스트)
    public static List<Beacon> beaconList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setFunction();
    }

    public void init() {

        //위치권한 요청
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        //내가 미리 세팅하는 Beacon 정보
        beaconList = new ArrayList<>();
        Beacon beacon1 = new Beacon("uuid", "major", "minor", "AC:23:3F:A2:20:7A");
        Beacon beacon2 = new Beacon("uuid", "major", "minor", "AC:23:3F:A2:20:76");
        beaconList.add(beacon1);
        beaconList.add(beacon2);

        btnScan = findViewById(R.id.btn_scan);
        btnStopScan = findViewById(R.id.btn_stop_scan);
        btnStopService = findViewById(R.id.btn_stop_service);

//        if (BeaconService.isScanning) {
//            btnScan.setClickable(false);
//            btnRealtimeScan.setClickable(false);
//            btnScan.setText("스캔중..");
//            btnRealtimeScan.setText("스캔중..");
//        }
        start_service();
    }

    public void setFunction() {
        //1회성 스캔
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_scan();
            }
        });

        //스캔 중지
        btnStopScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop_scan();
            }
        });

        //서비스 중지
        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop_service();
            }
        });
    }

    public void start_service() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        Intent intent = new Intent(getApplicationContext(), BeaconService.class);
        startService(intent);
    }

    public void stop_service() {
        Intent intent = new Intent(getApplicationContext(), BeaconService.class);
        stopService(intent);
    }

    public void start_scan() {
        BeaconService.scanLeDevice(true);
    }

    public void stop_scan() {
        BeaconService.scanLeDevice(false);
    }
}
