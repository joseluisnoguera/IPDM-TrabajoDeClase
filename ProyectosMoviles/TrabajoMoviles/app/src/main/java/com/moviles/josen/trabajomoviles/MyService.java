package com.moviles.josen.trabajomoviles;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;

import com.moviles.josen.trabajomoviles.ui.main.FragmentTp4Tp5;

public class MyService extends Service {

    public static final int ME = 2;

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Intent intent = (Intent) msg.obj;
            if (intent != null) {
                int iteration = intent.getIntExtra("ITERATION", -1);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent responseIntent = new Intent(FragmentTp4Tp5.RESPONSE_ACTION);
                responseIntent.putExtra(FragmentTp4Tp5.ITERATION, iteration);
                responseIntent.putExtra(FragmentTp4Tp5.WHO, ME);
                LocalBroadcastManager.getInstance(MyService.this).sendBroadcast(responseIntent);
            }
            stopSelfResult(msg.arg1);
        }
    }

    @Override
    public void onCreate(){
        HandlerThread thread = new HandlerThread("IterationCounterService");
        thread.start();
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.obj = intent;
        mServiceHandler.sendMessage(msg);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
