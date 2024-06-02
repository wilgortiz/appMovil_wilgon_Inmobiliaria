package com.wilgon.appinmobiliariawilgon.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.wilgon.appinmobiliariawilgon.modelo.Pago;
import com.wilgon.appinmobiliariawilgon.request.ApiClient;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Pago>> mLista;

    public PagosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<Pago>> getmLista() {
        if(mLista == null){
            mLista = new MutableLiveData<>();
        }
        return mLista;
    }

    public void obtenerPagos(Bundle bundle){
        String token = ApiClient.leerToken(context);
        ApiClient.MisEndpoints api = ApiClient.getEndPoints();
        Call<List<Pago>> call = api.obtenerPagosPorContrato(token, bundle.getInt("id"));
        call.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                Log.d("salida", response.raw().toString());
                if(response.isSuccessful()){
                    mLista.postValue(response.body());
                } else{
                    Log.d("salida", response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                Log.d("salida", t.getMessage());
            }
        });
    }
}