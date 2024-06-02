package com.wilgon.appinmobiliariawilgon.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import com.wilgon.appinmobiliariawilgon.R;
import com.wilgon.appinmobiliariawilgon.databinding.FragmentNuevoInmuebleBinding;
import com.wilgon.appinmobiliariawilgon.modelo.Inmueble;


public class NuevoInmuebleFragment extends Fragment {
    private FragmentNuevoInmuebleBinding binding;
    private NuevoInmuebleViewModel mv;
    private static final int PICK_IMAGE_REQUEST = 1;

    private Uri selectedImageUri;
    private Intent intent;
    private ActivityResultLauncher<Intent> arl;

    public static NuevoInmuebleFragment newInstance() {
        return new NuevoInmuebleFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openGallery();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = new ViewModelProvider(this).get(NuevoInmuebleViewModel.class);
        binding = FragmentNuevoInmuebleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Spinner ddUso = (Spinner) root.findViewById(R.id.spNuevoInmuebleUso);

        String[] itemsUso = new String[]{"Residencial", "Comercial"};
        ArrayAdapter<String> adapterUso = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, itemsUso);
        ddUso.setAdapter(adapterUso);

        Spinner ddTipo = (Spinner) root.findViewById(R.id.spNuevoInmuebleTipo);
        String[] itemsTipo = new String[]{"Casa", "Departamento", "Oficina", "Local", "Deposito"};

        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, itemsTipo);
        ddTipo.setAdapter(adapterTipo);

        mv.getMInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble i) {
                NavController navController = Navigation.findNavController(root);
                navController.navigate(R.id.action_nuevoInmuebleFragment_to_nav_inmuebles);
            }
        });

        binding.btNuevoInmuebleCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedImageUri != null) {
                    Inmueble inmueble = new Inmueble();
                    inmueble.setDireccion(binding.etNuevoInmuebleDireccion.getText().toString());
                    inmueble.setAmbientes(Integer.parseInt(binding.etNuevoInmuebleAmbientes.getText().toString()));
                    inmueble.setTipo(binding.spNuevoInmuebleTipo.getSelectedItem().toString());
                    inmueble.setUso(binding.spNuevoInmuebleUso.getSelectedItem().toString());
                    try {
                        inmueble.setPrecio(Double.parseDouble(binding.etNuevoInmueblePrecio.getText().toString()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return;
                    }

                    mv.crearInmueble(inmueble, selectedImageUri);
                } else {
                    Toast.makeText(getContext(), "Debe Seleccionar una imagen", Toast.LENGTH_LONG).show();
                }
            }
        });


        binding.ivNuevoInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void openGallery() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    selectedImageUri = data.getData();
                    binding.ivNuevoInmueble.setImageURI(selectedImageUri);
                }
            }
        });
    }
}