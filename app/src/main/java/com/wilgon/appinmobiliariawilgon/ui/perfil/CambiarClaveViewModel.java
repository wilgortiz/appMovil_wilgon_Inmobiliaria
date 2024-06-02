package com.wilgon.appinmobiliariawilgon.ui.perfil;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.wilgon.appinmobiliariawilgon.modelo.CambiarClaveView;
import com.wilgon.appinmobiliariawilgon.request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarClaveViewModel extends AndroidViewModel {

    private MutableLiveData<String> errorMessage;
    private MutableLiveData<Boolean> success;

    public CambiarClaveViewModel(@NonNull Application application) {
        super(application);
        errorMessage = new MutableLiveData<>();
        success = new MutableLiveData<>();
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<Boolean> getSuccess() {
        return success;
    }

    public void cambiarClave(String token, String claveActual, String claveNueva) {
        if (claveActual.equals(claveNueva)) {
            errorMessage.setValue("La nueva clave debe ser diferente de la actual");
            return;
        }

        CambiarClaveView cambioClave = new  CambiarClaveView(claveActual, claveNueva);
        Call<Void> call = ApiClient.getEndPoints().cambiarClave(token, cambioClave);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    success.setValue(true);
                } else {
                    errorMessage.setValue("Error al cambiar la clave");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                errorMessage.setValue("Error de conexión: " + t.getMessage());
            }
        });
    }
}


