package com.s23010453.dinithi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private Button btnGetPath;
    private TextInputEditText username;
    private TextInputEditText password;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnGetPath = findViewById(R.id.btnGetPath);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        dbHelper = new DatabaseHelper(this);

        btnGetPath.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please enter your credentials", Toast.LENGTH_SHORT).show();
            } else {
                boolean inserted = dbHelper.addUser(user, pass);
                if (inserted) {
                    Toast.makeText(this, "Saved to database!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, MapIntegrate.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Error saving to database", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
