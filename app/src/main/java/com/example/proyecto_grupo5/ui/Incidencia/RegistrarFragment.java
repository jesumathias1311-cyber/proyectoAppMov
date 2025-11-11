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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.proyecto_grupo5.R;
import com.example.proyecto_grupo5.ui.Login.SesionUsuario;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class RegistrarFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    Spinner tipos;
    EditText descripcion;
    Button regIncidencia,regneta;
    ArrayList<TiposIncidencia> listaIncidencia;
    ArrayAdapter<TiposIncidencia> adapterIncidencia;

    int idTipoIncidenciaSeleccionada;
    @Override public void onCreate(Bundle savedInstanceState)
    { super.onCreate(savedInstanceState); }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registrar, container, false);

        tipos = view.findViewById(R.id.spinnerTipo);
        descripcion = view.findViewById(R.id.etDescripcion);
        regIncidencia = view.findViewById(R.id.btnRegistrar);
        regIncidencia.setOnClickListener(this);

        // Inicializar lista y adaptador
        listaIncidencia = new ArrayList<>();
        adapterIncidencia = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, listaIncidencia);
        tipos.setAdapter(adapterIncidencia);
        tipos.setOnItemSelectedListener(this);

        ConsultarIncidencia();

        return view;
    }
    private void ConsultarIncidencia() {
        String url = servidorurl + "consultar_tipos.php";
        AsyncHttpClient httpClient = new AsyncHttpClient();

        httpClient.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    String respuesta = new String(bytes);
                    JSONArray jsonArray = new JSONArray(respuesta);

                    listaIncidencia.clear();

                    for (int x = 0; x < jsonArray.length(); x++) {
                        JSONObject obj = jsonArray.getJSONObject(x);
                        int id = obj.getInt("id");
                        String nombre = obj.getString("nombre");

                        listaIncidencia.add(new TiposIncidencia(id, nombre));
                    }

                    adapterIncidencia.notifyDataSetChanged();
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error al procesar JSON", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getContext(), "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v== regIncidencia) {
            RegistrarIncidencia();
        }
    }

    private void RegistrarIncidencia() {
        String des = descripcion.getText().toString();
        String spTipoIncidencia = String.valueOf(idTipoIncidenciaSeleccionada);

        if (des.isEmpty())
        { Toast.makeText(getContext(), "Debe completar el campo", Toast.LENGTH_SHORT).show(); }
        else
        {
            int idUsuario = SesionUsuario.getIdUsuario();
            Log.d("ID_USUARIO", "ID actual: " + idUsuario);

            RequestParams requestParams = new RequestParams();
            requestParams.put("id_tipo", spTipoIncidencia);
            requestParams.put("descripcion", des);
            requestParams.put("id_usuario", idUsuario);

            String url = servidorurl + "registrar_incidencia.php";
            AsyncHttpClient httpClient = new AsyncHttpClient();
            httpClient.post(url, requestParams, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    Toast.makeText(getContext(), "Registro realizado exitosamente", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Toast.makeText(getContext(), "Error en el registro", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView==tipos) {
            TiposIncidencia tipoSeleccionada = (TiposIncidencia) adapterView.getItemAtPosition(i);
            // Guardar el ID de la marca
            idTipoIncidenciaSeleccionada = tipoSeleccionada.getId();
            Toast.makeText(getContext(), "ID tipo de Incidencia es: " + idTipoIncidenciaSeleccionada, Toast.LENGTH_SHORT).show(); }
        }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
