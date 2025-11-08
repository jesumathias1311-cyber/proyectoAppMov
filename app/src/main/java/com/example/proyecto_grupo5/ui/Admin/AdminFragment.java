package com.example.proyecto_grupo5.ui.Admin;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.proyecto_grupo5.R;
import android.widget.TextView;

public class AdminFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        TextView text = view.findViewById(R.id.edtAdmin);
        text.setText("Panel de administración - Bienvenido Administrador");

        return view;
    }
}
