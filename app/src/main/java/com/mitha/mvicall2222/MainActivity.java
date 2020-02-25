package com.mitha.mvicall2222;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_succes_asctivity);
    }
    public void blink(){
        ImageView image = findViewById(R.id.imageView2);
        @SuppressLint("ResourceType") Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(),
                        R.animator.bounce);
        image.startAnimation(animation1);
    }
}
