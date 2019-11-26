package com.c17206413.maycontain;

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

import java.util.Locale;

public class AccountSettings extends AppCompatActivity {

    CheckBox check1, check2, check3;
    Button button_sel;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Button changeLanguageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        loadLocale();
        check1 = findViewById(R.id.nutAllergyCheck);
        check2 = findViewById(R.id.dairyAllergyCheck);
        check3 = findViewById(R.id.glutenAllergyCheck);
        button_sel = findViewById(R.id.saveBox);


        button_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "@string/SelectedAllergens";
                if (check1.isChecked() && preferences.getBoolean(getString(R.string.checked), false)) {
                    result += "@string/nutAllergy";
                }
                if (check2.isChecked() && preferences.getBoolean(getString(R.string.checked), false)) {
                    result += "@string/dairyAllergy";
                }
                if (check3.isChecked() && preferences.getBoolean(getString(R.string.checked), false)) {
                    result += "@string/gluten_Allergy";
                }

                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
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
                if(i ==0) {
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
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);

    }
    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();
        String str=" ";

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.nutAllergyCheck:
                str = checked?"@string/nutAllergySelected":"@string/nutAllergyDeselected";
                editor.putBoolean(getString(R.string.checked), true);
                editor.apply();
                break;
            case R.id.dairyAllergyCheck:
                str = checked?"@string/dairySelect":"@string/dairyDeselect";
                editor.putBoolean(getString(R.string.checked), true);
                editor.apply();
                break;
            case R.id.glutenAllergyCheck:
                str = checked?"@string/glutenSel":"@string/glutenDesel";
                editor.putBoolean(getString(R.string.checked), true);
                editor.apply();
                break;
        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
}
