package com.example.bai2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Lưu ý: Đảm bảo import đúng R của project bạn
import com.example.baitapbuoi1.R;
import com.example.baitapbuoi1.MainActivity;

public class LifecycleActivity extends AppCompatActivity {

    private static final String TAG = "Lifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lifecycle);
        
        Log.d(TAG, "Hàm onCreate được gọi");

        // Ánh xạ ID từ layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Explicit Intent: Mở trực tiếp MainActivity (Bài 1)
        Button btnExplicit = findViewById(R.id.btnExplicit);
        if (btnExplicit != null) {
            btnExplicit.setOnClickListener(v -> {
                Intent intent = new Intent(LifecycleActivity.this, MainActivity.class);
                startActivity(intent);
            });
        }

        // 2. Implicit Intent: Yêu cầu hệ thống mở trình duyệt web
        Button btnImplicit = findViewById(R.id.btnImplicit);
        if (btnImplicit != null) {
            btnImplicit.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"));
                startActivity(intent);
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Hàm onStart được gọi");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Hàm onResume được gọi");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Hàm onPause được gọi");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Hàm onStop được gọi");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "Hàm onRestart được gọi");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Hàm onDestroy được gọi");
    }
}
