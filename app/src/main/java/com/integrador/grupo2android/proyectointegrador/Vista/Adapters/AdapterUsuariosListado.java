package com.integrador.grupo2android.proyectointegrador.Vista.Adapters;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Usuario;
import com.integrador.grupo2android.proyectointegrador.R;
import java.util.ArrayList;
import java.util.List;

public class AdapterUsuariosListado extends RecyclerView.Adapter {
    private List<Usuario> listaDeUsuarios;
    private Context context;

    public AdapterUsuariosListado(Context context) {
        this.listaDeUsuarios = new ArrayList<>();
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View celda = layoutInflater.inflate(R.layout.celda_usuarios, parent, false);
        UsuarioViewholder usuarioViewholder = new UsuarioViewholder(celda);
        return usuarioViewholder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Usuario usuario = listaDeUsuarios.get(position);
        UsuarioViewholder usuarioViewholder = (UsuarioViewholder) holder;
        usuarioViewholder.asignarUsuario(usuario);
    }

    @Override
    public int getItemCount() {
        return listaDeUsuarios.size();
    }

    public void setUsuarios(List<Usuario> listaDeUsuariosNuevos) {
        this.listaDeUsuarios.clear();
        if (listaDeUsuariosNuevos != null) {
            this.listaDeUsuarios.addAll(listaDeUsuariosNuevos);
        } else {
            Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
        }
        notifyDataSetChanged();
    }

    private class UsuarioViewholder extends RecyclerView.ViewHolder {
        private ImageView imageViewUsuario;
        private TextView textViewNombre;

        public UsuarioViewholder(View itemView) {
            super(itemView);
            imageViewUsuario = itemView.findViewById(R.id.imageViewFoto);
            textViewNombre = itemView.findViewById(R.id.textViewTexto);
        }

        public void asignarUsuario(Usuario usuario) {
            Glide.with(context).load(usuario.getFoto()).into(imageViewUsuario);
            textViewNombre.setText(usuario.getNombre());
        }
    }
}