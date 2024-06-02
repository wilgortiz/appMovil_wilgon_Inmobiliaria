package com.wilgon.appinmobiliariawilgon.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.wilgon.appinmobiliariawilgon.modelo.Inmueble;
import com.wilgon.appinmobiliariawilgon.request.ApiClient;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratosViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Inmueble>> mLista;

    public ContratosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<Inmueble>> getmLista() {
        if(mLista == null){
            mLista = new MutableLiveData<>();
        }
        return mLista;
    }

    public void leerInmuebles(){
        String token = ApiClient.leerToken(context);
        ApiClient.MisEndpoints api= ApiClient.getEndPoints();
        Call<List<Inmueble>> call = api.obtenerInmueblesAlquiladas(token);
        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                Log.d("salida", response.raw().toString());
                if(response.isSuccessful()){
                    mLista.postValue(response.body());
                } else{
                    Log.d("salida", response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Log.d("salida", t.getMessage());
            }
        });
    }
}