package com.wilgon.appinmobiliariawilgon.ui.login;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.wilgon.appinmobiliariawilgon.modelo.Propietario;
import com.wilgon.appinmobiliariawilgon.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Propietario> mPropietario;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Propietario> getMPropietario() {
        if(mPropietario == null){
            mPropietario = new MutableLiveData<>();
        }
        return mPropietario;
    }

    public void leerUsuario() {
        String token = ApiClient.leerToken(context);
        ApiClient.MisEndpoints endpoint = ApiClient.getEndPoints();
        Call<Propietario> call = endpoint.obtenerPerfil(token);
        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if(response.isSuccessful()){
                    mPropietario.postValue(response.body());
                } else{
                    Log.d("salida", response.message());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Log.d("salida", t.getMessage());
            }
        });
    }
}
