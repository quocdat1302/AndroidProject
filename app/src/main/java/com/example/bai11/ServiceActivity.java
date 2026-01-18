package com.example.bai11;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baitapbuoi1.R;

public class ServiceActivity extends AppCompatActivity {

    private Button btnStart, btnStop;
    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        btnStart = findViewById(R.id.btnStartMusic);
        btnStop = findViewById(R.id.btnStopMusic);

        // Khởi tạo và đăng ký BroadcastReceiver
        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(myReceiver, filter);

        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(ServiceActivity.this, MusicService.class);
            startService(intent);
        });

        btnStop.setOnClickListener(v -> {
            Intent intent = new Intent(ServiceActivity.this, MusicService.class);
            stopService(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Hủy đăng ký để tránh rò rỉ bộ nhớ
        unregisterReceiver(myReceiver);
    }
}
