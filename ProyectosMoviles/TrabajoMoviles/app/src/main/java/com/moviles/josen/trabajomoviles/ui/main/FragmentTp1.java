package com.moviles.josen.trabajomoviles.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.moviles.josen.trabajomoviles.R;

public class FragmentTp1 extends Fragment {

    private static final String TAG = FragmentTp1.class.getCanonicalName();

    public static FragmentTp1 newInstance() {
        return new FragmentTp1();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tp1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            Button buttonLog = getActivity().findViewById(R.id.buttonLog);
            Button buttonToast = getActivity().findViewById(R.id.buttonToast);
            buttonLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "El usuario hizo click");
                }
            });
            buttonToast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Wizard Toast here!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
