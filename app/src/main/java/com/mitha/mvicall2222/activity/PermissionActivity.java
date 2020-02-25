package com.mitha.mvicall2222.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mitha.mvicall2222.MainActivity;
import com.mitha.mvicall2222.R;

public class PermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

         Button lanjut =findViewById(R.id.btnLanjut);
         lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PermissionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
