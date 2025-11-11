package com.example.proyecto_grupo5.ui.home;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyecto_grupo5.R;

public class HomeFragment extends Fragment implements View.OnClickListener {

    Button btnRegistrarInci, btnAjustes, btnCerrarSesion;
    TextView tvBienvenida;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvBienvenida = view.findViewById(R.id.tvBienvenida);
        btnRegistrarInci = view.findViewById(R.id.btnRegistrarInci);
        btnAjustes = view.findViewById(R.id.btnAjustes);
        btnCerrarSesion = view.findViewById(R.id.btnCerrarSesion);

        btnRegistrarInci.setOnClickListener(this);
        btnAjustes.setOnClickListener(this);
        btnCerrarSesion.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        NavController navController = Navigation.findNavController(getView());

        if (view.getId() == R.id.btnRegistrarInci) {
            navController.navigate(R.id.nav_incidencia);
        } else if (view.getId() == R.id.btnAjustes) {
            navController.navigate(R.id.nav_ajuste);
        } else if (view.getId() == R.id.btnCerrarSesion) {
            navController.navigate(R.id.loginFragment);
        }
    }
}
