package com.moviles.josen.trabajomoviles;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.moviles.josen.trabajomoviles.ui.main.FragmentTp1;
import com.moviles.josen.trabajomoviles.ui.main.FragmentTp2;
import com.moviles.josen.trabajomoviles.ui.main.FragmentTp3;
import com.moviles.josen.trabajomoviles.ui.main.FragmentTp4Tp5;


public class MainActivity extends AppCompatActivity {

    private final static String TEXT_HELP_KEY = "textHelp";
    private final static String LAST_FRAGMENT_KEY = "last_fragment_used";

    private TextView textHelper;
    private Fragment fgTp1;
    private final static int fragment_id_1 = 1;
    private Fragment fgTp2;
    private final static int fragment_id_2 = 2;
    private Fragment fgTp3;
    private final static int fragment_id_3 = 3;
    private Fragment fgTp4Tp5;
    private final static int fragment_id_4 = 4;
    private Fragment fragmentActive;
    private static int fragmentActive_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        textHelper = findViewById(R.id.textView);
        if (savedInstanceState != null) {
            textHelper.setText(savedInstanceState.getString(TEXT_HELP_KEY));
            fragmentActive_id = savedInstanceState.getInt(LAST_FRAGMENT_KEY);
            fgTp1 = getSupportFragmentManager().findFragmentByTag("1");
            fgTp2 = getSupportFragmentManager().findFragmentByTag("2");
            fgTp3 = getSupportFragmentManager().findFragmentByTag("3");
            fgTp4Tp5 = getSupportFragmentManager().findFragmentByTag("4");
            switch (fragmentActive_id){
                case fragment_id_1:
                    fragmentActive = fgTp1;
                    break;
                case fragment_id_2:
                    fragmentActive = fgTp2;
                    break;
                case fragment_id_3:
                    fragmentActive = fgTp3;
                    break;
                case fragment_id_4:
                    fragmentActive = fgTp4Tp5;
                    break;
            }
        } else {
            fgTp1 = FragmentTp1.newInstance();
            fgTp2 = FragmentTp2.newInstance();
            fgTp3 = FragmentTp3.newInstance();
            fgTp4Tp5 = FragmentTp4Tp5.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.container, fgTp4Tp5, "4")
                    .add(R.id.container, fgTp3, "3")
                    .add(R.id.container, fgTp2, "2")
                    .add(R.id.container, fgTp1, "1").commit();

            getSupportFragmentManager().beginTransaction().hide(fgTp4Tp5).hide(fgTp3).hide(fgTp2).show(fgTp1).commit();
            fragmentActive = fgTp1;
            fragmentActive_id = fragment_id_1;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment lastFragment = fragmentActive;
        int text_id = -1;
        switch (item.getItemId()) {
            case R.id.menuopt1:
                text_id = R.string.TextAyudaTp1;
                fragmentActive = fgTp1;
                fragmentActive_id = fragment_id_1;
                break;
            case R.id.menuopt2:
                text_id = R.string.TextAyudaTp2;
                fragmentActive = fgTp2;
                fragmentActive_id = fragment_id_2;
                break;
            case R.id.menuopt3:
                text_id = R.string.TextAyudaTp3;
                fragmentActive = fgTp3;
                fragmentActive_id = fragment_id_3;
                break;
            case R.id.menuopt4:
                text_id = R.string.TextAyudaTp4Tp5;
                fragmentActive = fgTp4Tp5;
                fragmentActive_id = fragment_id_4;
                break;
            default: //Nothing
        }
        if (lastFragment != fragmentActive) {
            textHelper.setText(text_id);
            getSupportFragmentManager().beginTransaction().show(fragmentActive).hide(lastFragment).commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXT_HELP_KEY, textHelper.getText().toString());
        outState.putInt(LAST_FRAGMENT_KEY, fragmentActive_id);
    }
}