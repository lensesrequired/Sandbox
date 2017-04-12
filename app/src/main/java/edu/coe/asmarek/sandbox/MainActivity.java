package edu.coe.asmarek.sandbox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener {

    Spinner colors;
    ArrayList<String> colorsList;
    Button submit;
    Button remember;

    SensorManager smgr;
    Sensor acc;
    ArrayList<Float> lastVals;
    long lastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String theme = getIntent().getStringExtra("theme");
        if (theme == null) {
            SharedPreferences s = getSharedPreferences("myFile", 0);
            theme = s.getString("theme", "Default");
        }

        switch (theme) {
            case "Black":
                setTheme(R.style.BlackTheme_NoActionBar);
                break;
            case "Blue":
                setTheme(R.style.BlueTheme_NoActionBar);
                break;
            case "Green":
                this.setTheme(R.style.GreenTheme_NoActionBar);
                break;
            case "Orange":
                this.setTheme(R.style.OrangeTheme_NoActionBar);
                break;
            case "Pink":
                this.setTheme(R.style.PinkTheme_NoActionBar);
                break;
            case "Purple":
                this.setTheme(R.style.PurpleTheme_NoActionBar);
                break;
            case"Red":
                this.setTheme(R.style.RedTheme_NoActionBar);
                break;
            case "White":
                this.setTheme(R.style.WhiteTheme_NoActionBar);
                break;
            case "Yellow":
                this.setTheme(R.style.YellowTheme_NoActionBar);
                break;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setButtons();
        setColorSpinner(theme);

        setSensors();
    }

    @Override
    protected void onResume() {
        smgr.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL);

        super.onResume();
    }

    @Override
    protected void onPause() {
        smgr.unregisterListener(this, acc);

        super.onPause();
    }

    private void setSensors() {
        smgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        acc = smgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        lastVals = new ArrayList<Float>(Arrays.asList((float)0,(float)0,(float)0));
        lastTime = System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent i;

        //noinspection SimplifiableIfStatement
        switch (id) {
            case (R.id.action_theme):
                i = new Intent("edu.coe.asmarek.sandbox.MainActivity");
                startActivity(i);
                break;
            case (R.id.action_customBackground):
                i = new Intent("edu.coe.asmarek.sandbox.GetPhoto");
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setButtons() {
        submit = (Button) findViewById(R.id.btnSubmit);
        remember = (Button) findViewById(R.id.btnRemember);

        submit.setOnClickListener(this);
        remember.setOnClickListener(this);
    }

    private void setColorSpinner(String theme) {
        colors = (Spinner) findViewById(R.id.spnColors);
        colorsList = new ArrayList<String>(Arrays.asList("Black", "Blue", "Green", "Orange",
                "Pink", "Purple", "Red", "White", "Yellow"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, colorsList);
        colors.setAdapter(adapter);
        int pos;
        if (theme != "Default") {
            pos = colorsList.indexOf(theme);
        } else {
            pos = 0;
        }
        colors.setSelection(pos);
    }

    @Override
    public void onClick(View v) {
        String color = colors.getSelectedItem().toString();
        Intent i = new Intent("edu.coe.asmarek.sandbox.MainActivity");
        switch (v.getId()) {
            case R.id.btnSubmit:
                i.putExtra("theme", color);
                startActivity(i);
                break;
            case R.id.btnRemember:
                SharedPreferences s = getSharedPreferences("myFile", 0);
                SharedPreferences.Editor e = s.edit();
                e.putString("theme", color);
                e.commit();
                Toast.makeText(this, "Color preference saved", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                if(shake(event)) {
                    String c = colorsList.get((int)(Math.random()*colorsList.size()));
                    Intent i = new Intent("edu.coe.asmarek.sandbox.MainActivity");
                    i.putExtra("theme", c);
                    startActivity(i);
                }
                break;
        }
    }

    private boolean shake(SensorEvent event) {
        if(lastVals.get(0) == 0 && lastVals.get(1) == 0 && lastVals.get(2) == 0) {
            lastVals.set(0, event.values[0]);
            lastVals.set(1, event.values[1]);
            lastVals.set(2, event.values[2]);
        } else {
            long currTime = System.currentTimeMillis();
            if(currTime - lastTime > 150) {
                long diffTime = currTime - lastTime;
                lastTime = currTime;

                float speed = Math.abs(event.values[0]+event.values[1]+event.values[2]
                        - lastVals.get(0) - lastVals.get(1) - lastVals.get(2)) / diffTime * 10000;

                if (speed > 800) {
                    return true;
                }

                lastVals.set(0, event.values[0]);
                lastVals.set(1, event.values[1]);
                lastVals.set(2, event.values[2]);
            }
        }
        return false;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
