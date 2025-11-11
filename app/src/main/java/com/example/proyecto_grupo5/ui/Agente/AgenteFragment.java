package com.example.proyecto_grupo5.ui.Agente;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.navigation.Navigation;
import com.example.proyecto_grupo5.R;

public class AgenteFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agente, container, false);

        Button btnVerIncidencias = view.findViewById(R.id.btnVerIncidencias);
        Button btnHistorial = view.findViewById(R.id.btnHistorialIncidencias);

        btnVerIncidencias.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_agente_to_listadoIncidencias));

        btnHistorial.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_agente_to_historialIncidencias));

        return view;
    }
}
