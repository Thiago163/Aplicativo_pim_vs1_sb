package com.example.pim_mundo_verde;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class tela_calcular_frete extends AppCompatActivity {

    private EditText cepEditText, logradouroEditText, localidadeEditText, bairroEditText;
    private Spinner ufSpinner;
    private Button buscarButton, avancarButton;
    private TextView resultadoFrete;
    private boolean isFormComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_calcular_frete);

        // Inicializa os componentes de UI
        cepEditText = findViewById(R.id.cepOrigem);
        logradouroEditText = findViewById(R.id.logradouro);
        localidadeEditText = findViewById(R.id.localidade);
        bairroEditText = findViewById(R.id.bairro);
        ufSpinner = findViewById(R.id.ufSpinner);
        buscarButton = findViewById(R.id.botaoBuscar);
        avancarButton = findViewById(R.id.botaoAvancar);
        resultadoFrete = findViewById(R.id.resultadoFrete);

        // Habilita os listeners para o ajuste do padding da tela com base na barra de status
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tela_calcular_frete), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configura o botão de buscar CEP
        buscarButton.setOnClickListener(v -> buscarCep());

        // Configura o botão avançar
        avancarButton.setEnabled(false);
        avancarButton.setOnClickListener(v -> avancarParaProximaTela());
    }

    private void buscarCep() {
        String cep = cepEditText.getText().toString().trim();
        if (cep.isEmpty()) {
            Toast.makeText(this, "Por favor, insira um CEP válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Construa a URL para a API do ViaCEP
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        // Utiliza Volley para buscar os dados do CEP
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Preenche os campos com os dados retornados pela API
                            String logradouro = response.optString("logradouro");
                            String bairro = response.optString("bairro");
                            String localidade = response.optString("localidade");
                            String uf = response.optString("uf");

                            logradouroEditText.setText(logradouro);
                            bairroEditText.setText(bairro);
                            localidadeEditText.setText(localidade);
                            setUfSpinnerValue(uf);

                            // Verifica se todos os campos obrigatórios estão preenchidos
                            verificarCamposPreenchidos();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(tela_calcular_frete.this, "Erro ao buscar CEP", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(tela_calcular_frete.this, "Erro ao consultar o CEP", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(jsonObjectRequest);
    }

    private void setUfSpinnerValue(String uf) {
        // A lógica para preencher o estado (UF) no Spinner seria baseada na resposta da API
        for (int i = 0; i < ufSpinner.getCount(); i++) {
            if (ufSpinner.getItemAtPosition(i).toString().equals(uf)) {
                ufSpinner.setSelection(i);
                break;
            }
        }
    }

    private void verificarCamposPreenchidos() {
        String logradouro = logradouroEditText.getText().toString().trim();
        String localidade = localidadeEditText.getText().toString().trim();
        String bairro = bairroEditText.getText().toString().trim();

        if (!logradouro.isEmpty() && !localidade.isEmpty() && !bairro.isEmpty()) {
            isFormComplete = true;
            avancarButton.setEnabled(true);
        } else {
            isFormComplete = false;
            avancarButton.setEnabled(false);
        }
    }

    private void avancarParaProximaTela() {
        Intent in = new Intent(tela_calcular_frete.this, tela_home.class); // Use o nome correto da classe
        startActivity(in);
    }

    public void home(View view) {
        Intent in = new Intent(tela_calcular_frete.this, tela_finalizar_compra.class); // Use o nome correto da classe
        startActivity(in);
    }

}
