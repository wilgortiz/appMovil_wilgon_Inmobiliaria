package com.wilgon.appinmobiliariawilgon.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.wilgon.appinmobiliariawilgon.R;
import com.wilgon.appinmobiliariawilgon.databinding.FragmentContratoBinding;
import com.wilgon.appinmobiliariawilgon.modelo.Contrato;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ContratoFragment extends Fragment {

    private FragmentContratoBinding binding;
    private ContratoViewModel mv;

    public static ContratoFragment newInstance() {
        return new ContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = new ViewModelProvider(this).get(ContratoViewModel.class);
        binding = FragmentContratoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mv.getMContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato c) {
                binding.etContratoCodigo.setText(String.valueOf(c.getId()));
                LocalDate fechaDesde = LocalDate.parse(c.getDesde(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                binding.etContratoFechaInicio.setText(fechaDesde.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                LocalDate fechaHasta = LocalDate.parse(c.getHasta(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                binding.etContratoFechaFin.setText(fechaHasta.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                binding.etContratoMonto.setText(String.valueOf(c.getValor()));

                binding.etContratoInquilino.setText(c.getInquilino().getNombre() + " " + c.getInquilino().getApellido());
                binding.etContratoDNIInquilino.setText(String.valueOf(c.getInquilino().getDni()));
                binding.etContratoInmueble.setText(c.getInmueble().getDireccion());
                binding.etContratoInmuebleId.setText(String.valueOf(c.getInmueble().getId()));
                binding.etContratoPagos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", c.getId());
                        Navigation.findNavController(v).navigate(R.id.pagosFragment, bundle);
                    }
                });
            }
        });
        mv.obtenerInquilino(getArguments());
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mv = new ViewModelProvider(this).get(ContratoViewModel.class);
        // TODO: Use the ViewModel
    }

}