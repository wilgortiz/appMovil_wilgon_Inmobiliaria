package com.wilgon.appinmobiliariawilgon.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.wilgon.appinmobiliariawilgon.modelo.Inmueble;
import com.wilgon.appinmobiliariawilgon.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Inmueble> mInmueble;
    public InmuebleViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
    public LiveData<Inmueble> getMInmueble() {
        if(mInmueble == null){
            mInmueble = new MutableLiveData<>();
        }
        return mInmueble;
    }
    public void obtenerInmueble(Bundle bundle){
        String token = ApiClient.leerToken(context);
        ApiClient.MisEndpoints api = ApiClient.getEndPoints();
        Call<Inmueble> i = api.obtenerInmueble(token, Integer.parseInt(bundle.getString("id")));
        i.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                Log.d("salida", response.message());
                if(response.isSuccessful()){
                    mInmueble.postValue(response.body());;
                } else{
                    Log.d("salida", response.message());
                }
            }
            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Log.d("salida", t.getMessage());
            }
        });

    }
    public void actualizarInmueble(Inmueble i){
        String token = ApiClient.leerToken(context);
        ApiClient.MisEndpoints api = ApiClient.getEndPoints();
        Call<Inmueble> call = api.cambiarEstado(token, i);
        call.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(@NonNull Call<Inmueble> call, @NonNull Response<Inmueble> response) {
                Log.d("salida", response.raw().toString());
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mInmueble.postValue(response.body());
                        Toast.makeText(context, "Editado con exito", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Inmueble> call, @NonNull Throwable t) {
                Toast.makeText(context, "Error al obtener usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }
}