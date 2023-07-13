package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

public class SelectColourActivity extends AppCompatActivity {
    //keep references of views
    private ImageView preview;
    private SeekBar redS,blueS,greenS;
    //add listener to seekbars to track the current color and update in other view
    private final SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            preview.setBackgroundColor(Color.rgb(redS.getProgress(),
                    greenS.getProgress(),
                    blueS.getProgress()));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    //get current color from main activity and set views to provide information for user
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_colour);
        preview = findViewById(R.id.Preview);

        redS = findViewById(R.id.seekBarRed);
        blueS = findViewById(R.id.seekBarBlue);
        greenS = findViewById(R.id.seekBarGreen);

        int currentColor = getIntent().getExtras().getInt("currentColor");
        preview.setBackgroundColor(currentColor);

        redS.setProgress(Color.red(currentColor));
        blueS.setProgress(Color.blue(currentColor));
        greenS.setProgress(Color.green(currentColor));

        redS.setOnSeekBarChangeListener(listener);
        greenS.setOnSeekBarChangeListener(listener);
        blueS.setOnSeekBarChangeListener(listener);
    }
    //if ok button is pressed the color information will be send back
    public void onClickOk(View view) {
        Intent intent = new Intent();
        intent.putExtra("BrushColor",Color.rgb(redS.getProgress(),
                greenS.getProgress(),
                blueS.getProgress()));
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    //if cancel button or back is pressed nothing will be send back
    public void onClickCancel(View view) {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
    //save the current color setting user has made, in case something happens
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Red",redS.getProgress());
        outState.putInt("Blue",blueS.getProgress());
        outState.putInt("Green",greenS.getProgress());
    }
    //update views regards to user's previous choices
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        redS.setProgress(savedInstanceState.getInt("Red"));
        blueS.setProgress(savedInstanceState.getInt("Blue"));
        greenS.setProgress(savedInstanceState.getInt("Green"));
    }
}