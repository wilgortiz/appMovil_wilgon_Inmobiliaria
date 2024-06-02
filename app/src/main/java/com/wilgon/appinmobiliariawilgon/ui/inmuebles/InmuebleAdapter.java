package com.wilgon.appinmobiliariawilgon.ui.inmuebles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wilgon.appinmobiliariawilgon.R;
import com.wilgon.appinmobiliariawilgon.databinding.ItemInmuebleBinding;
import com.wilgon.appinmobiliariawilgon.modelo.Inmueble;
import com.wilgon.appinmobiliariawilgon.request.ApiClient;

import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.ViewHolder> {
    private List<Inmueble> inmuebles;
    private Context contexto;
    private LayoutInflater li;

    public InmuebleAdapter(List<Inmueble> inmuebles, Context contexto, LayoutInflater li) {
        this.inmuebles = inmuebles;
        this.contexto = contexto;
        this.li = li;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemInmuebleBinding binding = ItemInmuebleBinding.inflate(li, parent, false);
        return new ViewHolder(binding);
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Inmueble inmueble = inmuebles.get(position);
        //holder.binding.tvItemInmuebleDireccion.setText(inmueble.getDireccion());

        holder.binding.tvItemInmuebleDireccion.setText("dirección:\n" + inmueble.getDireccion());

        //holder.binding.tvItemInmueblePrecio.setText(inmuebles.get(position).getPrecioFormatted());
        holder.binding.tvItemInmueblePrecio.setText("$ " + Formateo.formatPrice(inmueble.getPrecio()));
        holder.binding.etItemInmuebleId.setText(String.valueOf(inmueble.getId()));
        holder.binding.tvItemInmuebleTipo.setText("tipo de inmueble:\n" + " " + String.valueOf(inmueble.getTipo()));
        String imagen = inmueble.getImagen().replace("\\", "/");
        String url = ApiClient.URLBASE + imagen;
        Glide.with(contexto)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.binding.ivItemInmueble);
    }


    @Override
    public int getItemCount() {
        return inmuebles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemInmuebleBinding binding;

        public ViewHolder(@NonNull ItemInmuebleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", binding.etItemInmuebleId.getText().toString());
                    Navigation.findNavController(view).navigate(R.id.inmuebleFragment, bundle);
                }
            });
        }
    }
}
