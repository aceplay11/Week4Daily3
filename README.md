# Week4Daily3

package com.example.week4day3;

1. Create an application to use 5 systems broadcasts
2. Create a foreground service with notification. Clicking on the notification will stop the foreground music. 
3. Create an IntentService to create a list of random objects (The objects should have atleast 4 fields including an image). Populate the recyclerView in the same activity which starts the intent service. Pass the data using a broadcast receiver.
4. Use the AlarmManager to send a notification after 5 secs on clicking each list item. The notification should have the object that was clicked on


import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.nfc.NfcAdapter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.TextView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastRecievers extends AppCompatActivity {
    TextView tvBattery;
    TextView tvPowerSave;
    TextView tvBluetooth;
    TextView tvNFC;
    TextView tvSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_recievers);
        tvNFC = findViewById(R.id.tvNFC);
        tvBattery = findViewById(R.id.tvBattery);
        tvPowerSave = findViewById(R.id.tvLocation);
        tvSound = findViewById(R.id.tvSound);
        tvBluetooth = findViewById(R.id.tvBluetooth);
        registerReceiver(broadcastReceiverBluetooth, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        registerReceiver(broadcastReceiverNFC, new IntentFilter(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED));
        registerReceiver(broadcastReceiverSound, new IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION));
        registerReceiver(broadcastReceiverBattery, new IntentFilter(BatteryManager.ACTION_CHARGING));
        registerReceiver(broadcastReceiverPowerSaveMode, new IntentFilter(PowerManager.ACTION_POWER_SAVE_MODE_CHANGED));
    }


    private final BroadcastReceiver broadcastReceiverBluetooth = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String actionBluetooth = intent.getAction();

            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(actionBluetooth)) {
                if (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 10) == BluetoothAdapter.STATE_OFF) {
                    tvBluetooth.setText(actionBluetooth + ": Bluetooth is off");
                } else if (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 12) == BluetoothAdapter.STATE_ON) {
                    tvBluetooth.setText(actionBluetooth + ": Bluetooth is on");

                }
            }
        }

    };
    private final BroadcastReceiver broadcastReceiverNFC = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String actionNFC = intent.getAction();

            if (NfcAdapter.ACTION_ADAPTER_STATE_CHANGED.equals(actionNFC)) {
                if (intent.getIntExtra(NfcAdapter.EXTRA_ADAPTER_STATE, 1)
                        == NfcAdapter.STATE_OFF) ;
                {
                    tvNFC.setText(actionNFC + ": NFC is off");
                }
                if (intent.getIntExtra(NfcAdapter.EXTRA_ADAPTER_STATE, 1)
                        == NfcAdapter.STATE_ON)
                    tvNFC.setText(actionNFC + ": NFC is on");

            }

        }
    };

    private final BroadcastReceiver broadcastReceiverSound = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String actionSound = intent.getAction();

            if (AudioManager.RINGER_MODE_CHANGED_ACTION.equals(actionSound)) {
                if (intent.getIntExtra(AudioManager.EXTRA_RINGER_MODE, 0)
                        == AudioManager.RINGER_MODE_SILENT) {
                    tvSound.setText(actionSound + ": Ringer Mode Silent");
                    if (intent.getIntExtra(AudioManager.EXTRA_RINGER_MODE, 1)
                            == AudioManager.RINGER_MODE_VIBRATE) {
                        tvSound.setText(actionSound + ": Ringer Mode Vibrate");
                    }
                    if (intent.getIntExtra(AudioManager.EXTRA_RINGER_MODE, 0)
                            == AudioManager.RINGER_MODE_NORMAL) {
                        tvSound.setText(actionSound + ": Ringer Mode Normal");
                    }
                }
            }
        }
    };

    private final BroadcastReceiver broadcastReceiverBattery = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String actionBattery = intent.getAction();

            if (BatteryManager.ACTION_CHARGING.equals(actionBattery)) {
                tvBattery.setText(actionBattery);
            }
        }
    };

    private final BroadcastReceiver broadcastReceiverPowerSaveMode = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            final String actionPowerSaveMode = intent.getAction();

            if (PowerManager.ACTION_POWER_SAVE_MODE_CHANGED.equals(actionPowerSaveMode)) ;
            {
                tvPowerSave.setText(actionPowerSaveMode);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiverBattery);
        unregisterReceiver(broadcastReceiverSound);
        unregisterReceiver(broadcastReceiverNFC);
        unregisterReceiver(broadcastReceiverBluetooth);
        unregisterReceiver(broadcastReceiverPowerSaveMode);
    }
}




<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".BroadcastRecievers">

    <TextView
        android:id="@+id/tvNFC"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="18sp"
        android:text="@string/nfc_receiver"
        app:layout_constraintBottom_toTopOf="@+id/tvBattery"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:gravity="center_horizontal"/>

    <TextView
        android:id="@+id/tvBattery"
        android:textSize="18sp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/battery_receiver"
        app:layout_constraintBottom_toTopOf="@+id/tvLocation"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/tvNFC"
        android:gravity="center_horizontal"/>

    <TextView
        android:id="@+id/tvLocation"
        android:textSize="18sp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/power_save_mode"
        app:layout_constraintBottom_toTopOf="@+id/tvBluetooth"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/tvBattery"
        android:gravity="center_horizontal"/>

    <TextView
        android:id="@+id/tvBluetooth"
        android:textSize="18sp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/bluetooth_receiver"
        app:layout_constraintBottom_toTopOf="@+id/tvSound"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/tvLocation"
        android:gravity="center_horizontal"/>

    <TextView
        android:id="@+id/tvSound"
        android:textSize="18sp"
        android:gravity="center_horizontal"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/sound_setting_receiver"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/tvBluetooth" />


</androidx.constraintlayout.widget.ConstraintLayout>

![device-2019-06-27-025624](https://user-images.githubusercontent.com/51377336/60290989-e9452180-98e7-11e9-8dd9-ba2d32f8c9ad.png)
