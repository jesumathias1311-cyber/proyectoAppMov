package com.example.proyecto_grupo5.ui.Incidencia;

import static com.example.proyecto_grupo5.Servidor.servidorurl;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto_grupo5.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistrarFragment extends Fragment {

    private Spinner spinnerTipo;
    private EditText etTitulo, etDescripcion, etUbicacion;
    private CheckBox chkCritica;
    private Button btnRegistrar;
    private ArrayList<Integer> tiposIds = new ArrayList<>();

    private static final String URL_TIPOS = servidorurl + "listar_tipos.php";
    private static final String URL_REGISTRO = servidorurl + "registrar_incidencia.php";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registrar, container, false);

        spinnerTipo = view.findViewById(R.id.spinnerTipo);
        etTitulo = view.findViewById(R.id.etTitulo);
        etDescripcion = view.findViewById(R.id.etDescripcion);
        etUbicacion = view.findViewById(R.id.etUbicacion);
        chkCritica = view.findViewById(R.id.chkCritica);
        btnRegistrar = view.findViewById(R.id.btnRegistrar);

        cargarTiposIncidencia();

        btnRegistrar.setOnClickListener(v -> registrarIncidencia());

        return view;
    }

    private void cargarTiposIncidencia() {
        Log.d("REGISTRAR", " Cargando tipos desde: " + URL_TIPOS);

        StringRequest request = new StringRequest(Request.Method.GET, URL_TIPOS,
                response -> {
                    Log.d("REGISTRAR", " Respuesta recibida: " + response);
                    try {
                        JSONArray array = new JSONArray(response);
                        ArrayList<String> nombres = new ArrayList<>();
                        tiposIds.clear();

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            int idTipo = obj.has("id_tipo") ? obj.getInt("id_tipo") : obj.getInt("id");
                            tiposIds.add(idTipo);
                            nombres.add(obj.getString("nombre"));
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                                android.R.layout.simple_spinner_item, nombres);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerTipo.setAdapter(adapter);

                        Log.d("REGISTRAR", " Tipos cargados: " + tiposIds + " → " + nombres);

                    } catch (JSONException e) {
                        Log.e("REGISTRAR", " Error procesando JSON: " + e.getMessage(), e);
                        Toast.makeText(getContext(), "Error al procesar tipos", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("REGISTRAR", " Volley error al cargar tipos: ", error);
                    if (error.networkResponse != null) {
                        String body = new String(error.networkResponse.data);
                        Log.e("REGISTRAR", "Código HTTP: " + error.networkResponse.statusCode);
                        Log.e("REGISTRAR", "Cuerpo: " + body);
                    }
                    Toast.makeText(getContext(), "Error de conexión al cargar tipos", Toast.LENGTH_SHORT).show();
                }
        );

        Volley.newRequestQueue(requireContext()).add(request);
    }

    private void registrarIncidencia() {
        String titulo = etTitulo.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();
        String ubicacion = etUbicacion.getText().toString().trim();

        int posSeleccion = spinnerTipo.getSelectedItemPosition();
        if (posSeleccion < 0 || posSeleccion >= tiposIds.size()) {
            Toast.makeText(getContext(), "Selecciona un tipo válido", Toast.LENGTH_SHORT).show();
            return;
        }
        int idTipo = tiposIds.get(posSeleccion); //
        boolean esCritica = chkCritica.isChecked();

        SharedPreferences prefs = requireActivity().getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        int idUsuario = prefs.getInt("id_usuario", -1);

        if (idUsuario == -1) {
            Toast.makeText(getContext(), "Error: no se encontró la sesión del usuario", Toast.LENGTH_LONG).show();
            return;
        }
        if (titulo.isEmpty() || descripcion.isEmpty() || ubicacion.isEmpty()) {
            Toast.makeText(getContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Registrando incidencia...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Log.d("REGISTRAR", "Enviando a: " + URL_REGISTRO);
        StringRequest request = new StringRequest(Request.Method.POST, URL_REGISTRO,
                response -> {
                    progressDialog.dismiss();
                    Log.d("REGISTRAR", "Respuesta servidor: " + response);
                    try {
                        JSONObject json = new JSONObject(response);
                        if (json.optBoolean("success", false)) {
                            Toast.makeText(getContext(), "✅ " + json.optString("message", "Registrado"), Toast.LENGTH_LONG).show();
                            etTitulo.setText("");
                            etDescripcion.setText("");
                            etUbicacion.setText("");
                            chkCritica.setChecked(false);
                        } else {
                            String error = json.optString("error", "Error desconocido");
                            Toast.makeText(getContext(), "Error del servidor: " + error, Toast.LENGTH_LONG).show();
                            Log.e("REGISTRAR", "Respuesta con error: " + response);
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Error al procesar respuesta: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("REGISTRAR", "Excepción JSON: ", e);
                    }
                },
                error -> {
                    progressDialog.dismiss();
                    String msg = error.getMessage();
                    Log.e("REGISTRAR", "Volley error: ", error);
                    if (error.networkResponse != null) {
                        NetworkResponse nr = error.networkResponse;
                        String body = new String(nr.data);
                        Log.e("REGISTRAR", "Network response code: " + nr.statusCode + " body: " + body);
                        Toast.makeText(getContext(), "Error servidor: " + nr.statusCode, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Error de conexión: " + msg, Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_usuario", String.valueOf(idUsuario));
                params.put("id_tipo", String.valueOf(idTipo));
                params.put("titulo", titulo);
                params.put("descripcion", descripcion);
                params.put("ubicacion", ubicacion);
                params.put("es_critica", esCritica ? "1" : "0");

                Log.d("REGISTRAR", "Params enviados: " + params);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(request);
    }
}