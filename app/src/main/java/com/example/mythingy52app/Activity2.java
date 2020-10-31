package com.example.mythingy52app;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class Activity2 extends AppCompatActivity {
    TextView service1;
    Button getService1;
    Button Disconnect;
    String TAG = "SecondActivity";
    BluetoothGatt test;
    int BUTTON_PRESSED = 1;
    int BUTTON_RELEASED = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        final Intent intent = getIntent();
        final BluetoothDevice device = intent.getParcelableExtra("Example item");
        service1 = findViewById(R.id.service1);
        getService1 = findViewById(R.id.getService1);
        Disconnect = findViewById(R.id.disconnect);

        getService1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test = device.connectGatt(getApplicationContext(), false, gattCallback);

            }
        });
        Disconnect.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test.close();
                test = null;
                Intent intent = new Intent(Activity2.this , MainActivity.class);
                startActivity(intent);
            }
        }));
    }
    private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyUpdate(gatt, txPhy, rxPhy, status);
        }

        @Override
        public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyRead(gatt, txPhy, rxPhy, status);
        }

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            if(status == BluetoothGatt.GATT_SUCCESS){
                if(newState == BluetoothProfile.STATE_CONNECTED){
                    gatt.discoverServices();
                    Log.d(TAG, "Bluetooth connected");
                }
                else if(newState == BluetoothProfile.STATE_DISCONNECTED){
                    Log.d(TAG,  "Bluetooth disconnected");
                }
            }
            else{
                Log.d(TAG, "Bluetooth error!!!!!!");
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            BluetoothGattService servicio = gatt.getService(UUID.fromString("ef680300-9b35-4933-9b10-52ffa9740042"));
            BluetoothGattCharacteristic BTCharac = servicio.getCharacteristic(new UUID(0xEF6803029B354933L, 0x9B1052FFA9740042L));
            gatt.setCharacteristicNotification(BTCharac,true);
            BluetoothGattDescriptor descriptor = BTCharac.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            boolean success = gatt.writeDescriptor(descriptor);
            Log.d(TAG, "The notification was enabled successfully?" + success);
        }


        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);

        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            Log.d(TAG, "Si funciona la notificación");
            if(characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0)==BUTTON_PRESSED){
                Log.d(TAG, "Botón oprimido");
            }
            else{
                Log.d(TAG, "Botón ya no oprimido");
            }
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
        }
    };


}