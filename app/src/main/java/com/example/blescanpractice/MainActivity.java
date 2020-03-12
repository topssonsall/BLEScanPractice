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

    public static final int CHECK_ESCAPE_INTERVAL = 10200;
    public static final int CHECK_WARN_INTERVAL = 5100;

    public static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    public static final int REQUEST_ENABLE_BT = 1;
    public static final int BLE_RESCAN_INTERVAL = 5100;
    public static final int CHECK_REFRESH_INTERVAL = 200;
    public static final int PLAY_REFRESH_INTERVAL = 500;

    public static final int STATE_ACTIVITY_UNDEF = 0;
    public static final int STATE_ACTIVITY_SEARCH = 1;
    public static final int STATE_ACTIVITY_PAUSE = 2;
    public static final int STATE_ACTIVITY_PLAY = 3;
    public static final int STATE_ACTIVITY_FIND = 4;

    private TextView tv1, tv2, tv3, tv4, tv5;
    private Button btnScan, btnRealtimeScan, btnStopScan, btnStopService;
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
        //Beacon List (Beacon 정보 세팅 : 여기에 MAC 주소 같은거 있어야 될 듯.)
        //일단 예시로 5개라고 가정.
        beaconList = new ArrayList<>();
        Beacon beacon1 = new Beacon("uuid", "major", "minor", "AC:23:3F:A2:20:7A");
        Beacon beacon2 = new Beacon("uuid", "major", "minor", "AC:23:3F:A2:20:76");
        beaconList.add(beacon1);
        beaconList.add(beacon2);

        tv1 = findViewById(R.id.tv_1);
        tv2 = findViewById(R.id.tv_2);
        tv3 = findViewById(R.id.tv_3);
        tv4 = findViewById(R.id.tv_4);
        tv5 = findViewById(R.id.tv_5);
        btnScan = findViewById(R.id.btn_scan);
        btnRealtimeScan = findViewById(R.id.btn_realtime_scan);
        btnStopScan = findViewById(R.id.btn_stop_scan);
        btnStopService = findViewById(R.id.btn_stop_service);

        if (BeaconService.isScanning) {
            btnScan.setClickable(false);
            btnRealtimeScan.setClickable(false);
            btnScan.setText("스캔중..");
            btnRealtimeScan.setText("스캔중..");
        }
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

        //실시간 스캔 (지속성 스캔)
        btnRealtimeScan.setOnClickListener(new View.OnClickListener() {
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
        Log.d("BeaconTest", "start_service 진입");
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
        Log.d("BeaconTest", "누름 start_scan");
        BeaconService.scanLeDevice(true);
    }

    public void stop_scan() {
        Log.d("BeaconTest", "stop_scan");
        BeaconService.scanLeDevice(false);
    }
}
