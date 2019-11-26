package com.c17206413.maycontain;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button logIn;
    private Button scanButton;
    private Button accountButton;
    private Button addButton;
    private TextView description;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private String currentDocRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        logIn = findViewById(R.id.LoginButton);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OnLoginClick(view);
            }
        });
        if (user==null) {
            updateUI(0);
        } else {
            updateUI(1);
        }
        scanButton = findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScannerActivity(v);
            }
        });

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        accountButton = findViewById(R.id.accountButton);
        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsActivity();
            }
        });
    }

    private void addProduct() {

        Intent intent = new Intent(this, AddProduct.class);
        intent.putExtra("EXTRA_DOC_REF_ID", currentDocRef);
        startActivity(intent);
        updateUI(1);
    }


    private void OnLoginClick(View v) {

            Intent intent = new Intent(this, GoogleSignInActivity.class);
            startActivityForResult(intent, 2);
    }

    public void openScannerActivity(View v) {
        Intent intent = new Intent(this, Scanner.class);
        startActivityForResult(intent, 1);
    }

    public void openSettingsActivity() {
        Intent intent = new Intent(this, AccountSettings.class);
        startActivity(intent);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String result=data.getStringExtra("result");
                currentDocRef =result;
                DocumentReference docIdRef = db.collection("products").document(result);
                docIdRef.get().addOnCompleteListener(this, new OnCompleteListener<DocumentSnapshot>() {
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                if (isSafe(document)) {
                                    updateUI(2);
                                } else {
                                    updateUI(3);
                                }
                            } else {
                                updateUI(4);
                            }
                        }
                    }
                });

            }
            if (resultCode == RESULT_CANCELED) {
               updateUI(1);
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                updateUI(1);
                user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    updateUI(1);
                }
            }
            if (resultCode == RESULT_CANCELED) {
                updateUI(0);
            }

        }
    }

    private boolean isSafe (DocumentSnapshot doc) {


        return true;
    }

    private void updateUI(int result) {
        TextView description = findViewById(R.id.description);
        ImageView image = findViewById(R.id.image);
        if (result == 0) { // login
            description.setText(R.string.desc_google_sign_in);

            findViewById(R.id.addButton).setVisibility(View.GONE);
            findViewById(R.id.LoginButton).setVisibility(View.VISIBLE);
            findViewById(R.id.scanButton).setVisibility(View.GONE);
            image.setImageResource(R.drawable.common_google_signin_btn_icon_light_normal);
        } else if (result == 1) { // search
            description.setText(R.string.search);

            findViewById(R.id.addButton).setVisibility(View.GONE);
            findViewById(R.id.LoginButton).setVisibility(View.GONE);
            findViewById(R.id.scanButton).setVisibility(View.VISIBLE);
            image.setImageResource(R.drawable.search_icon);
        } else if (result == 2) { // safe
            description.setText(R.string.this_product_is_safe_to_use);

            findViewById(R.id.addButton).setVisibility(View.GONE);
            findViewById(R.id.LoginButton).setVisibility(View.GONE);
            findViewById(R.id.scanButton).setVisibility(View.VISIBLE);
            image.setImageResource(R.drawable.safe_tick);
        } else if (result == 3) { // unsafe
            description.setText(R.string.this_product_is_not_safe);

            findViewById(R.id.addButton).setVisibility(View.GONE);
            findViewById(R.id.LoginButton).setVisibility(View.GONE);
            findViewById(R.id.scanButton).setVisibility(View.VISIBLE);
            image.setImageResource(R.drawable.x_mark);
        } else if (result == 4) { // unknown
            description.setText(R.string.unknown_item);

            findViewById(R.id.addButton).setVisibility(View.VISIBLE);
            findViewById(R.id.LoginButton).setVisibility(View.GONE);
            findViewById(R.id.scanButton).setVisibility(View.VISIBLE);
            image.setImageResource(R.drawable.unknown_icon);
        }
    }
}
