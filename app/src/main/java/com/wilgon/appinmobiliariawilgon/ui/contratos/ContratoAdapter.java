package com.wilgon.appinmobiliariawilgon.ui.contratos;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.wilgon.appinmobiliariawilgon.R;
import com.wilgon.appinmobiliariawilgon.modelo.Inmueble;
import com.wilgon.appinmobiliariawilgon.request.ApiClient;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ViewHolder> {
    private List<Inmueble> inmuebles;
    private Context contexto;
    private LayoutInflater li;

    public ContratoAdapter(List<Inmueble> inmuebles, Context contexto, LayoutInflater li) {
        this.inmuebles = inmuebles;
        this.contexto = contexto;
        this.li = li;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = li.inflate(R.layout.item_contrato, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.direccion.setText("Dirección:\n" + inmuebles.get(position).getDireccion());
        holder.tipoI.setText("Tipo de inmueble:\n" + inmuebles.get(position).getTipo());
        holder.id.setText(String.valueOf(inmuebles.get(position).getId()));
        String imagen = inmuebles.get(position).getImagen().replace("\\", "/");
        String url = ApiClient.URLBASE + imagen;
        Glide.with(contexto)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return inmuebles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView direccion;
        private EditText id;
        private ImageView imagen;
        private TextView tipoI;
        private Button ver;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.tvItemContratoDireccion);
            id = itemView.findViewById(R.id.etItemContratoInmuebleId);
            ver = itemView.findViewById(R.id.btItemContratoVer);
            imagen = itemView.findViewById(R.id.ivItemContrato);
            tipoI=itemView.findViewById(R.id.tvItemContratoTipo);
            ver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", id.getText().toString());
                    Navigation.findNavController(view).navigate(R.id.contratoFragment, bundle);

                }
            });
        }
    }
}
