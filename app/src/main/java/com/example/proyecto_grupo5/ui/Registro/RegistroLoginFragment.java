package com.example.proyecto_grupo5.ui.Registro;

import static com.example.proyecto_grupo5.Servidor.servidorurl;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto_grupo5.R;
import com.google.android.material.textfield.TextInputEditText;
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
        String nom_Cli = nom.getText().toString();
        String ap_Cli = apat.getText().toString();
        String am_Cli = amat.getText().toString();
        String cor_Cli = cor.getText().toString();
        String cel_Cli = telf.getText().toString();
        String dir_Cli = dir.getText().toString();
        String user_Cli = user.getText().toString();
        String pass_Cli = pass.getText().toString();


        if(nom_Cli.isEmpty() || ap_Cli.isEmpty()|| am_Cli.isEmpty() || cor_Cli.isEmpty()|| cel_Cli.isEmpty()|| dir_Cli.isEmpty()|| user_Cli.isEmpty()|| pass_Cli.isEmpty())
        {
            Toast.makeText(getContext(), "Debe completar el campo", Toast.LENGTH_SHORT).show();

        }
        else
        {
            //registrar a BD
            RequestParams requestParams = new RequestParams();
            requestParams.put("nom",nom_Cli);
            requestParams.put("ap",ap_Cli);
            requestParams.put("am",am_Cli);
            requestParams.put("cor",cor_Cli);
            requestParams.put("cel",cel_Cli);
            requestParams.put("dir",dir_Cli);
            requestParams.put("user",user_Cli);
            requestParams.put("pass",pass_Cli);


            String url = servidorurl + "registrar_cliente.php";

            AsyncHttpClient httpClient = new AsyncHttpClient();
            httpClient.post(url, requestParams, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {

                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

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