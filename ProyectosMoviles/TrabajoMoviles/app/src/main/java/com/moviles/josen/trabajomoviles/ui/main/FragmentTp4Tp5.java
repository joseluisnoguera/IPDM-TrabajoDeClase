package com.moviles.josen.trabajomoviles.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.moviles.josen.trabajomoviles.MyBoundService;
import com.moviles.josen.trabajomoviles.MyIntentService;
import com.moviles.josen.trabajomoviles.MyService;
import com.moviles.josen.trabajomoviles.R;

import java.util.Locale;

public class FragmentTp4Tp5 extends Fragment {

    private FragmentTp4Tp5ViewModel mViewModel;

    public static final String RESPONSE_ACTION = "com.josen.android.RESPONSE_ACTION";
    public static final String ITERATION = "ITERATION";
    public static final String WHO = "WHO";
    private static final int MAX_ITERATION = 4;
    private static  final String BASE_BOUND_SERV_TEXT = "Último valor: ";
    private TextView serviceText = null;
    private TextView intentServiceText = null;
    private TextView boundServiceText = null;
    private Button serviceBtn = null;
    private Button intentServiceBtn = null;
    private Button boundServiceBtn = null;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent data) {
            int who = data.getIntExtra(WHO, -1);
            if (intentServiceText != null && serviceText != null)
                switch (who){
                    case MyIntentService.ME:
                        mViewModel.setIntentServiceText(String.format(Locale.ROOT, "%s", data.getIntExtra(ITERATION, -1)));
                        intentServiceText.setText(mViewModel.getIntentServiceText());
                        break;
                    case MyService.ME:
                        mViewModel.setServiceText(String.format(Locale.ROOT, "%s", data.getIntExtra(ITERATION, -1)));
                        serviceText.setText(mViewModel.getServiceText());
                        break;
                }
        }
    };
    Messenger mBoundService;
    private final Messenger mMessenger = new Messenger(new IncomingHandler());

    /******** Receptor de mensaje desde el servicio ********/
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MyBoundService.GET_RANDOM && boundServiceText != null)
                mViewModel.setLastNumberFromService(msg.arg1);
                String txt = BASE_BOUND_SERV_TEXT + String.format(Locale.ROOT, "%s", mViewModel.getLastNumberFromService());
                boundServiceText.setText(txt);
            }
    }


    /******** Conexión al servicio ********/
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBoundService = new Messenger(service);
            mViewModel.setBoundServiceConnected(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundService = null;
            mViewModel.setBoundServiceConnected(false);
        }
    };

    public static FragmentTp4Tp5 newInstance() {
        return new FragmentTp4Tp5();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tp4_tp5, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FragmentTp4Tp5ViewModel.class);

        //Views
        if (getActivity() != null) {
            serviceBtn = getActivity().findViewById(R.id.serviceBtn);
            intentServiceBtn = getActivity().findViewById(R.id.intentServiceBtn);
            boundServiceBtn = getActivity().findViewById(R.id.boundServiceBtn);
            serviceText = getActivity().findViewById(R.id.textService);
            intentServiceText = getActivity().findViewById(R.id.textIntentService);
            serviceText.setText(mViewModel.getServiceText());
            intentServiceText.setText(mViewModel.getIntentServiceText());
            boundServiceText = getActivity().findViewById(R.id.textBoundService);
            String txt = BASE_BOUND_SERV_TEXT + String.format(Locale.ROOT, "%s", mViewModel.getLastNumberFromService());
            boundServiceText.setText(txt);
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, new IntentFilter(RESPONSE_ACTION));
        }
        serviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 1; i <= MAX_ITERATION; i++){
                    Intent service = new Intent(getActivity(), MyService.class);
                    service.putExtra(ITERATION, i);
                    getActivity().startService(service);
                }
            }
        });

        intentServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 1; i <= MAX_ITERATION; i++){
                    Intent intentService = new Intent(getActivity(), MyIntentService.class);
                    intentService.putExtra(ITERATION, i);
                    getActivity().startService(intentService);
                }
            }
        });

        boundServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewModel.isBoundServiceConnected()) {
                    Message msg = Message.obtain(null, MyBoundService.GET_RANDOM, 0, 0);
                    msg.replyTo = mMessenger;
                    try {
                        mBoundService.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        //Bind
        FragmentActivity fa = getActivity();
        if (fa != null) {
            Intent intent = new Intent(getActivity(), MyBoundService.class);
            getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
         //Unbind
        if (mViewModel.isBoundServiceConnected() && getActivity() != null) {
            getActivity().unbindService(mConnection);
            mViewModel.setBoundServiceConnected(false);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (getActivity() != null)
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
