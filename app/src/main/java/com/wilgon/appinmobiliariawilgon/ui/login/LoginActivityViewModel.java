package com.wilgon.appinmobiliariawilgon.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.wilgon.appinmobiliariawilgon.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<String> mMensaje;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<String> getmMensaje() {
        if (mMensaje == null) {
            mMensaje = new MutableLiveData<>();
        }
        return mMensaje;
    }

    public void Logueo(String email, String password) {

        ApiClient.MisEndpoints api = ApiClient.getEndPoints();

        Call<String> call = api.login(email, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d("salida", response.body());


                    ApiClient.guardarToken("Bearer " + response.body(), context);
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Log.d("salida", "incorrecto");
                    // Log.d("salida", response.message());
                    Toast.makeText(context, "Error al intentar autenticar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("salida", "falla" + t.getMessage());
                //Log.d("salida", t.getMessage());
                Toast.makeText(context, "Error en la app", Toast.LENGTH_LONG).show();
            }
        });
    }



    private void guardarToken(String token){
        ApiClient.guardarToken(token,getApplication());

    }

}//fin
