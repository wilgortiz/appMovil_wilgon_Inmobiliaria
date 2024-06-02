package com.wilgon.appinmobiliariawilgon.ui.inmuebles;

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

public class InmueblesViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Inmueble>> mLista;

    public InmueblesViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<Inmueble>> getmLista() {
        if (mLista == null) {
            mLista = new MutableLiveData<>();
        }
        return mLista;
    }

    public void leerInmuebles() {
        String token = ApiClient.leerToken(context);
        ApiClient.MisEndpoints api = ApiClient.getEndPoints();
        Call<List<Inmueble>> p = api.obtenerInmuebles(token);
        p.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                Log.d("salida", response.raw().toString());
                if (response.isSuccessful()) {
                    mLista.postValue(response.body());
                } else {
                    Log.d("salida", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Log.d("salida", t.getMessage());
            }
        });
    }

    public void NuevoInmueble() {

    }
}