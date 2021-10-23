package com.abhinay.trafficheatmap;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DBHandler dbHandler;

    LatLng delhi = new LatLng(28.644800, 77.216721);

    private Button hybridMapBtn, terrainMapBtn, satelliteMapBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hybridMapBtn = findViewById(R.id.idBtnHybridMap);
        terrainMapBtn = findViewById(R.id.idBtnTerrainMap);
        satelliteMapBtn = findViewById(R.id.idBtnSatelliteMap);
        dbHandler = new DBHandler(MainActivity.this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        hybridMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        });

        terrainMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            }
        });

        satelliteMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(delhi));
        addHeatMap();
    }
    private void addHeatMap()
    {
        ArrayList <location> list = dbHandler.readLocations();
        List<WeightedLatLng> latLngs = new ArrayList<WeightedLatLng>();
        for (int i=0;i<list.size();i++)
        {
            mMap.addMarker(new MarkerOptions().position(new LatLng(list.get(i).getLatitude(),list.get(i).getLongitude())).title(list.get(i).getName()));
            latLngs.add(new WeightedLatLng(new LatLng(list.get(i).getLatitude(),list.get(i).getLongitude()),list.get(i).getIntensity()));
        }
        HeatmapTileProvider provider = new HeatmapTileProvider.Builder().weightedData(latLngs).radius(50).maxIntensity(1000).build();
        TileOverlay overlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(provider));
    }

}
