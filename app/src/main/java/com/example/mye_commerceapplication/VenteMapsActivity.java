package com.example.mye_commerceapplication;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class VenteMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Double longitude_commande,latitude_commande,longitude_vendeur,latitude_vendeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vente_maps);
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

        longitude_commande=Double.valueOf(getIntent().getStringExtra("longitude_commande"));
        latitude_commande=Double.valueOf(getIntent().getStringExtra("latitude_commande"));
        longitude_vendeur=Double.valueOf(getIntent().getStringExtra("longitude_vendeur"));
        latitude_vendeur=Double.valueOf(getIntent().getStringExtra("latitude_vendeur"));
        // Add a marker in Sydney and move the camera
        LatLng command = new LatLng(latitude_commande, longitude_commande);
        LatLng seller=new LatLng(latitude_vendeur,longitude_vendeur);

        mMap.addMarker(new MarkerOptions().position(seller).title("Seller"));
        mMap.addMarker(new MarkerOptions().position(command).title("your Command"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(command));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(seller));
        //detection de la longitude et latitude a l'aide de setonMapClickListener() !!!!

        /*mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng clickCoords) {
                //Log.e("TAG", "Found @ " + clickCoords.latitude + " " + clickCoords.longitude);
                System.out.println("clicked point:\n"+"latitude:\t"+clickCoords.latitude+"\nlongitude:\t"+clickCoords.longitude);
                Intent intent =new Intent(VenteMapsActivity.this,SalesActivity.class);
                startActivity(intent);
            }
        });*/
    }
}
