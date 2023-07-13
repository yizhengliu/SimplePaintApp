package com.example.coursework;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    //reference to FPV
    private FingerPainterView myFingerPainterView;
    //activity launcher for color result
    private ActivityResultLauncher<Intent> colorSelectionLauncher;
    //activity launcher for brush setting result
    private ActivityResultLauncher<Intent> brushSettingLauncher;
    //The view model which stores the information of this view
    private MainViewModel myViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //load the layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initiate local variables
        myViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        myFingerPainterView = findViewById(R.id.FPV);
        //load image if there is one
        if(getIntent().getData() != null)
            myFingerPainterView.load(getIntent().getData());
        //store the current(initial) value of each var in my view model
        myViewModel.setBrushType(myFingerPainterView.getBrush() == Paint.Cap.ROUND ?
                myViewModel.ROUND : myViewModel.SQUARE);
        myViewModel.setColor(myFingerPainterView.getColour());
        myViewModel.setBrushWidth(myFingerPainterView.getBrushWidth());
        //initiate color launcher which will store the result back into the model
        colorSelectionLauncher = registerForActivityResult(new
                        ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        if (result.getData() != null)
                            myViewModel.setColor(result.getData().getExtras().getInt("BrushColor"));
                    }
                });
        //initiate brush launcher which will store the result back into the model
        brushSettingLauncher = registerForActivityResult(new
                        ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (result.getData() != null){
                            if (result.getData().getExtras().getBoolean("isSquare")) myViewModel.setBrushType(myViewModel.SQUARE);
                            else myViewModel.setBrushType(myViewModel.ROUND);
                            myViewModel.setBrushWidth(result.getData().getExtras().getInt("newSize"));
                        }
                    }
                });
        //binding.So that once a data in the view model is changed, the configuration in the FPV will also be updated
        myViewModel.getColor().observe(this,color -> myFingerPainterView.setColour(color));
        myViewModel.getBrushType().observe(this, brushType -> myFingerPainterView.setBrush(brushType == myViewModel.ROUND ?
                Paint.Cap.ROUND : Paint.Cap.SQUARE));
        myViewModel.getBrushWidth().observe(this,brushWidth -> myFingerPainterView.setBrushWidth(brushWidth));
    }
    //when select color button is pressed, change current activity and inform it the current color
    public void SelectColor(View view) {
        Intent colorSelectionIntent = new Intent(MainActivity.this, SelectColourActivity.class);
        colorSelectionIntent.putExtra("currentColor", myViewModel.getColor().getValue());
        colorSelectionLauncher.launch(colorSelectionIntent);
    }
    //when brush setting button is pressed, change current activity and inform it current color
    public void BrushSetting(View view) {
        Intent brushSettingIntent = new Intent(MainActivity.this, BrushSettingActivity.class);
        brushSettingIntent.putExtra("currentSize", myFingerPainterView.getBrushWidth());
        brushSettingIntent.putExtra("currentType", myFingerPainterView.getBrush() == Paint.Cap.ROUND);
        brushSettingLauncher.launch(brushSettingIntent);
    }
    //maintain the data from model throughout main activity's expected lifecycle
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("prevColor", myViewModel.getColor().getValue());
        outState.putInt("prevSize", myViewModel.getBrushWidth().getValue());
        outState.putInt("prevType", myViewModel.getBrushType().getValue());
        super.onSaveInstanceState(outState);
    }
    // load the data back and reset the view
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        myViewModel.setBrushType(savedInstanceState.getInt("prevType"));
        myViewModel.setColor(savedInstanceState.getInt("prevColor"));
        myViewModel.setBrushWidth(savedInstanceState.getInt("prevSize"));
    }
}