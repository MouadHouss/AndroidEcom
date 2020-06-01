package com.example.mye_commerceapplication;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.mye_commerceapplication.Model.Ventes;
import com.example.mye_commerceapplication.Model.VentesFireBase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class AddMarkerMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;

    private Double longitude;
    private Double latitude;
    private Marker orderMarker;
    private VentesFireBase ventesFireBase;
    private Ventes vente;
    private Bundle extras;
    private String idVente;

    Button buttonConfirmerPosition;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marker_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        buttonConfirmerPosition = findViewById(R.id.setCoordButton);
        mapFragment.getMapAsync(this);
        extras = getIntent().getExtras();
        idVente=extras.getString("idVente");
        new VentesFireBase().readVentes(new VentesFireBase.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Ventes> ventes, ArrayList<String> keys) {
                for (Ventes v:ventes ){

                    if (v.getIdLignCommande().equals(idVente)){
                        vente=v;
                    }
                }
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

        buttonConfirmerPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vente.setLatitude(String.valueOf(orderMarker.getPosition().latitude));
                vente.setLongitude(String.valueOf(orderMarker.getPosition().longitude));
                new VentesFireBase().addVente(vente);
                finish();

            }
        });

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


        // Add a marker in Sydney and move the camera
        LatLng order = new LatLng(33.9987686, -6.8548013);
        orderMarker = mMap.addMarker(new MarkerOptions().position(order).title("Order Position").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(order));


        //detection de la longitude et latitude a l'aide de setonMapClickListener() !!!!

        mMap.setOnMarkerDragListener(this);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
        /*int X = (int)event.getX();
        int Y = (int)event.getY();
*/
        //GeoPoint geoPoint = mapView.getProjection().fromPixels(X, Y);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }
}
