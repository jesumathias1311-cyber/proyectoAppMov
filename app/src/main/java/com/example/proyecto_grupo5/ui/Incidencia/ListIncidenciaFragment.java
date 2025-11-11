package com.example.proyecto_grupo5.ui.Incidencia;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto_grupo5.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.proyecto_grupo5.Servidor.servidorurl;

public class ListIncidenciaFragment extends Fragment {

    private ListView listView;
    private ArrayList<String> listaIncidencias = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private static final String URL_LISTAR = servidorurl + "listar_incidencias.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_incidencia, container, false);

        listView = view.findViewById(R.id.listViewIncidencias);
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, listaIncidencias);
        listView.setAdapter(adapter);

        // obtener id del usuario desde SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        int idUsuario = prefs.getInt("id_usuario", -1);

        if (idUsuario != -1) {
            cargarIncidencias(idUsuario);
        } else {
            Toast.makeText(getContext(), "Error: no se encontró la sesión del usuario", Toast.LENGTH_LONG).show();
        }

        return view;
    }

    private void cargarIncidencias(int idUsuario) {
        String url = URL_LISTAR + "?id_usuario=" + idUsuario;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        listaIncidencias.clear();

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);

                            String texto = "Título: " + obj.getString("titulo") +
                                    "\nDescripción: " + obj.getString("descripcion") +
                                    "\nTipo: " + obj.getString("tipo") +
                                    "\nUbicación: " + obj.getString("ubicacion") +
                                    "\nEstado: " + obj.getString("estado");

                            listaIncidencias.add(texto);
                        }

                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Error procesando los datos", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(requireContext()).add(request);
    }
}
