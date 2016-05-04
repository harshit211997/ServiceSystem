package com.sdsmdg.servicesystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent(this, ToastService.class);
        startService(i);

        Intent i1 = new Intent(this, MyAccessibilityService.class);
        startService(i1);

    }
}
