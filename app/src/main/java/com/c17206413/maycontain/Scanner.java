package com.c17206413.maycontain;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.*;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {


    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
       {
           if(checkPermission())
           {
               Toast.makeText(Scanner.this, "@string/permsG", Toast.LENGTH_LONG).show();
           }
           else {
            requestPermissions();
           }

       }
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(Scanner.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,new String[] {CAMERA},REQUEST_CAMERA);
    }


    public void onReqPermissionsResult(int requestCode, String [] permissions, int [] grantResults) {
        switch (requestCode)
        {
            case REQUEST_CAMERA:
                if(grantResults.length > 0)
                {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted) {
                        Toast.makeText(Scanner.this, "@string/permsG", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(Scanner.this, "@string/permsDenied", Toast.LENGTH_LONG).show();
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if(shouldShowRequestPermissionRationale(CAMERA)) {
                                displayAlertMessage("@string/accessPerms", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                        }
                                    }
                                });
                                return;
                            }
                        }
                    }

                }
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkPermission()) {
                if(scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();

            } else {
                requestPermissions();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener){
            new AlertDialog.Builder(Scanner.this).setMessage(message).setPositiveButton("@string/okCheck", listener)
                    .setNegativeButton("@string/cancel", null).create().show();
    }


    @Override
    public void handleResult(Result result) {
        final String scanResult = result.getText();

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",scanResult);
        setResult(RESULT_OK,returnIntent);
        finish();

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("@string/scanResult");
//        builder.setPositiveButton("@string/okCheck", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(scanResult));
//                startActivity(intent);
//            }
//        });
//        builder.setMessage(scanResult);
//        AlertDialog alert = builder.create();
//        alert.show();
    }

}

