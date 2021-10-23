package com.abhinay.trafficheatmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class counter extends AppCompatActivity {
    private Button btn;
    private EditText txt;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        txt=findViewById(R.id.dummy1);
        btn=findViewById(R.id.dummy2);
        text=findViewById(R.id.dummy3);
        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));
        Python py = Python.getInstance();
        PyObject pyobj = py.getModule("script");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=Integer.parseInt(txt.getText().toString());
                PyObject obj = pyobj.callAttr("main",a);

                text.setText(obj.toString());
            }
        });
    }

}