package com.wilgon.appinmobiliariawilgon.ui.salir;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.wilgon.appinmobiliariawilgon.request.ApiClient;

public class Dialogo {


    /*
    //una forma
    public static void mostrarDialogo(Context context) {

            new AlertDialog.Builder(context)
                    .setTitle("Confirmar salida")
                    .setMessage("¿Desea salir? ")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("salir", ApiClient.leerToken(context.getApplicationContext()));

                            ApiClient.borrarToken(context);
                            //System.exit(0);
                            //requireActivity().finish();
                            Log.d("salir", ApiClient.leerToken(context.getApplicationContext()));

                         }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }





     */


    //otra forma
    public static void mostrarDialogo(Context context, final Activity activity) {
        new AlertDialog.Builder(context)
                .setTitle("Confirmar salida")
                .setMessage("¿Desea salir? ")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("salir", "......");

                        // Leer y mostrar el token actual
                        String token = ApiClient.leerToken(context);
                        //Toast.makeText(context, "Token actual: " + token, Toast.LENGTH_LONG).show();
                        Log.d("tokenn", token);


                        ApiClient.borrarToken(context);

                        // Intentar leer el token nuevamente para confirmar que ha sido borrado
                        String tokenDespues = ApiClient.leerToken(context);
                        Log.d("tokenn", tokenDespues);
                        //Toast.makeText(context, "Token FINAL: " + tokenDespues, Toast.LENGTH_LONG).show();

                        activity.finish();


//                            activity.finish();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // No hacer nada, solo cerrar el diálogo
                    }
                })
                .show();
    }


}//fin
