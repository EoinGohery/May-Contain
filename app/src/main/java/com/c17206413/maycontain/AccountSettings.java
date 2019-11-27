package com.c17206413.maycontain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class AccountSettings extends AppCompatActivity {

    CheckBox check1, check2, check3;
    Button button_sel;
    private DocumentSnapshot userDoc;
    private Button changeLanguageButton;
    DocumentReference userIdRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        check1 = findViewById(R.id.nutAllergyCheck);
        check2 = findViewById(R.id.dairyAllergyCheck);
        check3 = findViewById(R.id.glutenAllergyCheck);
        button_sel = findViewById(R.id.saveBox);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userIdRef = db.collection("users").document(user.getUid());
        userIdRef.get().addOnCompleteListener(this, new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        userDoc =document;
                    } else {
                        recreate();
                    }
                }
            }
        });
        loadLocale();
        button_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check1.isChecked()) {
                    userIdRef.update("nuts", true);
                } else {
                    userIdRef.update("nuts", false);
                }
                if (check2.isChecked()) {
                    userIdRef.update("lactose", true);
                } else {
                    userIdRef.update("lactose", false);
                }
                if (check3.isChecked()) {
                    userIdRef.update("gluten", true);
                } else {
                    userIdRef.update("gluten", false);
                }
                finish();
            }

        });


        changeLanguageButton = findViewById(R.id.changeLanguageButton);
        changeLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });
    }


    private void showChangeLanguageDialog() {
        final String[] listItems = {"French", "English"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AccountSettings.this);
        mBuilder.setTitle("@string/chooseLang");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(i ==0) {
                    setLocale("fr");
                    recreate();
                }
                if(i ==1) {
                    setLocale("en");
                    recreate();
                }
                dialog.dismiss();
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        //userIdRef.update("language", lang);
    }

    private void loadLocale() {
        String language = "en";//userDoc.get("language").toString();
        setLocale(language);

    }
//    public void onCheckboxClicked(View view) {
//
//        boolean checked = ((CheckBox) view).isChecked();
//
//        // Check which checkbox was clicked
//        switch(view.getId()) {
//            case R.id.nutAllergyCheck:
//                userIdRef.update("nuts",checked);
//                break;
//            case R.id.dairyAllergyCheck:
//                userIdRef.update("lactose",checked);
//                break;
//            case R.id.glutenAllergyCheck:
//                userIdRef.update("gluten",checked);
//                break;
//        }
//    }
}
