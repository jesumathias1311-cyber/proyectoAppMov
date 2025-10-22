package com.example.proyecto_grupo5.ui.Registro;

import static com.example.proyecto_grupo5.Servidor.servidorurl;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.proyecto_grupo5.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;


public class RegistroLoginFragment extends Fragment implements View.OnClickListener {

    Button btnReg;
    EditText nom,apat,amat,cor,dir,telf,user,pass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_registro_login, container, false);
        nom = view.findViewById(R.id.edtRegNomLog);
        apat = view.findViewById(R.id.edtApatLog);
        amat = view.findViewById(R.id.edtAmatLog);
        cor = view.findViewById(R.id.edtCorLog);
        dir = view.findViewById(R.id.edtDirLog);
        telf = view.findViewById(R.id.edtTelfLog);
        user = view.findViewById(R.id.edtUserLog);
        pass = view.findViewById(R.id.edtPassLog);
        btnReg = view.findViewById(R.id.btnRegistrarCuenta);
        btnReg.setOnClickListener(this);
        return view;


    }

    @Override
    public void onClick(View v) {
        if (v==btnReg)
        {
            RegistrarUsuario();
        }
    }

    private void RegistrarUsuario() {
        String nom_user = nom.getText().toString();
        String ap_user = apat.getText().toString();
        String am_user = amat.getText().toString();
        String cor_user = cor.getText().toString();
        String cel_user = telf.getText().toString();
        String dir_user = dir.getText().toString();
        String user_user = user.getText().toString();
        String pass_user = pass.getText().toString();


        if(nom_user.isEmpty() || ap_user.isEmpty()|| am_user.isEmpty() || cor_user.isEmpty()|| cel_user.isEmpty()|| dir_user.isEmpty()|| user_user.isEmpty()|| pass_user.isEmpty())
        {
            Toast.makeText(getContext(), "Debe completar el campo", Toast.LENGTH_SHORT).show();

        }
        else
        {
            //registrar a BD
            RequestParams requestParams = new RequestParams();
            requestParams.put("nom",nom_user);
            requestParams.put("ap",ap_user);
            requestParams.put("am",am_user);
            requestParams.put("cor",cor_user);
            requestParams.put("cel",cel_user);
            requestParams.put("dir",dir_user);
            requestParams.put("user",user_user);
            requestParams.put("pass",pass_user);


            String url = servidorurl + "registrar_usuario.php";

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
            LimpiarCampos();
        }

    }
    private void LimpiarCampos() {

        nom.setText("");
        apat.setText("");
        amat.setText("");
        cor.setText("");
        telf.setText("");
        dir.setText("");
        user.setText("");
        pass.setText("");
    }


}