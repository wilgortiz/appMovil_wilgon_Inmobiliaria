
/*
package com.wilgon.appinmobiliariawilgon.ui.perfil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wilgon.appinmobiliariawilgon.R;
import com.wilgon.appinmobiliariawilgon.databinding.FragmentCambiarClaveBinding;

public class CambiarClaveFragment extends Fragment {

    private CambiarClaveViewModel mViewModel;
    private FragmentCambiarClaveBinding binding;

    public static CambiarClaveFragment newInstance() {
        return new CambiarClaveFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= FragmentCambiarClaveBinding.inflate(inflater,container,false);
        View root= binding.getRoot();
        inicializar(root);
        return root;
    }

    private void inicializar(View root) {
        mViewModel = new ViewModelProvider(this).get(CambiarClaveViewModel.class);
        mViewModel.getError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrores.setText(s);
                binding.tvErrores.setVisibility(View.VISIBLE);
            }
        });
        mViewModel.getExito().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment_content_main).
                        navigate(R.id.nav_perfil);
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
            }
        });
        binding.btCambiarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvErrores.setText("");
                binding.tvErrores.setVisibility(View.VISIBLE);
                String actual= binding.etClaveActual.getText().toString();
                String nueva= binding.etClaveNueva.getText().toString();
                mViewModel.editarClave(actual, nueva);
            }
        });

    }

}
*/













package com.wilgon.appinmobiliariawilgon.ui.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.wilgon.appinmobiliariawilgon.ui.login.LoginActivity;
import com.wilgon.appinmobiliariawilgon.R;
import com.wilgon.appinmobiliariawilgon.request.ApiClient;

public class CambiarClaveFragment extends Fragment {

    private CambiarClaveViewModel viewModel;
    private EditText etClaveActual, etClaveNueva;
    private TextView tvErrores;
    private Button btCambiarClave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cambiar_clave, container, false);
        etClaveActual = view.findViewById(R.id.etClaveActual);
        etClaveNueva = view.findViewById(R.id.etClaveNueva);
        tvErrores = view.findViewById(R.id.tvErrores);
        btCambiarClave = view.findViewById(R.id.btCambiarClave);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(CambiarClaveViewModel.class);




        btCambiarClave.setOnClickListener(v -> {
            String claveActual = etClaveActual.getText().toString();
            String claveNueva = etClaveNueva.getText().toString();
            String token = ApiClient.leerToken(getContext());
            viewModel.cambiarClave(token, claveActual, claveNueva);
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                tvErrores.setText(errorMessage);
                tvErrores.setVisibility(View.VISIBLE);
            } else {
                tvErrores.setVisibility(View.GONE);
            }
        });

        viewModel.getSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success != null && success) {

                // Cerrar sesión NUEVO
                cerrarSesion();
                tvErrores.setText("Clave cambiada con éxito");
                tvErrores.setVisibility(View.VISIBLE);
            }
        });
    }






    private void cerrarSesion() {
        String mensaje = "Clave cambiada con éxito. Será redirigido al inicio de sesión.";
        tvErrores.setText(mensaje);
        tvErrores.setVisibility(View.VISIBLE);

        Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();

        new Handler().postDelayed(() -> {
            // Eliminar el token guardado después del retraso
            ApiClient.borrarToken(getContext());

            // Redirigir al usuario a la pantalla de inicio de sesión
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();
        }, 2000); // Retraso de 2 segundos (2000 milisegundos)
    }










}//fin
