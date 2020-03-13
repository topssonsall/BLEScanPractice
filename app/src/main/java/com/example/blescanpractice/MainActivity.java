package com.example.blescanpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_ENABLE_BT = 1;

    private RecyclerView rvBeacon;
    private LinearLayoutManager linearLayoutManager;
    public static RecyclerViewBeaconListAdapter adapter;

    private Button btnScan, btnStopScan, btnStopService;

    //Beacon List (미리 Beacon들의 정보를 세팅하고 담을 리스트)
    public static List<Beacon> beaconList;

    //찾은 Beacon List
    public static List<Beacon> findedBeaconList;

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

        findedBeaconList = new ArrayList<>();

        //내가 미리 세팅하는 Beacon 정보
        beaconList = new ArrayList<>();
        Beacon beacon1 = new Beacon("uuid", "major", "minor", "AC:23:3F:A2:20:7A", -1.0, false, -1L);
        Beacon beacon2 = new Beacon("uuid", "major", "minor", "AC:23:3F:A2:20:76", -1.0, false, -1L);
        beaconList.add(beacon1);
        beaconList.add(beacon2);

        //Beacon List Recycler View 설정
        rvBeacon = findViewById(R.id.rv_beacon_list);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvBeacon.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewBeaconListAdapter();
        adapter.setBeaconList(findedBeaconList);
        rvBeacon.setAdapter(adapter);

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

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted())
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() // start actions in UI thread
                        {
                            @Override
                            public void run() {
                                for (int i = 0; i < findedBeaconList.size(); i++) {
                                    if ((System.currentTimeMillis() - findedBeaconList.get(i).getFindedTime()) / 1000 > 5) {
                                        for (Beacon beacon : beaconList) {
                                            beacon.setFinded(false);
                                            beacon.setFindedTime(-1L);
                                        }
                                        findedBeaconList.remove(i);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } catch (InterruptedException e) {
                        Log.d("BeaconTest", e.toString());
                    }
            }
        }).start();
    }

    public void setFunction() {
        //스캔
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Beacon beacon : beaconList) {
                    beacon.setFinded(false);
                }
                findedBeaconList.clear();
                adapter.notifyDataSetChanged();
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
