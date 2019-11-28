package com.c17206413.maycontain;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
        super.onCreate(savedInstanceState);scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        //checks to see if version is Marshmallow API 23 or higher
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
           if(checkPermission())
           {
               Toast.makeText(Scanner.this, R.string.permsG, Toast.LENGTH_LONG).show();
           }
           else {
            requestPermissions();
           }

        }
    }

    //checks permission from user to use camera.
    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(Scanner.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    //requests permission from user to access camera.
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,new String[] {CAMERA},REQUEST_CAMERA);
    }

    //passes requestcode and runs if permission is granted to use camera.
    public void onReqPermissionsResult(int requestCode, String [] permissions, int [] grantResults) {
        switch (requestCode)
        {
            case REQUEST_CAMERA:
                if(grantResults.length > 0)
                {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted) {
                        Toast.makeText(Scanner.this, R.string.permsG, Toast.LENGTH_LONG).show();

                    } else { // if permission isnt granted, displays message to request permission
                        Toast.makeText(Scanner.this, R.string.permsDenied, Toast.LENGTH_LONG).show();
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if(shouldShowRequestPermissionRationale(CAMERA)) {
                                displayAlertMessage(getString(R.string.accessPerms), new DialogInterface.OnClickListener() {
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

    // checks version and permissions on resume to app, requests permision if not granted
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

    // stops camera once scanner is used and app is closing
    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    // displays alert message of the scanned item with ok/cancel buttons
    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener){
            new AlertDialog.Builder(Scanner.this).setMessage(message).setPositiveButton(R.string.okCheck, listener)
                    .setNegativeButton(R.string.cancel, null).create().show();
    }

    // handles the result of the scan and passes the result as an intent
    @Override
    public void handleResult(Result result) {
        final String scanResult = result.getText();

        Intent returnIntent = new Intent();
        returnIntent.putExtra(getString(R.string.result),scanResult);
        setResult(RESULT_OK,returnIntent);
        finish();

    }

}

