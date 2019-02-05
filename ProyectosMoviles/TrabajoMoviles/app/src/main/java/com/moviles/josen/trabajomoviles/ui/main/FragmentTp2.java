package com.moviles.josen.trabajomoviles.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.moviles.josen.trabajomoviles.Multiply_Activity;
import com.moviles.josen.trabajomoviles.R;

import java.util.Locale;

public class FragmentTp2 extends Fragment {

    private FragmentTp2ViewModel mViewModel;

    private static final int REQUEST_MULTIPLY = 1;
    private static final int REQUEST_CONTACT = 2;

    private Button contactBtn = null;
    private TextView resultText;
    private Button multiplyBtn = null;
    private TextView contactText;

    public static FragmentTp2 newInstance() {
        return new FragmentTp2();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tp2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            multiplyBtn = getActivity().findViewById(R.id.getMultiplication);
            resultText = getActivity().findViewById(R.id.textViewMultiplication);
            contactBtn = getActivity().findViewById(R.id.getContact);
            contactText = getActivity().findViewById(R.id.textViewContact);
        }

        //ViewModel
        resultText.setText(mViewModel.getResult());
        contactText.setText(mViewModel.getContact());

        //Listeners
        multiplyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Multiply_Activity.class);
                startActivityForResult(intent, REQUEST_MULTIPLY);
            }
        });
        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                intent.setDataAndType(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, REQUEST_CONTACT);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(FragmentTp2ViewModel.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == FragmentActivity.RESULT_OK){
            if (requestCode == REQUEST_MULTIPLY) {
                mViewModel.setResult(String.format(Locale.ROOT, "%g", data.getDoubleExtra("result", 0)));
                resultText.setText(mViewModel.getResult());
            }
            if (requestCode == REQUEST_CONTACT) {

                Uri uri_get_contact = data.getData();
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

                Cursor cursor = getContext().getContentResolver().query(uri_get_contact, projection, null, null, null);
                cursor.moveToFirst();
                int number_column = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);
                int name_column = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String number = cursor.getString(number_column);
                String name = cursor.getString(name_column);
                cursor.close();
                mViewModel.setContact(name + ": " + number);
                contactText.setText(mViewModel.getContact());
            }
        }
    }
}
