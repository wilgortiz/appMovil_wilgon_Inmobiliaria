package com.wilgon.appinmobiliariawilgon.ui.login;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.wilgon.appinmobiliariawilgon.request.ApiClient;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReseteoPasswordViewModel extends AndroidViewModel {
    public ReseteoPasswordViewModel(@NonNull Application application) {
        super(application);
    }
    public void enviarEmail(String email){
        ApiClient.MisEndpoints api = ApiClient.getEndPoints();
        Call<Void> call = api.enviarEmail(email);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Intent intent = new Intent(getApplication(), RecibirEmailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Log.d("salida", "whatt1");
                    getApplication().startActivity(intent);
                    Log.d("salida", "whatt2");
                } else {
                    Log.d("salida", "Incorrecto");
                    // mutableMsgError.setValue("🚫Correo o contraseña incorrecta");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable throwable) {
                Log.d("salida", "Falla");
            }
        });
    }


}

