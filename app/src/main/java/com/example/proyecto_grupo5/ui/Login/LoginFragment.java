package com.example.proyecto_grupo5.ui.Login;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.proyecto_grupo5.R;

public class LoginFragment extends Fragment implements View.OnClickListener {
    Button btnRegistrarLog,btnIngresarLog;


    public LoginFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int a= 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        btnRegistrarLog = view.findViewById(R.id.btnRegistrarLog);
        btnRegistrarLog.setOnClickListener(this);
        btnIngresarLog = view.findViewById(R.id.btnIngresarLog);
        btnIngresarLog.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view==btnIngresarLog)
        {
            NavController navController = Navigation.findNavController(getView());
            navController.navigate(R.id.action_loginFragment_to_nav_home);
        }
        if (view==btnRegistrarLog)
        {
            NavController navController = Navigation.findNavController(getView());
            navController.navigate(R.id.action_loginFragment_to_nav_registroUser);
        }

    }
}