package com.example.echo.bring2me.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.echo.bring2me.URLRequests;
import com.example.echo.bring2me.util.DateMask;
import com.example.echo.bring2me.util.PopulateArray;
import com.example.echo.bring2me.R;
import com.example.echo.bring2me.data.RequestSender;
import com.example.echo.bring2me.data.SQLiteHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CadastroViagemActivity extends Activity{
    private static final String TAG = CadastroViagemActivity.class.getSimpleName();
    private ArrayList<String> paises = new ArrayList<String>();
    private Spinner spPaisesOri;
    private Spinner spPaisesDes;
    private Button btnRegister;
    private EditText maxVal;
    private EditText mintax;
    private EditText retorno;
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    private AlertDialog alerta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_viagens);
        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        final String id = user.get("uid");

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        btnRegister = (Button) findViewById(R.id.btnRegistrarViagem);
        maxVal = (EditText) findViewById(R.id.txtValor) ;
        mintax = (EditText) findViewById(R.id.txtMinTax) ;
        retorno = (EditText) findViewById(R.id.txtData) ;
        retorno.addTextChangedListener(DateMask.insert("####-##-##", retorno));
        db = new SQLiteHandler(getApplicationContext());

        PopulateArray.populatePaises(paises);
        spPaisesOri = (Spinner) findViewById(R.id.spOrigem);
        spPaisesDes = (Spinner) findViewById(R.id.spDestino);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, paises);
        spPaisesOri.setAdapter(arrayAdapter1);
        spPaisesDes.setAdapter(arrayAdapter1);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String paisOrigem = spPaisesOri.getSelectedItem().toString();
                String paisDestino = spPaisesDes.getSelectedItem().toString();
                String maxValor = maxVal.getText().toString().trim();
                String minTax = mintax.getText().toString().trim();
                String data = retorno.getText().toString().trim();


                if (!paisOrigem.isEmpty() && !paisDestino.isEmpty() && !maxValor.isEmpty() && !data.isEmpty()) {
                    if(Integer.parseInt(data.substring(5,7))>12 || Integer.parseInt(data.substring(5,7))<1)
                        Toast.makeText(getApplicationContext(),
                                "Data Invalida!", Toast.LENGTH_LONG)
                                .show();
                    else
                        Alerta(id, paisOrigem, paisDestino, maxValor, minTax, data);
                }
                 else {
                    Toast.makeText(getApplicationContext(),
                            "Cadastro incompleto, Por favor insira seus dados!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });


    }


    private void registrarViagem(final String user_id, final String paisOrigem, final String paisDestino, final String maxVal,final String mintax , final String retorno) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registrando ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URLRequests.URL_VIAGENS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String user_id= jObj.getString("id");
                        String paisOrigem = jObj.getString("paisAtual");
                        String paisDestino = jObj.getString("paisDestino");
                        String maxVal = jObj.getString("maxval");
                        String mintax = jObj.getString("mintax");
                        String retorno  = jObj.getString("retorno");


                        // Inserting row in users table
                        db.addViagem(user_id, paisOrigem, paisDestino, maxVal, mintax, retorno);

                        Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso.", Toast.LENGTH_LONG).show();

                        // Launch activity_login activity
                        Intent intent = new Intent(
                                CadastroViagemActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        })
        {
        @Override
        protected Map<String, String> getParams() {
            // Posting params to register url
            Map<String, String> params = new HashMap<String, String>();
            params.put("user_id", user_id);
            params.put("paisAt", paisOrigem);
            params.put("paisDest", paisDestino);
            params.put("maxval", maxVal);
            params.put("mintax", mintax);
            params.put("retorno", retorno);

            return params;
        }

    };

        RequestSender.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    private void Alerta(final String id, final String paisOrigem, final String paisDestino, final String maxValor,final String minTax , final String data) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Cadastro de Viagem");
        //define a mensagem
        builder.setMessage("Você tem certeza que deseja cadastrar a viagem?");

        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(DetalhesPedidoRecebidoActivity.this, "positivo=" + arg1, Toast.LENGTH_SHORT).show();
                registrarViagem(id, paisOrigem, paisDestino, maxValor, minTax, data);
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(DetalhesPedidoRecebidoActivity.this, "negativo=" + arg1, Toast.LENGTH_SHORT).show();
                pDialog.cancel();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }
}
