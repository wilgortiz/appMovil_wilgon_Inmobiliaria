package com.wilgon.appinmobiliariawilgon.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wilgon.appinmobiliariawilgon.R;

public class RecibirEmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recibir_email);


        Button buttonLogin = findViewById(R.id.btInicioSesion);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecibirEmailActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}