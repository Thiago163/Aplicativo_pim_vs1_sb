package com.example.pim_mundo_verde;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

public class tela_calcular_frete extends AppCompatActivity {

    private EditText cepEditText, logradouroEditText, localidadeEditText, bairroEditText, ufEditText, cepDestino;
    private Button buscarButton, avancarButton, calcularFreteButton;
    private TextView resultadoFrete;
    private TextInputEditText pesoProdutoEditText; // Campo para exibir o peso do produto
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
        ufEditText = findViewById(R.id.ufEditText);
        cepDestino = findViewById(R.id.cepDestino);  // Inicializa o campo cepDestino
        pesoProdutoEditText = findViewById(R.id.pesoProduto);  // Referência para o campo de peso
        buscarButton = findViewById(R.id.botaoBuscar);
        avancarButton = findViewById(R.id.botaoAvancar);
        calcularFreteButton = findViewById(R.id.botaoCalcularFrete); // Botão "Calcular frete"
        resultadoFrete = findViewById(R.id.resultadoFrete);

        // Recupera a quantidade de produtos e o valor da compra passados da tela anterior
        int quantidadeProdutos = getIntent().getIntExtra("quantidadeProdutos", 0);  // Valor default é 0
        double valorCompra = getIntent().getDoubleExtra("valorCompra", 0.0);  // Valor default é 0.0

        // Agora você pode usar o valor da compra (valorCompra)
        // Exemplo de como exibir o valor da compra na tela
        // Você pode colocar esse valor em algum TextView ou usar em outro lugar conforme necessário
        Log.d("Valor da Compra", "Total da Compra: R$ " + valorCompra);

        // Agora, você pode usar o valor da compra, que foi passado, em qualquer lugar do código
        // Exemplo: mostrar o valor da compra em um TextView, se necessário
        TextView valorCompraTextView = findViewById(R.id.text_valor_compra);
        valorCompraTextView.setText(String.format("Valor Total: R$ %.2f", valorCompra));

        // Configura os listeners para os botões
        buscarButton.setOnClickListener(v -> buscarCep());
        avancarButton.setOnClickListener(v -> avancarParaProximaTela());
        calcularFreteButton.setOnClickListener(v -> calcularFrete());
    }

    // Método para calcular o frete
    private void calcularFrete() {
        // Verificar se todos os campos obrigatórios estão preenchidos
        verificarCamposPreenchidos();

        String cepDestinoText = cepDestino.getText().toString().trim();
        if (cepDestinoText.isEmpty()) {
            Toast.makeText(this, "Por favor, insira um CEP de destino válido", Toast.LENGTH_SHORT).show();
            return; // Retorna se o campo não estiver preenchido
        }

        if (isFormComplete) {
            // Calcular o valor do frete (pesoTotal * 1.5)
            String pesoProdutoText = pesoProdutoEditText.getText().toString().trim();
            float pesoProduto = pesoProdutoText.isEmpty() ? 0.0f : Float.parseFloat(pesoProdutoText.split(" ")[0]);

            float valorFrete = pesoProduto * 1.5f;

            // Exibe o resultado do frete
            resultadoFrete.setText(String.format("Valor do frete: R$ %.2f", valorFrete));
        } else {
            Toast.makeText(this, "Por favor, preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show();
        }
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
                            ufEditText.setText(uf);  // Preenche o campo UF com o valor retornado

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

    private void verificarCamposPreenchidos() {
        String logradouro = logradouroEditText.getText().toString().trim();
        String localidade = localidadeEditText.getText().toString().trim();
        String bairro = bairroEditText.getText().toString().trim();
        String uf = ufEditText.getText().toString().trim();  // Verifica se o campo UF está preenchido

        if (!logradouro.isEmpty() && !localidade.isEmpty() && !bairro.isEmpty() && !uf.isEmpty()) {
            isFormComplete = true;
        } else {
            isFormComplete = false;
        }
    }

    private void avancarParaProximaTela() {
        // Verificar se todos os campos estão preenchidos antes de avançar
        verificarCamposPreenchidos();

        // Verificar se o frete foi calculado
        if (isFormComplete && resultadoFrete.getText().toString().contains("Valor do frete: R$")) {
            // Limpar o carrinho
            Carrinho.getInstance().limparCarrinho();  // Limpa o carrinho após a compra

            // Navegar para a próxima tela (tela_home, por exemplo)
            Intent in = new Intent(tela_calcular_frete.this, tela_home.class);
            in.putExtra("compraFinalizada", true);  // Passa o sinalizador para a próxima tela
            startActivity(in);
        } else {
            Toast.makeText(this, "Por favor, calcule o frete antes de avançar", Toast.LENGTH_SHORT).show();
        }
    }

    // Método corrigido para o botão Home (voltar)
    public void home(View view) {
            // Navegar para a tela de finalizar compra
    Intent intent = new Intent(tela_calcular_frete.this, tela_menu_geral.class);
    startActivity(intent);
    }
}