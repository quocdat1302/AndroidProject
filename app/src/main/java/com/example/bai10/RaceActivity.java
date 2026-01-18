package com.example.bai10;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baitapbuoi1.R;

import java.util.Random;

public class RaceActivity extends AppCompatActivity {

    private ProgressBar pbRabbit, pbTurtle;
    private TextView tvProgressRabbit, tvProgressTurtle, tvWinner;
    private Button btnStart;
    private boolean isRacing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race);

        pbRabbit = findViewById(R.id.pbRabbit);
        pbTurtle = findViewById(R.id.pbTurtle);
        tvProgressRabbit = findViewById(R.id.tvProgressRabbit);
        tvProgressTurtle = findViewById(R.id.tvProgressTurtle);
        tvWinner = findViewById(R.id.tvWinner);
        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(v -> {
            if (!isRacing) {
                startRace();
            }
        });
    }

    private void startRace() {
        isRacing = true;
        btnStart.setEnabled(false);
        tvWinner.setText("Đang đua...");
        
        pbRabbit.setProgress(0);
        pbTurtle.setProgress(0);
        tvProgressRabbit.setText("0%");
        tvProgressTurtle.setText("0%");

        // Luồng của Thỏ
        new Thread(() -> {
            int progress = 0;
            Random random = new Random();
            while (progress < 100 && isRacing) {
                try {
                    Thread.sleep(200); // Giả lập độ trễ
                    // Thỏ chạy nhanh nhưng thỉnh thoảng dừng lại chơi
                    int speed = random.nextInt(10);
                    if (speed > 7) { // 30% cơ hội dừng lại
                        Thread.sleep(500);
                    } else {
                        progress += random.nextInt(5) + 2;
                    }
                    
                    if (progress > 100) progress = 100;

                    int finalProgress = progress;
                    runOnUiThread(() -> {
                        pbRabbit.setProgress(finalProgress);
                        tvProgressRabbit.setText(finalProgress + "%");
                        checkWinner("Thỏ 🐰", finalProgress);
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Luồng của Rùa
        new Thread(() -> {
            int progress = 0;
            Random random = new Random();
            while (progress < 100 && isRacing) {
                try {
                    Thread.sleep(300); // Rùa chậm hơn nhưng đều đặn
                    progress += random.nextInt(3) + 1;
                    
                    if (progress > 100) progress = 100;

                    int finalProgress = progress;
                    runOnUiThread(() -> {
                        pbTurtle.setProgress(finalProgress);
                        tvProgressTurtle.setText(finalProgress + "%");
                        checkWinner("Rùa 🐢", finalProgress);
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private synchronized void checkWinner(String name, int progress) {
        if (progress >= 100 && isRacing) {
            isRacing = false;
            tvWinner.setText("NGƯỜI CHIẾN THẮNG: " + name);
            btnStart.setEnabled(true);
            Toast.makeText(this, name + " đã về đích!", Toast.LENGTH_LONG).show();
        }
    }
}
