package com.example.mascota;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageView imgMascota;
    Button btnCumplio, btnNoCumplio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgMascota = findViewById(R.id.imgMascota);
        btnCumplio = findViewById(R.id.btnCumplio);
        btnNoCumplio = findViewById(R.id.btnNoCumplio);

        btnCumplio.setOnClickListener(v -> {
            imgMascota.setImageResource(R.drawable.mascota_feliz);
        });

        btnNoCumplio.setOnClickListener(v -> {

            imgMascota.setImageResource(R.drawable.mascota_enojada);

            Animation shake =
                    AnimationUtils.loadAnimation(this, R.anim.shake);

            imgMascota.startAnimation(shake);
        });
    }
}