package com.example.yang.cashdash;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CashDashActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap, CashMap;
    private Location currentLocation;
    private GoogleApiClient mGoogleApiClient;
    private int counter = 0;
    private LocationRequest mLocationRequest;
    private Marker requestMarker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_dash);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                        //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.cash_map);
        SupportMapFragment mapFragment2 = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.dash_map);
        mapFragment.getMapAsync(this);
        mapFragment2.getMapAsync(this);
        //THIS HIDES THE FRAGMENT
        getSupportFragmentManager().beginTransaction().hide(mapFragment2).commit();

        Button sos = (Button) findViewById(R.id.SOS_button);
        sos.setOnClickListener(hide);
        Button confirm = (Button) findViewById(R.id.confirm_SOS_button);
        confirm.setOnClickListener(request);
        Button req = (Button) findViewById(R.id.requesting_button);
        req.setOnClickListener(backToSos);
        Button cash_button = (Button) findViewById(R.id.cash_button);
        cash_button.setOnClickListener(cashShow);
        Button dash_button = (Button) findViewById(R.id.dash_button);
        dash_button.setOnClickListener(dashShow);
        RelativeLayout relLay = (RelativeLayout) findViewById(R.id.map_container);
        relLay.setOnClickListener(returnToNormal);
        mGoogleApiClient.connect();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest()
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
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
        if (counter == 0){
            CashMap = googleMap;
            CashMap.setMyLocationEnabled(true);
            CashMap.getUiSettings().setZoomGesturesEnabled(true);
            CashMap.getUiSettings().setScrollGesturesEnabled(true);
            if (currentLocation != null) {
                CashMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 13));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))      // Sets the center of the map to location user
                        .zoom(15)                   // Sets the zoom
                        .build();                   // Creates a CameraPosition from the builder
                CashMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
            counter++;
        } else {
            mMap = googleMap;
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomGesturesEnabled(true);
            mMap.getUiSettings().setScrollGesturesEnabled(true);
            if (currentLocation != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 13));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))      // Sets the center of the map to location user
                        .zoom(15)                   // Sets the zoom
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        }
    }

    private View.OnClickListener hide = new View.OnClickListener() {
        public void onClick(View v) {
            (findViewById(R.id.SOS_button)).setVisibility(View.GONE);
            (findViewById(R.id.confirm_SOS_button)).setVisibility(View.VISIBLE);
            (findViewById(R.id.amount_EditText)).setVisibility(View.VISIBLE);
        }
    };
    private View.OnClickListener returnToNormal = new View.OnClickListener() {
        public void onClick(View v) {
            (findViewById(R.id.SOS_button)).setVisibility(View.VISIBLE);
            (findViewById(R.id.confirm_SOS_button)).setVisibility(View.GONE);
            (findViewById(R.id.amount_EditText)).setVisibility(View.GONE);
        }
    };
    private View.OnClickListener request = new View.OnClickListener() {
        public void onClick(View v) {
            (findViewById(R.id.confirm_SOS_button)).setVisibility(View.GONE);
            (findViewById(R.id.amount_EditText)).setVisibility(View.GONE);
            (findViewById(R.id.requesting_button)).setVisibility(View.VISIBLE);
            requestMarker = CashMap.addMarker(new MarkerOptions()
                    .position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
                    .title("$"+((EditText)findViewById(R.id.amount_EditText)).getText().toString())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.cashdash_marker)));
        }
    };
    private View.OnClickListener backToSos = new View.OnClickListener() {
        public void onClick(View v) {
            (findViewById(R.id.SOS_button)).setVisibility(View.VISIBLE);
            (findViewById(R.id.requesting_button)).setVisibility(View.GONE);
            if (requestMarker != null) {
                requestMarker.remove();
            }
        }
    };



    private View.OnClickListener cashShow = new View.OnClickListener() {
        public void onClick(View v) {
            //change color
            (findViewById(R.id.cash_button)).setBackgroundColor(Color.parseColor("#336600"));
            (findViewById(R.id.dash_button)).setBackgroundColor(Color.parseColor("#4c9900"));
            //hide dash show cash
            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager()
                    .findFragmentById(R.id.dash_map)).commit();
            getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager()
                    .findFragmentById(R.id.cash_map)).commit();
            (findViewById(R.id.SOS_button)).setVisibility(View.VISIBLE);
        }
    };
    private View.OnClickListener dashShow = new View.OnClickListener() {
        public void onClick(View v) {
            (findViewById(R.id.cash_button)).setBackgroundColor(Color.parseColor("#4c9900"));
            (findViewById(R.id.dash_button)).setBackgroundColor(Color.parseColor("#336600"));
            //hide dash show cash
            getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager()
                    .findFragmentById(R.id.dash_map)).commit();
            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager()
                    .findFragmentById(R.id.cash_map)).commit();

            (findViewById(R.id.SOS_button)).setVisibility(View.GONE);
            (findViewById(R.id.confirm_SOS_button)).setVisibility(View.GONE);
            (findViewById(R.id.amount_EditText)).setVisibility(View.GONE);

        }
    };

    @Override
    public void onConnected(Bundle bundle) {
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
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        Log.d("ABCD","CONNECTED");
        if (location == null) {
            Log.d("ABCD", "UPDATING LOCATION");
        } else {
            //If everything went fine lets get latitude and longitude
            currentLocation = location;
            Log.d("ABCD", "Location updated");
            new getAllATM().execute();
            Log.d("ABCD", currentLocation.getLatitude()+"");
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("ABCD", "Firing onLocationChanged..............................................");
        if (location.distanceTo(currentLocation)/1000 > 1) {
            update();
        }
        currentLocation = location;

    }


    private void update() {
        Log.d("ABCD", "UI update initiated .............");
        if (currentLocation != null) {
            new getAllATM().execute();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    class getAllATM extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            try {

                String link = String.format("http://api.reimaginebanking.com/atms?lat=%1$s&lng=%2$s&rad=1&key=%3$s", currentLocation.getLatitude(), currentLocation.getLongitude(), getResources().getString(R.string.Nessie_API_Key));
                Log.d("ABCD", link);
                URL url = new URL(link);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR1";
            }

            Log.d("ABCD", response);
            try {
                JSONObject json_obj = new JSONObject(response);
                JSONArray all_atm = json_obj.getJSONArray("data");
                for (int i = 0; i < all_atm.length(); i++) {
                    JSONObject atm = all_atm.getJSONObject(i);
                    JSONObject geo = atm.getJSONObject("geocode");
                    Location temp = new Location("");
                    temp.setLatitude(geo.getDouble("lat"));
                    temp.setLongitude(geo.getDouble("lng"));
                    CashMap.addMarker(new MarkerOptions()
                            .position(new LatLng(geo.getDouble("lat"), geo.getDouble("lng")))
                            .title(atm.getString("name"))
                            .snippet((double) Math.round(currentLocation.distanceTo(temp) / 1000 * 100000d) / 100000d + "km away"));

                    Log.d("ABCD", "marker added");
                }
            } catch (Exception e) {
                Log.d("ABCD", "ERROR commited");
                Log.d("ABCD", e.toString());
            }
        }
    }

}

