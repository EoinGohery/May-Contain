package com.example.appshell;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class AccountSettings extends AppCompatActivity {

    CheckBox check1, check2, check3;
    Button button_sel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        check1 = findViewById(R.id.nutAllergyCheck);
        check2 = findViewById(R.id.dairyAllergyCheck);
        check3 = findViewById(R.id.shellfishAllergyCheck);
        button_sel = findViewById(R.id.acceptClickBox);

        button_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "@string/SelectedAllergens";
                if (check1.isChecked()) {
                    result += "@string/nutAllergy";
                }
                if (check2.isChecked()) {
                    result += "@string/dairyAllergy";
                }
                if (check3.isChecked()) {
                    result += "@string/shellfishAllergy";
                }

                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }

    });

    }
    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.nutAllergyCheck:
                str = checked?"@string/nutAllergySelected":"@string/nutAllergyDeselected";
                break;
            case R.id.dairyAllergyCheck:
                str = checked?"@string/dairySelect":"@string/dairyDeselect";
                break;
            case R.id.shellfishAllergyCheck:
                str = checked?"@string/shellfishSel":"@string/shellfishDesel";
                break;

        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();



    }

}
