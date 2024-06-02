package com.wilgon.appinmobiliariawilgon.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.wilgon.appinmobiliariawilgon.ui.login.LoginActivity;
import com.wilgon.appinmobiliariawilgon.modelo.Propietario;
import com.wilgon.appinmobiliariawilgon.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Propietario> mPropietario;
    private MutableLiveData<Boolean> esEditable;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Propietario> getMPropietario() {
        if(mPropietario == null){
            mPropietario = new MutableLiveData<>();
        }
        return mPropietario;
    }

    public LiveData<Boolean> getMEsEditable() {
        if (esEditable == null) {
            esEditable = new MutableLiveData<>();
            esEditable.setValue(false);
        }
        return esEditable;
    }

    public void LeerUsuario(){
        String token = ApiClient.leerToken(context);
        ApiClient.MisEndpoints api = ApiClient.getEndPoints();
        Call<Propietario> p = api.obtenerPerfil(token);
        p.enqueue(new Callback<Propietario>() {
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

    public void CambiarEstadoEdicion(){
        esEditable.setValue(!esEditable.getValue());
    }

    public void GuardarPropietario(Propietario p){


        String token = ApiClient.leerToken(context);
        if (token.isEmpty()) {
            Log.d("salida", "Token inexistente");
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            return;
        }

        ApiClient.MisEndpoints api= ApiClient.getEndPoints();
        Call<Propietario> call = api.editarPerfil(token, p);
        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(@NonNull Call<Propietario> call, @NonNull Response<Propietario> response) {
                Log.d("salida", response.raw().toString());
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mPropietario.setValue(p);
                        Toast.makeText(context, "Perfil editado correctamente", Toast.LENGTH_LONG).show();
                        CambiarEstadoEdicion();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Propietario> call, @NonNull Throwable t) {
                Toast.makeText(context, "Error al obtener usuario", Toast.LENGTH_LONG).show();
            }
        });

    }

}







