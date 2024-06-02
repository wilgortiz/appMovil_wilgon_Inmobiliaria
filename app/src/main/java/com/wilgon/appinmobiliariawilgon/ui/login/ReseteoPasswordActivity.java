package com.wilgon.appinmobiliariawilgon.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.wilgon.appinmobiliariawilgon.databinding.ActivityReseteoPasswordBinding;
public class ReseteoPasswordActivity extends AppCompatActivity {






    ReseteoPasswordViewModel vm;
    ActivityReseteoPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm= ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ReseteoPasswordViewModel.class);
        binding=ActivityReseteoPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnRestablecerPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.enviarEmail(binding.etEmailRestablecer.getText().toString());
            }
        });



    }
}
