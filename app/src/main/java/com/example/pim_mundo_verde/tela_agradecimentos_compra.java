package com.example.pim_mundo_verde;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class tela_agradecimentos_compra extends AppCompatActivity {

    private TextView agradecimentoTextView, cepTextView, enderecoTextView, pesoTextView, valorCompraTextView, freteTextView, valorTotalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_agradecimentos_compra);

        // Inicializa os componentes de UI
        agradecimentoTextView = findViewById(R.id.text_agradecimento);
        cepTextView = findViewById(R.id.text_cep);
        enderecoTextView = findViewById(R.id.text_endereco);
        pesoTextView = findViewById(R.id.text_peso);
        valorCompraTextView = findViewById(R.id.text_valor_compra);
        freteTextView = findViewById(R.id.text_frete);
        valorTotalTextView = findViewById(R.id.text_valor_total);

        // Recupera os dados passados da tela anterior
        Intent intent = getIntent();
        String cep = intent.getStringExtra("cep");
        String logradouro = intent.getStringExtra("logradouro");
        String bairro = intent.getStringExtra("bairro");
        String localidade = intent.getStringExtra("localidade");
        String uf = intent.getStringExtra("uf");
        float pesoProduto = intent.getFloatExtra("pesoProduto", 0.0f);
        double valorCompra = intent.getDoubleExtra("valorCompra", 0.0);
        float valorFrete = intent.getFloatExtra("valorFrete", 0.0f);

        // Calcula o valor total (valor da compra + valor do frete)
        double valorTotal = valorCompra + valorFrete;

        // Exibe as informações na tela de agradecimento
        agradecimentoTextView.setText("Obrigado pela sua compra!");
        cepTextView.setText("CEP: " + cep);
        enderecoTextView.setText("Endereço: " + logradouro + ", " + bairro + ", " + localidade + " - " + uf);
        pesoTextView.setText(String.format("Peso Total: %.2f kg", pesoProduto));
        valorCompraTextView.setText(String.format("Valor da Compra: R$ %.2f", valorCompra));
        freteTextView.setText(String.format("Valor do Frete: R$ %.2f", valorFrete));
        valorTotalTextView.setText(String.format("Valor Total: R$ %.2f", valorTotal));
    }

    public void home(View view) {
        // Limpar o carrinho
        Carrinho.getInstance().limparCarrinho();  // Limpa os produtos do carrinho

        // Intent para redirecionar para a tela inicial
        Intent in = new Intent(tela_agradecimentos_compra.this, tela_home.class); // Ajuste para sua tela inicial
        startActivity(in);

        // Finaliza a tela atual para evitar voltar
        finish();
    }

}
