package com.example.week4day3;

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


