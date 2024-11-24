package com.example.pim_mundo_verde;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pim_mundo_verde.Adapter.AdapterProduto;
import com.example.pim_mundo_verde.model.Produto;
import com.example.pim_mundo_verde.tela_menu_geral;

import java.util.List;

public class tela_finalizar_compra extends AppCompatActivity {
    private RecyclerView recyclerViewRevisao;
    private TextView textTotal;
    private TextView textQuantidadeProdutos;  // Novo TextView para mostrar a quantidade de produtos
    private List<Produto> produtosCarrinho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_finalizar_compra);

        recyclerViewRevisao = findViewById(R.id.recycler_view_revisao);
        textTotal = findViewById(R.id.text_total_revisao);
        textQuantidadeProdutos = findViewById(R.id.text_quantidade_produtos);  // Referência para o novo TextView

        // Recebe a lista de produtos do carrinho
        Intent intent = getIntent();
        produtosCarrinho = intent.getParcelableArrayListExtra("produtosCarrinho");

        // Configura o RecyclerView para exibir os produtos
        AdapterProduto adapterProduto = new AdapterProduto(produtosCarrinho, produto -> {});
        recyclerViewRevisao.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRevisao.setAdapter(adapterProduto);

        // Atualiza o total e a quantidade de produtos
        atualizarTotal();
        atualizarQuantidadeProdutos();
    }

    private void atualizarTotal() {
        double total = 0;
        for (Produto produto : produtosCarrinho) {
            total += produto.getPrecoAsDouble() * produto.getQuantidade();
        }
        textTotal.setText(String.format("Total: R$ %.2f", total));
    }

    private void atualizarQuantidadeProdutos() {
        // Soma a quantidade de cada produto no carrinho
        int quantidadeTotal = 0;
        for (Produto produto : produtosCarrinho) {
            quantidadeTotal += produto.getQuantidade();  // Adiciona a quantidade do produto
        }
        textQuantidadeProdutos.setText("Quantidade de produtos: " + quantidadeTotal);
    }

    public void finalizarCompra(View view) {
        // Atualizar o total
        atualizarTotal();

        // Calcular o valor total da compra
        double total = 0;
        for (Produto produto : produtosCarrinho) {
            total += produto.getPrecoAsDouble() * produto.getQuantidade();  // Calculo do total
        }

        // Passa a quantidade de produtos e o valor total para a tela de calcular frete
        int quantidadeProdutos = 0;
        for (Produto produto : produtosCarrinho) {
            quantidadeProdutos += produto.getQuantidade();  // Conta o total de unidades
        }

        // Cria o Intent para navegar para a tela de calcular frete
        Intent intent = new Intent(tela_finalizar_compra.this, tela_calcular_frete.class);
        intent.putExtra("quantidadeProdutos", quantidadeProdutos);  // Passando a quantidade total de unidades
        intent.putExtra("valorCompra", total);  // Passando o valor total da compra
        startActivity(intent);
    }

    public void menu(View view) {
        Intent in = new Intent(tela_finalizar_compra.this, tela_menu_geral.class);
        startActivity(in);
    }
}