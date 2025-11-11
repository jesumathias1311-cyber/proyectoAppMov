package com.example.proyecto_grupo5.ui.Agente;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_grupo5.R;

import java.util.List;

public class AdapterIncidenciaAgente extends RecyclerView.Adapter<AdapterIncidenciaAgente.ViewHolder> {

    private List<ModeloIncidenciaAgente> listaIncidencias;
    private Context context;

    public AdapterIncidenciaAgente(List<ModeloIncidenciaAgente> listaIncidencias, Context context) {
        this.listaIncidencias = listaIncidencias;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_incidencia_agente, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModeloIncidenciaAgente incidencia = listaIncidencias.get(position);

        holder.tvTitulo.setText(incidencia.getTitulo());
        holder.tvDescripcion.setText(incidencia.getDescripcion());
        holder.tvUbicacion.setText("📍 " + incidencia.getUbicacion());
        holder.tvEstadoActual.setText("Estado actual: " + incidencia.getEstado());

        // Spinner de estados (ya definido en strings.xml)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.estados_incidencia, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinnerEstado.setAdapter(adapter);

        // Selecciona automáticamente el estado actual
        int pos = adapter.getPosition(incidencia.getEstado());
        if (pos >= 0) holder.spinnerEstado.setSelection(pos);

        // Botón para actualizar estado (lógica pendiente, solo interfaz por ahora)
        holder.btnActualizarEstado.setOnClickListener(v -> {
            String nuevoEstado = holder.spinnerEstado.getSelectedItem().toString();
            // Aquí luego puedes hacer una llamada con Volley o Retrofit para actualizar el estado
        });
    }

    @Override
    public int getItemCount() {
        return listaIncidencias.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvDescripcion, tvUbicacion, tvEstadoActual;
        Spinner spinnerEstado;
        Button btnActualizarEstado;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvUbicacion = itemView.findViewById(R.id.tvUbicacion);
            tvEstadoActual = itemView.findViewById(R.id.tvEstadoActual);
            spinnerEstado = itemView.findViewById(R.id.spinnerEstado);
            btnActualizarEstado = itemView.findViewById(R.id.btnActualizarEstado);
        }
    }
}
