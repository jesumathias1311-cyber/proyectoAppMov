package com.example.proyecto_grupo5.ui.Login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.proyecto_grupo5.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import com.google.android.material.textfield.TextInputEditText;

import cz.msebera.android.httpclient.Header;

import static com.example.proyecto_grupo5.Servidor.servidorurl;

import org.json.JSONObject;

public class LoginFragment extends Fragment implements View.OnClickListener {

    TextInputEditText emailEditText, passwordEditText;
    Button btnIngresarLog, btnRegistrarLog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        emailEditText = view.findViewById(R.id.EmailEditText);
        passwordEditText = view.findViewById(R.id.PasswordEditText);
        btnIngresarLog = view.findViewById(R.id.btnIngresarLog);
        btnRegistrarLog = view.findViewById(R.id.btnRegistrarLog);

        btnIngresarLog.setOnClickListener(this);
        btnRegistrarLog.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == btnIngresarLog) {
            IniciarSesion();
        } else if (view == btnRegistrarLog) {
            NavController navController = Navigation.findNavController(getView());
            navController.navigate(R.id.action_loginFragment_to_nav_registroUser);
        }
    }

    private void IniciarSesion() {
        String usuario = emailEditText.getText().toString().trim();
        String contrasena = passwordEditText.getText().toString().trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(getContext(), "Ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = servidorurl + "login.php";

        RequestParams params = new RequestParams();
        params.put("usuario", usuario);
        params.put("contrasena", contrasena);

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String respuesta = new String(responseBody).trim();
                Log.d("LOGIN_DEBUG", "Respuesta servidor: [" + respuesta + "]");

                try {
                    JSONObject json = new JSONObject(respuesta);
                    String status = json.getString("status");

                    if (status.equals("success")) {

                        int idUsuario = json.getInt("id_usuario");  //
                        String rol = json.getString("rol");
                        String nombre = json.getString("nombre_completo");

                        // guardar sesión
                        SharedPreferences prefs = requireActivity().getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("id_usuario", idUsuario);
                        editor.putString("rol", rol);
                        editor.putString("nombre_completo", nombre);
                        editor.apply();

                        Toast.makeText(getContext(), "Bienvenido " + nombre + " (" + rol + ")", Toast.LENGTH_SHORT).show();

                        //Redirecciona según rol
                        NavController navController = Navigation.findNavController(getView());
                        if (rol.equalsIgnoreCase("admin")) {
                            navController.navigate(R.id.action_loginFragment_to_nav_admin);
                        } else if (rol.equalsIgnoreCase("agente")) {
                            navController.navigate(R.id.action_loginFragment_to_nav_agente);
                        } else {
                            navController.navigate(R.id.action_loginFragment_to_nav_home);
                        }

                    } else if (status.equals("error")) {
                        Toast.makeText(getContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    } else if (status.equals("faltan_datos")) {
                        Toast.makeText(getContext(), "Faltan datos para iniciar sesión", Toast.LENGTH_SHORT).show();
                    } else if (status.equals("error_conexion")) {
                        Toast.makeText(getContext(), "Error de conexión con el servidor", Toast.LENGTH_SHORT).show();
                    } else if (status.equals("error_sql")) {
                        Toast.makeText(getContext(), "Error en base de datos", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Respuesta inesperada: " + respuesta, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(getContext(), "Error interpretando respuesta", Toast.LENGTH_SHORT).show();
                    Log.e("LOGIN_DEBUG", "Excepción: " + e.getMessage());
                }
            }



            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getContext(), "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
