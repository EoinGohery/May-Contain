package com.c17206413.maycontain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.*;

public class AccountSettings extends AppCompatActivity {

    CheckBox check1, check2, check3;
    Button button_sel;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        check1 = findViewById(R.id.nutAllergyCheck);
        check2 = findViewById(R.id.dairyAllergyCheck);
        check3 = findViewById(R.id.glutenAllergyCheck);
        button_sel = findViewById(R.id.saveBox);


        button_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "@string/SelectedAllergens";
                if (check1.isChecked() &&preferences.getBoolean(getString(R.string.checked), false)) {
                    result += "@string/nutAllergy";
                }
                if (check2.isChecked() && preferences.getBoolean(getString(R.string.checked), false)) {
                    result += "@string/dairyAllergy";
                }
                if (check3.isChecked()&& preferences.getBoolean(getString(R.string.checked), false)) {
                    result += "@string/gluten_Allergy";
                }

                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                finish();
            }

        });

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
