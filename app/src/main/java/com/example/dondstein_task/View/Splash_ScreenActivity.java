package com.example.dondstein_task.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.dondstein_task.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class Splash_ScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


        if (isConnected()) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    initialPermission();
                }
            }, 3000);


        }else {

            AlertDialog.Builder aBuilder=new AlertDialog.Builder(Splash_ScreenActivity.this);
            aBuilder.setMessage("No Internet Connection")
                    .setCancelable(false)
                    .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            aBuilder.show();
        }
    }

    private void initialPermission() {

        Dexter.withActivity(Splash_ScreenActivity.this)
                .withPermissions("android.permission.INTERNET",
                        "android.permission.ACCESS_WIFI_STATE",
                        "android.permission.ACCESS_NETWORK_STATE",
                        "android.permission.ACCESS_COARSE_LOCATION",
                        "android.permission.ACCESS_FINE_LOCATION",
                        "android.permission.WAKE_LOCK",
                        "android.permission.READ_EXTERNAL_STORAGE",
                        "android.permission.WRITE_EXTERNAL_STORAGE",
                        "android.permission.CAMERA",
                        "android.permission.CALL_PHONE")
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        Intent intent = new Intent(Splash_ScreenActivity.this, HomePage_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {


                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    public boolean isConnected() {

        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return connected;
    }
}