package com.example.bai4;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baitapbuoi1.R;

public class HomeActivity extends AppCompatActivity {

    private TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvWelcome = findViewById(R.id.tvWelcome);

        // Nhận dữ liệu từ Intent
        String username = getIntent().getStringExtra("USER_NAME");

        if (username != null && !username.isEmpty()) {
            tvWelcome.setText("Xin chào, " + username + "!");
        }
    }
}
