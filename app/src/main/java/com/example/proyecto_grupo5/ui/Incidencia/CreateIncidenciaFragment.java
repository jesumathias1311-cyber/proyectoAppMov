package com.example.proyecto_grupo5.ui.Incidencia;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.proyecto_grupo5.R;


public class CreateIncidenciaFragment extends Fragment implements View.OnClickListener {

    Button btnNueIn;


    //esto tambien se hace
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment (modificar el Viiew view)
        View view = inflater.inflate(R.layout.fragment_create_incidencia, container, false);
        //aca se instancia los botoncitos q creee
        btnNueIn = view.findViewById(R.id.btnNueIn);
        btnNueIn.setOnClickListener(this);
        return view;
    }



    @Override
    public void onClick(View view) {
        if (view == btnNueIn)
        {
            NavController navController = Navigation.findNavController(getView());
            navController.navigate(R.id.action_nav_incidencia_to_nav_registrar);
        }
    }
}