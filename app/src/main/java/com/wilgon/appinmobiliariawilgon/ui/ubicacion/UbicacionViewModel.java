package com.wilgon.appinmobiliariawilgon.ui.ubicacion;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class UbicacionViewModel extends AndroidViewModel {

    private MutableLiveData<MapaInmobiliaria> mapa;
    private static final LatLng COORDENADAS = new LatLng(-33.2891658454959, -66.2584516828081);

    public UbicacionViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<MapaInmobiliaria> getMapaInmobiliaria() {
        if (mapa == null) {
            mapa = new MutableLiveData<>();
        }
        return mapa;
    }

    public void obtenerMapa() {
        MapaInmobiliaria mapita = new MapaInmobiliaria();
        mapa.setValue(mapita);
    }

    public class MapaInmobiliaria implements OnMapReadyCallback {

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            try {
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                // controles de zoom
                googleMap.getUiSettings().setZoomControlsEnabled(true);

                googleMap.addMarker(new MarkerOptions().position(COORDENADAS).title("Inmobiliaria Wilgon"));
                CameraPosition campos = new CameraPosition.Builder()
                        .target(COORDENADAS)
                        .zoom(18)  //zoom inicial
                        .bearing(10)
                        .tilt(70)
                        .build();
                CameraUpdate update = CameraUpdateFactory.newCameraPosition(campos);
                googleMap.animateCamera(update);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("MapaInmobiliaria", "Error en la configuracion del mapa", e);
            }
        }
    }


}//fin