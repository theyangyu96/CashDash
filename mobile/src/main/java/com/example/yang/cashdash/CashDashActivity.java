package com.example.yang.cashdash;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class CashDashActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int mode = 0; //0 = cash, 1 = dash
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_dash);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.cash_map);
        SupportMapFragment mapFragment2 = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.dash_map);
        mapFragment.getMapAsync(this);
        mapFragment2.getMapAsync(this);
        //THIS HIDES THE FRAGMENT
        getSupportFragmentManager().beginTransaction().hide(mapFragment2).commit();

        Button sos= (Button)findViewById(R.id.SOS_button);
        sos.setOnClickListener(hide);
        Button cash_button = (Button)findViewById(R.id.cash_button);
        cash_button.setOnClickListener(cashShow);
        Button dash_button = (Button)findViewById(R.id.dash_button);
        dash_button.setOnClickListener(dashShow);
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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        final LatLng PERTH = new LatLng(-31.90, 115.86);
//        Marker perth = mMap.addMarker(new MarkerOptions()
//                .position(PERTH)
//                .title("Yang")
//                .snippet("I need $5"));
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
        {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(15)                   // Sets the zoom
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }
    }


    private View.OnClickListener hide = new View.OnClickListener() {
        public void onClick(View v) {
            (findViewById(R.id.SOS_button)).setVisibility(View.GONE);
            (findViewById(R.id.confirm_SOS_button)).setVisibility(View.VISIBLE);
            (findViewById(R.id.amount_EditText)).setVisibility(View.VISIBLE);
        }
    };
    private View.OnClickListener cashShow = new View.OnClickListener() {
        public void onClick(View v) {
            //change color
            (findViewById(R.id.cash_button)).setBackgroundColor(Color.parseColor("#8BC34A"));
            (findViewById(R.id.dash_button)).setBackgroundColor(Color.parseColor("#649130"));
            //hide dash show cash
            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager()
                    .findFragmentById(R.id.dash_map)).commit();
            getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager()
                    .findFragmentById(R.id.cash_map)).commit();

        }
    };
    private View.OnClickListener dashShow = new View.OnClickListener() {
        public void onClick(View v) {
            (findViewById(R.id.cash_button)).setBackgroundColor(Color.parseColor("#649130"));
            (findViewById(R.id.dash_button)).setBackgroundColor(Color.parseColor("#8BC34A"));
            //hide dash show cash
            getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager()
                    .findFragmentById(R.id.dash_map)).commit();
            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager()
                    .findFragmentById(R.id.cash_map)).commit();

        }
    };
}

