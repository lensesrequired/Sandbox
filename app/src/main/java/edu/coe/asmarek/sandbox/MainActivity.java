package edu.coe.asmarek.sandbox;

import android.content.Intent;
import android.graphics.Color;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner colors;
    Button submit;
    Button remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String theme = getIntent().getStringExtra("theme");
        if (theme == null)
            theme = "Default";

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

        colors = (Spinner) findViewById(R.id.spnColors);
        String[] items = new String[]{"Black", "Blue", "Green", "Orange", "Pink", "Purple", "Red", "White", "Yellow"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        colors.setAdapter(adapter);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setButtons() {
        submit = (Button) findViewById(R.id.btnSubmit);
        remember = (Button) findViewById(R.id.btnRemember);

        submit.setOnClickListener(this);
        remember.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String color = colors.getSelectedItem().toString();
        Intent i = new Intent("edu.coe.asmarek.sandbox.MainActivity");
        switch (v.getId()) {
            case R.id.btnSubmit:
                switch (color) {
                    case "Black":
                        this.setTheme(R.style.BlackTheme_NoActionBar);
                        i.putExtra("theme", "Black");
                        startActivity(i);
                        break;
                    case "Blue":
                        this.setTheme(R.style.BlueTheme_NoActionBar);
                        startActivity(new Intent(getIntent()).putExtra("theme", "Blue"));
                        break;
                    case "Green":
                        this.setTheme(R.style.GreenTheme_NoActionBar);
                        startActivity(new Intent(getIntent()).putExtra("theme", "Green"));
                        break;
                    case "Orange":
                        this.setTheme(R.style.OrangeTheme_NoActionBar);
                        startActivity(new Intent(getIntent()).putExtra("theme", "Orange"));
                        break;
                    case "Pink":
                        this.setTheme(R.style.PinkTheme_NoActionBar);
                        startActivity(new Intent(getIntent()).putExtra("theme", "Pink"));
                        break;
                    case "Purple":
                        this.setTheme(R.style.PurpleTheme_NoActionBar);
                        startActivity(new Intent(getIntent()).putExtra("theme", "Purple"));
                        break;
                    case"Red":
                        this.setTheme(R.style.RedTheme_NoActionBar);
                        startActivity(new Intent(getIntent()).putExtra("theme", "Red"));
                        break;
                    case "White":
                        this.setTheme(R.style.WhiteTheme_NoActionBar);
                        startActivity(new Intent(getIntent()).putExtra("theme", "White"));
                        break;
                    case "Yellow":
                        this.setTheme(R.style.YellowTheme_NoActionBar);
                        startActivity(new Intent(getIntent()).putExtra("theme", "Yellow"));
                        break;
                }
                break;
            case R.id.btnRemember:
                break;
        }
    }
}
