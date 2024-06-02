package com.wilgon.appinmobiliariawilgon.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wilgon.appinmobiliariawilgon.databinding.FragmentContratosBinding;

import com.wilgon.appinmobiliariawilgon.R;
import com.wilgon.appinmobiliariawilgon.modelo.Inmueble;

import java.util.List;

public class ContratosFragment extends Fragment {
    private ContratosViewModel mViewModel;
    private FragmentContratosBinding binding;

    public static ContratosFragment newInstance() {
        return new ContratosFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ContratosViewModel mv = new ViewModelProvider(this).get(ContratosViewModel.class);
        binding = FragmentContratosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mv.getmLista().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> listaActual) {
                RecyclerView rv = root.findViewById(R.id.rvContratos);
                GridLayoutManager glm = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                rv.setLayoutManager(glm);
                ContratoAdapter nad = new ContratoAdapter(listaActual, getContext(), getLayoutInflater());
                rv.setAdapter(nad);
            }
        });
        mv.leerInmuebles();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ContratosViewModel.class);
        // TODO: Use the ViewModel
    }

}