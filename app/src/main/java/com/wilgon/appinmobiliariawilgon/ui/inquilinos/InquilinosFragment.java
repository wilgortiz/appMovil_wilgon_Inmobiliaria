package com.wilgon.appinmobiliariawilgon.ui.inquilinos;

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

import com.wilgon.appinmobiliariawilgon.R;
import com.wilgon.appinmobiliariawilgon.databinding.FragmentInquilinosBinding;
import com.wilgon.appinmobiliariawilgon.modelo.Inmueble;


import java.util.List;

public class InquilinosFragment extends Fragment {
    InquilinosViewModel mv;
    private FragmentInquilinosBinding binding;
    public static InquilinosFragment newInstance() {
        return new InquilinosFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = new ViewModelProvider(this).get(InquilinosViewModel.class);
        binding = FragmentInquilinosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mv.getmLista().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> listaActual) {
                RecyclerView rv = root.findViewById(R.id.rvInquilinos);
                GridLayoutManager glm = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                rv.setLayoutManager(glm);
                InquilinoAdapter iad = new InquilinoAdapter(listaActual, getContext(), getLayoutInflater());
                rv.setAdapter(iad);
            }
        });
        mv.leerInmuebles();
        return root;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mv = new ViewModelProvider(this).get(InquilinosViewModel.class);
        // TODO: Use the ViewModel
    }
}