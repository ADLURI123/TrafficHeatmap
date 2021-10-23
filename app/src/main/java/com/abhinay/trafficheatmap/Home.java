package com.abhinay.trafficheatmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;

public class Home extends AppCompatActivity {
    private Button addloc,viewheatmap,addcount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addloc = findViewById(R.id.btnaddloc);
        viewheatmap = findViewById(R.id.btnviewheatmap);
        addcount= findViewById(R.id.btncounter);
        addloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddLocation.class);
                startActivity(intent);
            }
        });
        viewheatmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        addcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), counter.class);
                startActivity(intent);
            }
        });
    }
}