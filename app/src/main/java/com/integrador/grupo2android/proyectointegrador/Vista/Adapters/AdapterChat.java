package com.integrador.grupo2android.proyectointegrador.Vista.Adapters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Mensaje;
import com.integrador.grupo2android.proyectointegrador.R;
import java.util.ArrayList;
import java.util.List;

public class AdapterChat extends RecyclerView.Adapter {

    //DEFINIMOS MENSAJE RECIBIDO O ENVIADO EN CONSTANTES
    private static final Integer MENSAJE_ENVIADO  = 0;
    private static final Integer MENSAJE_RECIBIDO  = 1;

    private List<Mensaje> listadoDeMensajes;
    private AdapterChat.mensajeViewHolder mensajeViewHolder;
    private FirebaseUser user;

    public AdapterChat() {
        this.listadoDeMensajes = new ArrayList<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    //RECYCLERVIEW DE MENSAJES
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View celda;
        //DE ACUERDO AL MENSAJE, SE CARGA UNA CERGA U OTRA CELDA
        if(viewType == MENSAJE_ENVIADO){
            celda = layoutInflater.inflate(R.layout.celda_mensaje_enviado_chat,parent,false);
        } else {
            celda = layoutInflater.inflate(R.layout.celda_mensaje_recibido_chat,parent,false);
        }
        mensajeViewHolder = new AdapterChat.mensajeViewHolder(celda);
        return mensajeViewHolder;
    }

    //DETERMINAMOS POR ID, SI ES UN MENSAJE ENVIADO O RECIBIDO
    @Override
    public int getItemViewType(int position) {
        if(listadoDeMensajes.get(position).getNameUser().equals(user.getDisplayName())) {
            return MENSAJE_ENVIADO;
        }else {
            return MENSAJE_RECIBIDO;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Mensaje unMensaje = listadoDeMensajes.get(position);
        mensajeViewHolder mensajeViewHolder = (mensajeViewHolder) holder;
        mensajeViewHolder.cargarDatos(unMensaje);
    }

    @Override
    public int getItemCount() {
        return listadoDeMensajes.size();
    }

    public void agregarMensaje(Mensaje unMensaje){
        listadoDeMensajes.add(unMensaje);
        notifyItemInserted(listadoDeMensajes.size());
    }

    //AGREGAMOS Y LIMPIAMOS TODA LA LISTA
    public void agregarListaMensajes(List<Mensaje> listaMensaje){
        listadoDeMensajes.addAll(listaMensaje);
        notifyDataSetChanged();
    }

    private class mensajeViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewNameUserChat;
        private TextView textViewHoraClock;
        private TextView textViewMensajeChat;

        public mensajeViewHolder(View itemView) {
            super(itemView);
            textViewNameUserChat = itemView.findViewById(R.id.textViewNameUserChat);
            textViewHoraClock = itemView.findViewById(R.id.textViewClockMensaje);
            textViewMensajeChat = itemView.findViewById(R.id.textViewMensajeAEnviar);
        }

        public void cargarDatos(Mensaje unMensaje){
            textViewNameUserChat.setText(unMensaje.getNameUser());
            textViewHoraClock.setText(unMensaje.getClock());
            textViewMensajeChat.setText(unMensaje.getMensaje());
        }
    }
}
