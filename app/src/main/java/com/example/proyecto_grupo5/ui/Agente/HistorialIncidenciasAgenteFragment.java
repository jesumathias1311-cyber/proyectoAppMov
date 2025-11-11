package com.example.proyecto_grupo5.ui.Agente;

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
import java.util.List;

public class HistorialIncidenciasAgenteFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterIncidenciaAgente adapter;
    private List<ModeloIncidenciaAgente> listaIncidencias;

    private static final String URL_BASE = servidorurl+"listar_incidencias_historialA.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.historial_incidencias_agente, container, false);

        recyclerView = view.findViewById(R.id.recyclerHistorialIncidenciasAgente);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listaIncidencias = new ArrayList<>();
        adapter = new AdapterIncidenciaAgente(listaIncidencias, getContext());
        recyclerView.setAdapter(adapter);

        cargarHistorial();

        return view;
    }

    private void cargarHistorial() {
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_BASE, null,
                response -> procesarRespuesta(response),
                error -> {}
        );
        queue.add(request);
    }

    private void procesarRespuesta(JSONArray response) {
        listaIncidencias.clear();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                ModeloIncidenciaAgente incidencia = new ModeloIncidenciaAgente(
                        obj.getInt("id"),
                        obj.getString("titulo"),
                        obj.getString("descripcion"),
                        obj.getString("ubicacion"),
                        obj.getString("estado"),
                        obj.getString("tipo")
                );
                listaIncidencias.add(incidencia);
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
