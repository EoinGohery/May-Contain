package com.c17206413.maycontain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Unsafe extends AppCompatActivity {
    private Button goToSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        goToSearch = findViewById(R.id.returnToSearchFromUnsafe);
        goToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSearchActivity();
            }
        });
    }


    public void goToSearchActivity() {
        Intent intent = new Intent(this,Search.class);
        startActivity(intent);

    }
}