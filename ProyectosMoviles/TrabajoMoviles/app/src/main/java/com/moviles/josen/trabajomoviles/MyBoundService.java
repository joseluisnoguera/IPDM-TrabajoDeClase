package com.moviles.josen.trabajomoviles;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import java.util.Random;

public class MyBoundService extends Service {

    private static final int MAX_NUMBER_GENERATOR = 100;
    private final Random mRandomGenerator = new Random();
    public static final int GET_RANDOM = 1;

    final Messenger mMessenger = new Messenger(new IncomingHandler());

    public class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == GET_RANDOM) {
                try {
                    msg.replyTo.send(Message.obtain(null, GET_RANDOM, getRandomNumber(), 0));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    public int getRandomNumber() {
        return mRandomGenerator.nextInt(MAX_NUMBER_GENERATOR);
    }
}