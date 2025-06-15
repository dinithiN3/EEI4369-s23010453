package com.s23010453.dinithi;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class MapIntegrate extends AppCompatActivity implements OnMapReadyCallback{

    private Button btnGetPath;
    private EditText editTextText;
    public GoogleMap myMap;
    List<Address> listGeocorder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_map);

        editTextText = findViewById(R.id.editTextText);
        btnGetPath = findViewById(R.id.btnGetPath);

        btnGetPath.setOnClickListener(view -> {
          String textInput = editTextText.getText().toString();

          if(textInput.equals("")){
              Toast.makeText(this,"please enter an address", Toast.LENGTH_SHORT).show();
          }else{
              getPath(textInput);
          }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        try
        {
            listGeocorder = new Geocoder(this).getFromLocationName("open university, polgolla",1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        double longitude = listGeocorder.get(0).getLongitude();
        double latitude = listGeocorder.get(0).getLatitude();

        Log.i("GOOGLE_MAP_TAG","longitude value is:" + String.valueOf(longitude) + "latitude value is:" + String.valueOf(latitude));

        }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;
        LatLng ouslpolgolla = new LatLng(7.325437476789828, 80.65197051070787);
        myMap.addMarker(new MarkerOptions().position(ouslpolgolla).title("Marker OUSL Polgolla"));
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ouslpolgolla, 20.0f));
        myMap.getUiSettings().setZoomControlsEnabled(true);

    }



    public void getPath(String textInput){
        try{
            Uri uri = Uri.parse("https://www.google.com/maps/dir/"+textInput);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }


        catch (ActivityNotFoundException exception){
            Uri uri = Uri.parse("//https://play.google.com/store/apps/details?id=com.google.android.apps.maps&hl=en&gl=US");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
    }
}