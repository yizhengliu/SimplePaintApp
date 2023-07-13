package com.example.coursework;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    public final int ROUND = 1;
    public final int SQUARE = 2;
    //saving the current data user has made, when the values of them updated, the view will also be
    //updated
    private MutableLiveData<Integer> color;
    private MutableLiveData<Integer> brushType;
    private MutableLiveData<Integer> BrushWidth;

    MutableLiveData<Integer> getBrushWidth() {
        if (BrushWidth == null) {
            BrushWidth = new MutableLiveData<>();
        }
        return BrushWidth;
    }
    MutableLiveData<Integer> getColor() {
        if (color == null) {
            color = new MutableLiveData<>();
        }
        return color;
    }
    MutableLiveData<Integer> getBrushType() {
        if (brushType == null) {
            brushType = new MutableLiveData<>();
        }
        return brushType;
    }

    public void setColor(int color) {
        getColor().postValue(color);
    }


    public void setBrushType(int brushType) {getBrushType().postValue(brushType);}

    public void setBrushWidth(int brushWidth) {getBrushWidth().postValue(brushWidth);}



}
