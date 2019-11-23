package com.example.appshell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Safe extends AppCompatActivity {
    private Button previous;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        previous = findViewById(R.id.returnToSearchFromSafe);
        previous.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openSearchActivity();
            }
        });

    }
    public void openSearchActivity() {
        Intent intent = new Intent(this,Search.class);
        startActivity(intent);
    }
}

