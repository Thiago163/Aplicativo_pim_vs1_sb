package com.example.pim_mundo_verde.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pim_mundo_verde.Adapter.AdapterProduto;
import com.example.pim_mundo_verde.R;
import com.example.pim_mundo_verde.component.Carrinho;
import com.example.pim_mundo_verde.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class tela_vendas extends AppCompatActivity implements AdapterProduto.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_vendas);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configuração do RecyclerView para exibir produtos
        RecyclerView recyclerViewProdutos = findViewById(R.id.list_view);
        recyclerViewProdutos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewProdutos.setHasFixedSize(true);

        // Criando uma lista de produtos para exibir no RecyclerView
        List<Produto> listaProdutos = new ArrayList<>();
        listaProdutos.add(new Produto("id_beterraba", R.drawable.beterraba, "Beterraba", "Beterraba nutritiva, ótima para saladas e sucos.", "R$ 8,00"));
        listaProdutos.add(new Produto("id_cenoura", R.drawable.cenoura, "Cenoura", "Cenoura fresca, excelente para melhorar a visão.", "R$ 6,50"));
        listaProdutos.add(new Produto("id_tomate", R.drawable.tomate, "Tomate", "Tomate orgânico, ideal para saladas e molhos.", "R$ 4,00"));
        listaProdutos.add(new Produto("id_morango", R.drawable.morango, "Morango", "Morango doce e suculento, perfeito para sobremesas.", "R$ 12,00"));
        listaProdutos.add(new Produto("id_alface", R.drawable.alface, "Alface", "Alface fresca, ideal para saladas crocantes.", "R$ 5,00"));
        listaProdutos.add(new Produto("id_brocolis", R.drawable.brocolis, "Brócolis", "Brócolis rico em vitaminas, ótimo para cozidos e saladas.", "R$ 7,50"));

        // Configurando o AdapterProduto com a lista de produtos e o listener
        AdapterProduto adapterProduto = new AdapterProduto(listaProdutos, this);
        recyclerViewProdutos.setAdapter(adapterProduto);
    }

    @Override
    public void onItemClick(Produto produto) {
        // Verifica se o produto já foi adicionado ao carrinho
        boolean produtoExistente = false;
        for (Produto p : Carrinho.getInstance().getProdutos()) {
            if (p.getId().equals(produto.getId())) { // Compara pelo ID ou nome único do produto
                produtoExistente = true;
                break;
            }
        }

        if (produtoExistente) {
            // Exibe o alerta caso o produto já tenha sido adicionado
            Toast.makeText(tela_vendas.this, "Produto já adicionado ao carrinho", Toast.LENGTH_SHORT).show();
        } else {
            // Caso contrário, adiciona o produto ao carrinho
            Carrinho.getInstance().adicionarProduto(produto);
            Toast.makeText(tela_vendas.this, produto.getNome() + " adicionado ao carrinho!", Toast.LENGTH_SHORT).show();
        }
    }

    public void inicio(View view) {
        Intent in = new Intent(tela_vendas.this, tela_home.class);
        startActivity(in);
    }

    public void fazendeiro(View view) {
        Intent in = new Intent(tela_vendas.this, tela_fazendeiro.class);
        startActivity(in);
    }

    public void menu(View view) {
        Intent in = new Intent(tela_vendas.this, tela_menu_geral.class);
        startActivity(in);
    }

    public void carrinho(View view) {
        Intent in = new Intent(tela_vendas.this, tela_carrinho.class);
        startActivity(in);
    }
}