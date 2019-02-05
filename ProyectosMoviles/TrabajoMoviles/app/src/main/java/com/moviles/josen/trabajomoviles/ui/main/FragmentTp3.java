package com.moviles.josen.trabajomoviles.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.moviles.josen.trabajomoviles.R;

import java.util.Locale;

public class FragmentTp3 extends Fragment {

    private FragmentTp3ViewModel mViewModel;

    private static final int zero = 0;

    private Button startBtn, stopBtn, resetBtn;
    private TextView counterView;
    private static AsyncTask<?,?,?> asyncTaskCounter = null;

    private String sharedPreferencesFile = "com.josen.android.timesharedpreferences";


    public static FragmentTp3 newInstance() {
        return new FragmentTp3();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tp3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Views
        if (getActivity() != null) {
            startBtn = getActivity().findViewById(R.id.startCounter);
            stopBtn = getActivity().findViewById(R.id.stopCounter);
            resetBtn = getActivity().findViewById(R.id.resetCounter);
            counterView = getActivity().findViewById(R.id.textCounter);
            //Recover data saved
            SharedPreferences mData = getActivity().getSharedPreferences(sharedPreferencesFile, Context.MODE_PRIVATE);
            mViewModel.setCounter_time(mData.getInt("time", 0));
            counterView.setText(String.format(Locale.ROOT, "%d", mViewModel.getCounter_time()));
        }

        //Listeners
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.setCounterActive(true);
                startBtn.setEnabled(false);
                stopBtn.setEnabled(true);
                resetBtn.setEnabled(false);
                startAsyncTaskCounter();
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.setCounterActive(false);
                startBtn.setEnabled(true);
                stopBtn.setEnabled(false);
                resetBtn.setEnabled(true);
                synchronized(getActivity()) {
                    getActivity().notifyAll();
                }
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.setCounterActive(false);
                startBtn.setEnabled(true);
                stopBtn.setEnabled(false);
                resetBtn.setEnabled(false);
                mViewModel.setCounter_time(zero);
                counterView.setText(String.format(Locale.ROOT,"%d",mViewModel.getCounter_time()));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //Recover or Start UI
        if (mViewModel.isCounterActive()) {
            startAsyncTaskCounter();
            startBtn.setEnabled(false);
            stopBtn.setEnabled(true);
            resetBtn.setEnabled(false);
        }
        else if (mViewModel.isStarted()){
            startBtn.setEnabled(true);
            stopBtn.setEnabled(false);
            resetBtn.setEnabled(true);
        } else {
            startBtn.setEnabled(true);
            stopBtn.setEnabled(false);
            resetBtn.setEnabled(false);
        }
    }

    private void startAsyncTaskCounter(){
        asyncTaskCounter = new AsyncTask<Object, Integer, Object>() {
            @Override
            protected Object doInBackground(Object... objects) {
                while (mViewModel.isCounterActive()){
                    try {
                        synchronized (getActivity()) {
                            getActivity().wait(1000);
                        }
                        if (!mViewModel.isCounterActive())
                            break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mViewModel.setCounter_time(mViewModel.getCounter_time()+1);
                    this.publishProgress(mViewModel.getCounter_time());
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... progress){
                counterView.setText(String.valueOf(progress[0]));
            }
        };
        asyncTaskCounter.execute();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(FragmentTp3ViewModel.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (asyncTaskCounter != null)
            asyncTaskCounter.cancel(true);
        FragmentActivity fa = getActivity();
        if (fa != null) {
            SharedPreferences mData = getActivity().getSharedPreferences(sharedPreferencesFile, Context.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = mData.edit();
            prefEditor.putInt("time", mViewModel.getCounter_time());
            prefEditor.apply();
        }
    }
}
