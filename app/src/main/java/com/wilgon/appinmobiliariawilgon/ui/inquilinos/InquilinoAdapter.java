package com.wilgon.appinmobiliariawilgon.ui.inquilinos;

import android.annotation.SuppressLint;
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
import com.wilgon.appinmobiliariawilgon.modelo.Inquilino;
import com.wilgon.appinmobiliariawilgon.request.ApiClient;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class InquilinoAdapter extends RecyclerView.Adapter<InquilinoAdapter.ViewHolder> {
    private List<Inmueble> inmuebles;
    private Context contexto;
    private LayoutInflater li;

    public InquilinoAdapter(List<Inmueble> inmuebles, Context contexto, LayoutInflater li) {
        this.inmuebles = inmuebles;
        this.contexto = contexto;
        this.li = li;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = li.inflate(R.layout.item_inquilino, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.direccion.setText(inmuebles.get(position).getDireccion());
        holder.id.setText(String.valueOf(inmuebles.get(position).getId()));

        String imagen = inmuebles.get(position).getImagen().replace("\\","/");
        String url = ApiClient.URLBASE+imagen;
        Glide.with(contexto)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imagen);

    }
    @Override
    public int getItemCount() {
        return inmuebles.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView direccion;
        private EditText id;
        private ImageView imagen;
        private Button verInquilino;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.tvItemInquilinoDireccion);
            id = itemView.findViewById(R.id.etItemInquilinoInmuebleId);
            verInquilino = itemView.findViewById(R.id.btItemInquilinoVer);
            imagen = itemView.findViewById(R.id.ivItemInquilino);
            verInquilino.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", id.getText().toString());
                    Navigation.findNavController(view).navigate(R.id.inquilinoFragment, bundle);
                }
            });
        }
    }
}
