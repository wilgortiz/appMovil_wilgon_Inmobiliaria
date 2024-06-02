package com.wilgon.appinmobiliariawilgon.ui.inmuebles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wilgon.appinmobiliariawilgon.R;
import com.wilgon.appinmobiliariawilgon.databinding.FragmentInmueblesBinding;
import com.wilgon.appinmobiliariawilgon.modelo.Inmueble;

import java.util.List;

public class InmueblesFragment extends Fragment {
    private InmueblesViewModel mViewModel;
    private FragmentInmueblesBinding binding;

    public static InmueblesFragment newInstance() {
        return new InmueblesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        InmueblesViewModel mv = new ViewModelProvider(this).get(InmueblesViewModel.class);
        binding = FragmentInmueblesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.fbNuevoInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_nav_inmuebles_to_nuevoInmuebleFragment);
            }
        });
        mv.getmLista().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> listaActual) {
                RecyclerView rv = root.findViewById(R.id.rvInmuebles);
                GridLayoutManager glm = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                rv.setLayoutManager(glm);
                InmuebleAdapter iad = new InmuebleAdapter(listaActual, getContext(), getLayoutInflater());
                rv.setAdapter(iad);
            }
        });
        mv.leerInmuebles();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InmueblesViewModel.class);
        // TODO: Use the ViewModel
    }
}