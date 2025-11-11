package com.example.proyecto_grupo5.ui.Admin;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation; //
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.proyecto_grupo5.R;

public class AdminFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        Button btnListarAgentes = view.findViewById(R.id.btnListarAgentes);
        Button btnRegistrarAgente = view.findViewById(R.id.btnRegistrarAgente);
        Button btnAsignarIncidencia = view.findViewById(R.id.btnAsignarIncidencia);

        // 👉 Navegación con Navigation Component (sin usar transacciones manuales)
        btnListarAgentes.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.nav_admin_list_agentes)
        );

        // botones a configurar luego:
        //btnRegistrarAgente.setOnClickListener(v -> abrirFragment(new Admin_RegistrarAgenteFragment()));
        //btnAsignarIncidencia.setOnClickListener(v -> abrirFragment(new Admin_AsignarIncidenciaFragment()));

        return view;
    }

}
