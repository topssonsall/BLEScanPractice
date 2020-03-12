package com.example.blescanpractice;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BeaconService extends Service {

    //블루투쓰 관련 변수
    public BluetoothAdapter bluetoothAdapter;
    public static BluetoothLeScanner scanner;
    public static ScanSettings scanSettings;
    public static List<ScanFilter> scanFilterList;
    public static ScanCallback scanCallback;
    public BluetoothDevice bluetoothDevice;

    //그 외 변수
    public static boolean isScanning;

    //서비스 최초 실행
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("BeaconTest", "Service onCreate");
        init();
        setFunction();
    }

    //서비스 재실행
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    //서비스 종료
    @Override
    public void onDestroy() {
        super.onDestroy();
        scanLeDevice(false);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void init(){

        //그 외 값 초기화
        isScanning = false;

        //블루투쓰 및 스캐너 세팅
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        if(bluetoothAdapter == null) return;
        scanner = bluetoothAdapter.getBluetoothLeScanner();

        Log.d("BeaconTest", bluetoothAdapter.isEnabled()+"");

        scanFilterList = new ArrayList<>();

        //Scan 모드 세팅 (저전력 모드)
        scanSettings = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).setReportDelay(0).build();
    }

    public void setFunction(){
        scanCallback = new ScanCallback() {
            //Scan 성공시
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                Log.d("BeaconTest", "onScanResult");
                bluetoothDevice = result.getDevice();
                ScanRecord scanRecord = result.getScanRecord();
                String strAddress = bluetoothDevice.getAddress();
                String deviceNameToken[] = strAddress.split(":");

                int beaconListSize = MainActivity.beaconList.size();
                for(int i = 0 ; i < beaconListSize; i++){
                    Beacon beacon = MainActivity.beaconList.get(i);
                    Log.d("BeaconTest", "strAddress >>" + strAddress);
                    if(beacon.getMacAddress().equals(strAddress)){
                        //특정할 수 있는 Beacon 발견
                        Log.d("BeaconTest", strAddress + " >> 찾음");
                    }
                }
            }

            //Scan 실패시
            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
                Log.d("BeaconTest", "onScanFailed");
            }
        };
    }

    public static void scanLeDevice(final boolean enable){
        Log.d("BeaconTest", "scanLeDevice111");
        if(enable){
            Log.d("BeaconTest", "scanLeDevice222");
            if(scanner != null){
                Log.d("BeaconTest", "scanLeDevice333");
                if(isScanning){
                    scanner.stopScan(scanCallback);
                }
            scanFilterList.clear();                                           //AC:23:3F:A2:20:76
            ScanFilter scanFilter = new ScanFilter.Builder().setDeviceAddress("AC:23:3F:A2:20:76").build();
            scanFilterList.add(scanFilter);
            scanner.startScan(scanFilterList, scanSettings,scanCallback);
            isScanning = true;
                Log.d("BeaconTest", "scanLeDevice 진입");
            }
        }else{
            if(scanner != null){
                if(isScanning){
                    scanner.stopScan(scanCallback);
                    isScanning = false;
                }
            }
        }
    }

    public static void rescanLeDevice(){
        if(scanner != null){
            if(isScanning){
                scanner.stopScan(scanCallback);
                isScanning = false;
            }
        }

        if(scanner != null){
            scanner.startScan(scanFilterList, scanSettings, scanCallback);
            isScanning = true;
        }
    }
}
