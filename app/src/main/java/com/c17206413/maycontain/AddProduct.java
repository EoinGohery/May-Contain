package com.c17206413.maycontain;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.firestore.FirebaseFirestore;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.EditText;

public class AddProduct extends AppCompatActivity {

    CheckBox check1, check2, check3;
    Button button_sel;

    private FirebaseFirestore db;
    private boolean productGluten =false;
    private boolean productLactose =false;
    private boolean productNuts =false;
    private String productName ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);

        db = FirebaseFirestore.getInstance();

        check1 = findViewById(R.id.nutAllergyCheck);
        check2 = findViewById(R.id.dairyAllergyCheck);
        check3 = findViewById(R.id.glutenAllergyCheck);
        button_sel = findViewById(R.id.saveBox);

        button_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mEdit;
                Map<String, Object> product = new HashMap<>();
                mEdit = findViewById(R.id.nameTextBox);
                productName = mEdit.getText().toString();
                product.put("gluten", productGluten);
                product.put("lactose", productLactose);
                product.put("nuts", productNuts);
                product.put("name", productName);
                db.collection("products").document(getIntent().getStringExtra("EXTRA_DOC_REF_ID")).set(product);
                finish();
            }

        });

    }

    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.nutAllergyCheck:
                productNuts = checked;
                break;
            case R.id.dairyAllergyCheck:
                productLactose = checked;
                break;
            case R.id.glutenAllergyCheck:
                productGluten = checked;
                break;

        }
    }
}