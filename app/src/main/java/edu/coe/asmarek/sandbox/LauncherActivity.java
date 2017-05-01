package edu.coe.asmarek.sandbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        Log.d("Logger", "Working");
        startActivity(new Intent("edu.coe.asmarek.sandbox.MainActivity"));
    }
}
