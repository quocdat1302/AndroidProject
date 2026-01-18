package com.example.bai11;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {
            boolean isAirplaneModeOn = intent.getBooleanExtra("state", false);
            if (isAirplaneModeOn) {
                Toast.makeText(context, "Chế độ máy bay: ĐANG BẬT ✈️", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Chế độ máy bay: ĐÃ TẮT 🌐", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
