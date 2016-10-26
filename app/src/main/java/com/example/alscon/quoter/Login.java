package com.example.alscon.quoter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alscon.readtxt.R;

/**
 * Created by Alscon on 14.10.2016.
 */

public class Login extends Activity implements View.OnClickListener {
    private static final int REQUEST_WRITE_STORAGE = 112;
    private static String TAG = "PermissionDemo";
    Typeface mtypeface3;
    TextView tbtitle;
    Intent intent;
    private Button start, favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        isStoragePermissionGranted();
        start = (Button) findViewById(R.id.start);
        favorite = (Button) findViewById(R.id.favorite);
        tbtitle = (TextView) findViewById(R.id.toolbar_title2);
//        favorite = (Button)findViewById(R.id.favorite);
        start.setOnClickListener(this);
        favorite.setOnClickListener(this);
        fonts();
    }

    private void fonts() {
        mtypeface3 = Typeface.createFromAsset(getAssets(), "open-sans.extrabold.ttf");
        start.setTypeface(mtypeface3);
        favorite.setTypeface(mtypeface3);
        tbtitle.setTypeface(mtypeface3);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                startIntent();
                break;
            case R.id.favorite:
                favoriteIntent();
                break;
        }
    }


    private void startIntent() {
        intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);


    }


    private void favoriteIntent() {
        intent = new Intent(Login.this, Favorites.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        } else {
            Toast.makeText(Login.this, "Application does not correct", Toast.LENGTH_LONG).show();

        }


    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }
}
