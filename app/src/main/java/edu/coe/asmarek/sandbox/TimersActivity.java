package edu.coe.asmarek.sandbox;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class TimersActivity extends AppCompatActivity implements View.OnClickListener, OnTimerEndListener {

    Button add;
    EditText seconds;
    LinearLayout ll;

    NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
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
            case (R.id.action_timers):
                i = new Intent("edu.coe.asmarek.sandbox.TimersActivity");
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        add = (Button) findViewById(R.id.btnAddTimer);
        seconds = (EditText) findViewById(R.id.edtNumSeconds);
        ll = (LinearLayout) findViewById(R.id.llTimers);

        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddTimer:
                SlidingTimer t = new SlidingTimer(this);
                t.setTime(Integer.parseInt(seconds.getText().toString()));
                t.setLabel(seconds.getText().toString());
                t.setOnTimerEndListener(this);
                ll.addView(t);
                break;
        }
    }

    @Override
    public void onTimerEndListener() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setContentTitle("Timer!")
                .setContentText("Timer Ended!");

        mNotificationManager.notify(1, mBuilder.build());
    }
}
