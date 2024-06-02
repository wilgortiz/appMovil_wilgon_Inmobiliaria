package com.wilgon.appinmobiliariawilgon.ui.ubicacion;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.wilgon.appinmobiliariawilgon.R;
import com.wilgon.appinmobiliariawilgon.databinding.FragmentUbicacionBinding;
import com.google.android.gms.maps.SupportMapFragment;


public class UbicacionFragment extends Fragment {

    private FragmentUbicacionBinding binding;
    private UbicacionViewModel ubicacionViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ubicacionViewModel = new ViewModelProvider(this).get(UbicacionViewModel.class);

        binding = FragmentUbicacionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ubicacionViewModel.getMapaInmobiliaria().observe(getViewLifecycleOwner(), new Observer<UbicacionViewModel.MapaInmobiliaria>() {
            @Override
            public void onChanged(UbicacionViewModel.MapaInmobiliaria mapaInmobiliaria) {
                SupportMapFragment smp = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                if (smp != null) {
                    smp.getMapAsync(mapaInmobiliaria);
                } else {
                    Log.d("UbicacionFragment", "SupportMapFragment is null");
                }
            }
        });
        ubicacionViewModel.obtenerMapa();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}