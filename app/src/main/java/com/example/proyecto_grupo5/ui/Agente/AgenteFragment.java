package com.example.proyecto_grupo5.ui.Agente;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.proyecto_grupo5.R;

public class AgenteFragment extends Fragment {

    private Button btnVerIncidencias, btnReportarIncidencia;

    public AgenteFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agente, container, false);

        btnVerIncidencias = view.findViewById(R.id.btnVerIncidencias);
        btnReportarIncidencia = view.findViewById(R.id.btnReportarIncidencia);

        btnVerIncidencias.setOnClickListener(v ->
                Toast.makeText(getContext(), "Ver incidencias asignadas", Toast.LENGTH_SHORT).show()
        );

        btnReportarIncidencia.setOnClickListener(v ->
                Toast.makeText(getContext(), "Ir a registrar nueva incidencia", Toast.LENGTH_SHORT).show()
        );

        return view;
    }
}
