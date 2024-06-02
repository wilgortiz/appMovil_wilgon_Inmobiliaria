package com.wilgon.appinmobiliariawilgon.ui.salir;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.wilgon.appinmobiliariawilgon.R;
import com.wilgon.appinmobiliariawilgon.databinding.FragmentSalirBinding;

public class SalirFragment extends Fragment {


    private FragmentSalirBinding binding;

    //forma 1
    /*
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSalirBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Dialogo.mostrarDialogo(getContext());
        return root;
    }
*/

    //forma 2
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSalirBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Mostrar el diálogo al iniciar el fragmento
        Dialogo.mostrarDialogo(getContext(), getActivity());

        return root;
    }


    private void mostrarDialogo() {
        Log.d("salir", "entra al metodo");
        Dialogo.mostrarDialogo(getContext(), getActivity());

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}





