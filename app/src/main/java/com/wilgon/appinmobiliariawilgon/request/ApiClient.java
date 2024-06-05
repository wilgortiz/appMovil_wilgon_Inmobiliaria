package com.wilgon.appinmobiliariawilgon.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wilgon.appinmobiliariawilgon.modelo.CambiarClaveView;
import com.wilgon.appinmobiliariawilgon.modelo.Contrato;
import com.wilgon.appinmobiliariawilgon.modelo.Inmueble;
import com.wilgon.appinmobiliariawilgon.modelo.Inquilino;
import com.wilgon.appinmobiliariawilgon.modelo.Pago;
import com.wilgon.appinmobiliariawilgon.modelo.Propietario;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class ApiClient {

    //public static final String URLBASE = "http://192.168.1.103:5000/"; //notebook wifi


    //public static final String URLBASE = "http://192.168.0.6:5000/";

    //pc escritorio con wifi
    //public static final String URLBASE = "http://192.168.1.107:5000/";
    public static final String URLBASE = "http://192.168.1.104:5000/";
    private static MisEndpoints mep;


    public static MisEndpoints getEndPoints() {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                //conversion de json a objetos java
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mep = retrofit.create(MisEndpoints.class);
        return mep;
    }

    public interface MisEndpoints {

        @FormUrlEncoded
        @POST("Propietarios/Login")
        Call<String> login(@Field("Email") String usuario, @Field("Password") String password);

        @GET("Propietarios/Perfil")
        Call<Propietario> obtenerPerfil(@Header("Authorization") String token);

        //@PUT("Propietarios/Editar")
        //Call<Propietario> editarPerfil(@Header("Authorization") String token, @Body Propietario propietario);


        @PUT("Propietarios/EditarPerfil")
        Call<Propietario> editarPerfil(@Header("Authorization") String token, @Body Propietario propietario);


        @PUT("Propietarios/CambiarClave")
        Call<Void> cambiarClave(@Header("Authorization") String token, @Body CambiarClaveView cambiarClaveView);


        //inmuebles
        @GET("Inmuebles/Todos")
        Call<List<Inmueble>> obtenerInmuebles(@Header("Authorization") String token);

        @GET("Inmuebles/Obtener/{id}")
        Call<Inmueble> obtenerInmueble(@Header("Authorization") String token, @Path("id") int id);

        @PUT("Inmuebles/Cambiar_Estado/")
        Call<Inmueble> cambiarEstado(@Header("Authorization") String token, @Body Inmueble inmueble);

        @Multipart
        @POST("Inmuebles/Crear")
        Call<Inmueble> crearInmueble(@Header("Authorization") String token,
                                     @Part("Direccion") RequestBody direccion,
                                     @Part("Ambientes") RequestBody ambientes,
                                     @Part("Tipo") RequestBody tipo,
                                     @Part("Uso") RequestBody uso,
                                     @Part("Precio") RequestBody precio,
                                     @Part MultipartBody.Part imagen
        );

        @GET("Inmuebles/Alquilados")
        Call<List<Inmueble>> obtenerInmueblesAlquiladas(@Header("Authorization") String token);

        @GET("Inquilinos/Obtener/{id}")
        Call<Inquilino> obtenerInquilinoPorInmueble(@Header("Authorization") String token, @Path("id") int id);

        @GET("Contratos/Obtener/{id}")
        Call<Contrato> obtenerContratoPorInmueble(@Header("Authorization") String token, @Path("id") int id);

        @GET("Pagos/Obtener/{id}")
        Call<List<Pago>> obtenerPagosPorContrato(@Header("Authorization") String token, @Path("id") int id);


        @FormUrlEncoded
        @POST("Propietarios/olvidecontraseña")
        Call<Void> enviarEmail(@Field("email") String email);


    }


    public static void guardarToken(String token, Context contexto) {
        SharedPreferences sp = contexto.getSharedPreferences("token.xml", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
        //editor.commit();
    }


    public static String leerToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", 0);
        return sp.getString("token", "");
    }


    public static void borrarToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("token");
        editor.apply();
    }


}
