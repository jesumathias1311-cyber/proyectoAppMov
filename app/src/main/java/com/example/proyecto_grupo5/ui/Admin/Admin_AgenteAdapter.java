package com.example.proyecto_grupo5.ui.Admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_grupo5.R;

import java.util.ArrayList;

public class Admin_AgenteAdapter extends RecyclerView.Adapter<Admin_AgenteAdapter.ViewHolder> {
    private ArrayList<Admin_ModeloAgente> lista;

    public Admin_AgenteAdapter(ArrayList<Admin_ModeloAgente> lista) {
        this.lista = lista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_agente, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Admin_ModeloAgente agente = lista.get(position);
        holder.tvNombre.setText(agente.getNombre());
        holder.tvCorreo.setText(agente.getCorreo());
        holder.tvCodigo.setText(agente.getCodigo());
        holder.tvArea.setText(agente.getArea());
        holder.tvEstado.setText(agente.getEstado());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvCorreo, tvCodigo, tvArea, tvEstado;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreAgente);
            tvCorreo = itemView.findViewById(R.id.tvCorreoAgente);
            tvCodigo = itemView.findViewById(R.id.tvCodigoAgente);
            tvArea = itemView.findViewById(R.id.tvAreaAgente);
            tvEstado = itemView.findViewById(R.id.tvEstadoAgente);
        }
    }
}
