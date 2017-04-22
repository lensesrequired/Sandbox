package edu.coe.asmarek.sandbox;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class SlidingTimer extends LinearLayout implements View.OnClickListener {

    private Handler refreshHandler = new Handler();
    private OnTimerEndListener mListener;
    private float length;
    private int time;
    private float movement;
    private ImageButton btnStart;
    private ImageButton btnPause;
    private SeekBar slider;
    private TextView label;


    public SlidingTimer(Context context) {
        super(context);

        initViews(context);
    }

    public SlidingTimer(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SlidingTimer);
        time = ta.getInt(R.styleable.SlidingTimer_length , 1);
        ta.recycle();

        initViews(context);
    }

    public SlidingTimer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SlidingTimer);
        time = ta.getInt(R.styleable.SlidingTimer_length , 1);
        ta.recycle();

        initViews(context);
    }

    public void setTime(int t) {time = t;}

    public void setLabel(String s) {label.setText(s + " seconds");}

    private void initViews(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sliding_timer, this);

        btnStart = (ImageButton) findViewById(R.id.btnStart);
        btnPause = (ImageButton) findViewById(R.id.btnPause);
        slider = (SeekBar) findViewById(R.id.seekBar);
        label = (TextView) findViewById(R.id.txtTime);

        btnStart.setOnClickListener(this);
        btnPause.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                if (slider.getProgress() == slider.getMax()) {
                    slider.setProgress(0);
                }
                slider.setMax(this.getWidth());
                length = this.getWidth();
                movement = length/time;
                refreshHandler.post(update);
                break;
            case R.id.btnPause:
                refreshHandler.removeCallbacks(update);
                break;
        }
    }

    private Runnable update = new Runnable() {
        @Override
        public void run() {
            int newVal = Math.round((slider.getProgress() + movement/5));
            slider.setProgress(newVal);

            if(!(slider.getProgress() == slider.getMax())) {
                refreshHandler.postDelayed(update, 200);
            } else {
                mListener.onTimerEndListener();
            }
        }
    };

    public void setOnTimerEndListener(OnTimerEndListener onTimerEndListener) {
        mListener = onTimerEndListener;
    }
}
