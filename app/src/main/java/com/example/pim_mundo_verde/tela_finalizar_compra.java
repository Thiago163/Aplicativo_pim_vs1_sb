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

import java.util.ArrayList;
import java.util.List;

public class tela_finalizar_compra extends AppCompatActivity {
    private RecyclerView recyclerViewRevisao;
    private TextView textTotal;
    private List<Produto> produtosCarrinho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_finalizar_compra);

        recyclerViewRevisao = findViewById(R.id.recycler_view_revisao);
        textTotal = findViewById(R.id.text_total_revisao);

        // Recebe a lista de produtos do carrinho
        Intent intent = getIntent();
        produtosCarrinho = intent.getParcelableArrayListExtra("produtosCarrinho");

        // Configura o RecyclerView para exibir os produtos
        AdapterProduto adapterProduto = new AdapterProduto(produtosCarrinho, produto -> {});
        recyclerViewRevisao.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRevisao.setAdapter(adapterProduto);

        atualizarTotal();
    }

    private void atualizarTotal() {
        double total = 0;
        for (Produto produto : produtosCarrinho) {
            total += produto.getPrecoAsDouble() * produto.getQuantidade();
        }
        textTotal.setText(String.format("Total: R$ %.2f", total));
    }

    public void finalizarCompra(View view) {
        // Limpar o carrinho
        Carrinho.getInstance().limparCarrinho();  // Limpa o carrinho após a compra

        // Atualizar o RecyclerView
        AdapterProduto adapterProduto = (AdapterProduto) recyclerViewRevisao.getAdapter();
        if (adapterProduto != null) {
            adapterProduto.notifyDataSetChanged();  // Notifica a mudança na lista
        }

        // Atualizar o total
        atualizarTotal();

        // Ação para redirecionar ao menu ou outra tela
        Intent in = new Intent(tela_finalizar_compra.this, tela_calcular_frete.class);
        in.putExtra("compraFinalizada", true);  // Passando o sinalizador para limpar a tela de carrinho
        startActivity(in);
    }

    public void menu(View view) {
        Intent in = new Intent(tela_finalizar_compra.this, tela_menu_geral.class);
        startActivity(in);
    }
}