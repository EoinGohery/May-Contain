package com.c17206413.maycontain;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Button changeLanguageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_account_settings);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        check1 = findViewById(R.id.nutAllergyCheck);
        check2 = findViewById(R.id.dairyAllergyCheck);
        check3 = findViewById(R.id.glutenAllergyCheck);
        button_sel = findViewById(R.id.saveBox);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(getResources().getString(R.string.app_name));


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
        final String[] listItems = {"@string/French_box", "@string/English_box"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AccountSettings.this);
        mBuilder.setTitle(getString(R.string.chooseLang));
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(i ==0) {
                    setLocale("fr");
                    Toast.makeText(AccountSettings.this, R.string.eng_sel, Toast.LENGTH_SHORT).show();
                    recreate();
                }else if(i ==1) {
                    setLocale("en");
                    Toast.makeText(AccountSettings.this, R.string.fr_sel, Toast.LENGTH_SHORT).show();
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
        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.Settings), MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
        // not sure if it connects properly to mainactivity
        Intent refresh = new Intent(this,MainActivity.class);
        startActivity(refresh);
        finish();
    }
    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences(getString(R.string.Settings), Activity.MODE_PRIVATE);
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
