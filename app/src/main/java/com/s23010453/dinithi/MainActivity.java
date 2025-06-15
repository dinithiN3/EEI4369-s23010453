package com.s23010453.dinithi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.material.textfield.TextInputEditText;


public class MainActivity extends AppCompatActivity {

    private Button btnGetPath;
    private TextInputEditText username;
    private TextInputEditText username2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        btnGetPath = findViewById(R.id.btnGetPath);
        username = findViewById(R.id.username);
        username2 = findViewById(R.id.username2);

        btnGetPath.setOnClickListener(view -> {
            String textInput1 = username.getText().toString();
            String textInput2 = username2.getText().toString();

            if(textInput1.equals("") || textInput2.equals("")){
                Toast.makeText(this,"please enter your credentials", Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(this, MapIntegrate.class); // Change to your actual target activity
                intent.putExtra("username", textInput1);
                intent.putExtra("username2", textInput2);
                startActivity(intent);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });
    }

}