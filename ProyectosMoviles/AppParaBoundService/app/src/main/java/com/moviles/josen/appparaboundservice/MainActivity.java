package com.moviles.josen.appparaboundservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static  final String BASE_BOUND_SERV_TEXT = "Último valor: ";
    private static final String PREV_KEY = "prev_random";
    public static final int GET_RANDOM = 1;

    private TextView randomText;
    private boolean mBound = false;

    Messenger mBoundService;
    private final Messenger mMessenger = new Messenger(new IncomingHandler());

    /******** Receptor de mensaje desde el servicio ********/
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == GET_RANDOM) {
                String txt = BASE_BOUND_SERV_TEXT + String.format(Locale.ROOT, "%s", msg.arg1);
                randomText.setText(txt);
            }

        }
    }
    /******************************************************/

    /******** Conexión al servicio ********/
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBoundService = new Messenger(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundService = null;
            mBound = false;
        }
    };
    /***********************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button generateRandom = findViewById(R.id.button);
        randomText = findViewById(R.id.textView);
        generateRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBound) {
                    Message msg = Message.obtain(null, GET_RANDOM, 0, 0);
                    msg.replyTo = mMessenger;
                    try {
                        mBoundService.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        if (savedInstanceState != null){
            String previous_random = savedInstanceState.getString(PREV_KEY, "-1");
            randomText.setText(previous_random);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //Bind
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.moviles.josen.trabajomoviles","com.moviles.josen.trabajomoviles.MyBoundService"));
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        //Unbind
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(PREV_KEY, randomText.getText().toString());
        super.onSaveInstanceState(outState);
    }
}