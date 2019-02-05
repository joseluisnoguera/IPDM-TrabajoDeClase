package com.moviles.josen.trabajomoviles.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

public class FragmentTp3ViewModel extends ViewModel {

    private int counter_time;
    private boolean counterActive;
    private static final byte zero = 0;

    public void setCounterActive(boolean counterActive) {
        this.counterActive = counterActive;
    }

    public boolean isCounterActive() {
        return counterActive;
    }

    public FragmentTp3ViewModel(){
        counter_time = zero;
        counterActive = false;
    }

    public int getCounter_time() {
        return counter_time;
    }

    public void setCounter_time(int counter_time) {
        this.counter_time = counter_time;
    }

    public boolean isStarted() { return counter_time != zero; }

}
