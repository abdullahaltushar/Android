package com.example.maps_data;

import static java.lang.Float.parseFloat;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.maps_data.databinding.ActivityMapsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static String data;
    private static String data2;
    Float data1;
    Float data3;


    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        FirebaseDatabase db= FirebaseDatabase.getInstance();
        DatabaseReference root= db.getReference("Name");
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if( snapshot.exists()){
                    Map map =(Map) snapshot.getValue();
                    data=map.get("latitude").toString();
                    data2=map.get("longitude").toString();
                    data1= parseFloat(data);
                    data3= parseFloat(data2);
                    LatLng bangladesh = new LatLng(data3, data1);
                    mMap.addMarker(new MarkerOptions().position(bangladesh).title("Marker in Bangladesh"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(bangladesh));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bangladesh, 18), 5000, null);

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //data1= parseFloat(data);
        //data3= parseFloat(data2);
        // Add a marker in Sydney and move the camera


    }
}