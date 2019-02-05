package com.moviles.josen.trabajomoviles;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.moviles.josen.trabajomoviles.ui.main.FragmentTp4Tp5;

public class MyIntentService extends IntentService {
    public static final int ME = 1;

    public MyIntentService(){
        super("com.moviles.josen.trabajomoviles.MyIntentService");
    }

    protected void onHandleIntent(Intent intent){
        if (intent != null){
            int iteration = intent.getIntExtra("ITERATION", -1);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent responseIntent = new Intent(FragmentTp4Tp5.RESPONSE_ACTION);
            responseIntent.putExtra(FragmentTp4Tp5.ITERATION, iteration);
            responseIntent.putExtra(FragmentTp4Tp5.WHO, ME);
            LocalBroadcastManager.getInstance(MyIntentService.this).sendBroadcast(responseIntent);
        }
    }
}
