package com.example.proyecto_grupo5.ui.Admin;

import static com.example.proyecto_grupo5.Servidor.servidorurl;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto_grupo5.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Admin_ListAgentesFragment extends Fragment {

    private RecyclerView recyclerAgentes;
    private Admin_AgenteAdapter adapter;
    private ArrayList<Admin_ModeloAgente> listaAgentes;
    private static final String URL = servidorurl+ "Admin_listar_agentes.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_list_agentes, container, false);

        recyclerAgentes = view.findViewById(R.id.recyclerAgentes);
        recyclerAgentes.setLayoutManager(new LinearLayoutManager(getContext()));

        listaAgentes = new ArrayList<>();
        adapter = new Admin_AgenteAdapter(listaAgentes);
        recyclerAgentes.setAdapter(adapter);

        cargarAgentes();

        return view;
    }

    private void cargarAgentes() {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                response -> {
                    listaAgentes.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            int id = obj.getInt("id_agente");
                            String nombre = obj.getString("nombre_completo");
                            String correo = obj.getString("correo");
                            String codigo = obj.getString("codigo_agente");
                            String area = obj.getString("area");
                            String estado = obj.getInt("activo") == 1 ? "Activo" : "Inactivo";

                            listaAgentes.add(new Admin_ModeloAgente(id, nombre, correo, codigo, area, estado));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                },
                error -> {
                    error.printStackTrace();
                });

        queue.add(request);
    }

}
