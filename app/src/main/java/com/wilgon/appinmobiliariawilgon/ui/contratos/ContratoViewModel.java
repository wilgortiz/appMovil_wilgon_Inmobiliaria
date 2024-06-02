package com.wilgon.appinmobiliariawilgon.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.wilgon.appinmobiliariawilgon.modelo.Contrato;
import com.wilgon.appinmobiliariawilgon.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Contrato> mContrato;

    public ContratoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Contrato> getMContrato() {
        if(mContrato == null){
            mContrato = new MutableLiveData<>();
        }
        return mContrato;
    }

    public void obtenerInquilino(Bundle bundle){
        String token = ApiClient.leerToken(context);
        ApiClient.MisEndpoints api = ApiClient.getEndPoints();
        Call<Contrato> call = api.obtenerContratoPorInmueble(token, Integer.parseInt(bundle.getString("id")));
        call.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(@NonNull Call<Contrato> call, @NonNull Response<Contrato> response) {
                Log.d("salida", response.raw().toString());
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mContrato.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Contrato> call, @NonNull Throwable t) {
                Toast.makeText(context, "Error al obtener usuario", Toast.LENGTH_SHORT).show();
            }
        });

    }
}