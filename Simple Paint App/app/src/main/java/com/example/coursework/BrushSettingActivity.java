package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class BrushSettingActivity extends AppCompatActivity {
    //keep references of views
    private Switch aSwitch;
    private SeekBar size;
    //get current brush setting from main activity and set views to provide information for user
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brush_setting);
        int currentSize = getIntent().getExtras().getInt("currentSize");
        ((TextView)(findViewById(R.id.SizePreview))).setText(""+ currentSize);
        aSwitch = findViewById(R.id.SquareOrRound);
        aSwitch.setChecked(!getIntent().getExtras().getBoolean("currentType"));
        size = findViewById(R.id.brushSize);
        //initiate listener of the seekbar, which updates the size of the brush will be in a text view
        size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView tv = findViewById(R.id.SizePreview);
                tv.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        size.setProgress(currentSize);
    }


    //when ok button is pressed, pass back the result user had made
    public void OnClickOk(View view) {
        Intent intent = new Intent();
        intent.putExtra("isSquare", aSwitch.isChecked());
        intent.putExtra("newSize", size.getProgress());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
    //when cancel button or back is clicked, return nothing
    public void onClickCancel(View view) {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
    //save the current brush setting user has made, in case something happens
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("isSquare", aSwitch.isChecked());
        outState.putInt("currentSize", size.getProgress());
        super.onSaveInstanceState(outState);
    }
    //update views regards to user's previous choices
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        aSwitch.setChecked(savedInstanceState.getBoolean("isSquare"));
        size.setProgress(savedInstanceState.getInt("currentSize"));
    }
}