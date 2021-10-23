package com.abhinay.trafficheatmap;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class AddLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText name,lat,lon,intense;
    private DBHandler dbHandler;
    private Button addlocation;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        searchView = findViewById(R.id.idSearchView);
        name = findViewById(R.id.idLocation);
        lat = findViewById(R.id.idLatitude);
        lon = findViewById(R.id.idLongitude);
        intense = findViewById(R.id.idIntensity);
        dbHandler = new DBHandler(AddLocation.this);
        addlocation = findViewById(R.id.idAddLocation);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        addlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()|| lat.getText().toString().isEmpty() || lon.getText().toString().isEmpty() || intense.getText().toString().isEmpty()) {
                    Toast.makeText(AddLocation.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }
                String locname = name.getText().toString();
                Double latitude = Double.parseDouble(lat.getText().toString());
                Double longitude = Double.parseDouble(lon.getText().toString());
                Double intensity = Double.parseDouble(intense.getText().toString());
                dbHandler.addNewCourse(locname,latitude,longitude,intensity);
                Toast.makeText(AddLocation.this, "Location has been added.", Toast.LENGTH_SHORT).show();
                name.setText("");
                lat.setText("");
                lon.setText("");
                intense.setText("");
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;
                if (location != null || location.equals("")) {
                    Geocoder geocoder = new Geocoder(AddLocation.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);

                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    name.setText(location);
                    lat.setText(String.valueOf(address.getLatitude()));
                    lon.setText(String.valueOf(address.getLongitude()));

                    mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
