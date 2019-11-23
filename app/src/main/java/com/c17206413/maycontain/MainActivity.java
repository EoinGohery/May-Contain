package com.c17206413.maycontain;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button logIn;
    private Button scanButton;
    private Button button;
    private TextView description;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        updateUI(0);
        logIn = findViewById(R.id.LoginButton);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OnLoginClick(view);
            }
        });

        scanButton = findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScannerActivity(v);
            }
        });
    }

    private void OnLoginClick(View v) {

        Intent intent = new Intent(this, GoogleSignInActivity.class);
        startActivity(intent);
        if (user!=null) {
            updateUI(1);
        }
    }

    public void openScannerActivity(View v) {
        //Intent intent = new Intent(this, Scanner.class);
        //startActivity(intent);


        //this is here just to test the update ui
        updateUI(3);
    }

    private void updateUI(int result) {
        TextView description = findViewById(R.id.description);
        ImageView image = findViewById(R.id.image);
        if (result == 0) { // login
            description.setText("Log in to Continue");

            findViewById(R.id.LoginButton).setVisibility(View.VISIBLE);
            findViewById(R.id.scanButton).setVisibility(View.GONE);
            image.setImageResource(R.drawable.common_google_signin_btn_icon_light_normal);
        } else if (result == 1) { // search
            description.setText(R.string.search);

            findViewById(R.id.LoginButton).setVisibility(View.GONE);
            findViewById(R.id.scanButton).setVisibility(View.VISIBLE);
            image.setImageResource(R.drawable.search_icon);
        } else if (result == 2) { // safe
            description.setText(R.string.this_product_is_safe_to_use);

            findViewById(R.id.LoginButton).setVisibility(View.GONE);
            findViewById(R.id.scanButton).setVisibility(View.VISIBLE);
            image.setImageResource(R.drawable.safe_tick);
        } else if (result == 3) { // unsafe
            description.setText("This product is not safe");

            findViewById(R.id.LoginButton).setVisibility(View.GONE);
            findViewById(R.id.scanButton).setVisibility(View.VISIBLE);
            image.setImageResource(R.drawable.x_mark);
        } else if (result == 4) { // unknown
            description.setText("Unknown Item");

            findViewById(R.id.LoginButton).setVisibility(View.GONE);
            findViewById(R.id.scanButton).setVisibility(View.VISIBLE);
            image.setImageResource(R.drawable.unknown_icon);
        }
    }
}