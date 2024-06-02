package com.wilgon.appinmobiliariawilgon.ui.login;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.wilgon.appinmobiliariawilgon.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginActivityViewModel mv;
    private ActivityLoginBinding binding;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private DetectorLlamada detectorLlamada;

    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        solicitarPermisos();

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginActivityViewModel.class);

        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mv.Logueo(
                        binding.etLoginPropietario.getText().toString(),
                        binding.etLoginPassword.getText().toString()
                );
            }
        });

        binding.tvOlvideClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ReseteoPasswordActivity.class);
                startActivity(intent);
            }
        });


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        detectorLlamada = new DetectorLlamada();
        detectorLlamada.setOnShakeListener(new DetectorLlamada.OnShakeListener() {
            @Override
            public void onShake(int count) {
                hacerLlamada();
            }
        });

    }


    private void hacerLlamada() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:2664213547"));
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permiso de llamada no otorgado", Toast.LENGTH_LONG).show();
            return;
        }
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(detectorLlamada, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(detectorLlamada);
    }


    public void solicitarPermisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                (checkSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        checkSelfPermission(ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)) {

            requestPermissions(new String[]{ACCESS_FINE_LOCATION,
                    ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CALL_PHONE}, 1000);
        }
    }


}
