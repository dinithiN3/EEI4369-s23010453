package com.s23010453.dinithi;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class SensorIntegration extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor tempSensor;
    private TextView textView;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private final float THRESHOLD = 53;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sensor_integration);

        textView = findViewById(R.id.textView);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        if (tempSensor == null) {
            textView.setText("Ambient temperature sensor not available!");
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.song);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    protected void onResume() {
        super.onResume();
        if (tempSensor != null)
            sensorManager.registerListener((SensorEventListener) this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener((SensorListener) this);
    }
    public void onSensorChanged(SensorEvent event) {
        float currentTemp = event.values[0];
        textView.setText("Current Temperature: " + currentTemp + "°C");

        if (currentTemp > THRESHOLD && !isPlaying) {
            mediaPlayer.start();
            isPlaying = true;
        } else if (currentTemp <= THRESHOLD && isPlaying) {
            mediaPlayer.pause();
            isPlaying = false;
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

}