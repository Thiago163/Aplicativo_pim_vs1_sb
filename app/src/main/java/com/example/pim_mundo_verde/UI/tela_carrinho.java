package com.example.pim_mundo_verde.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pim_mundo_verde.Adapter.AdapterProduto;
import com.example.pim_mundo_verde.R;
import com.example.pim_mundo_verde.component.Carrinho;
import com.example.pim_mundo_verde.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class tela_carrinho extends AppCompatActivity {

    private RecyclerView recyclerViewCarrinho;
    private TextView textTotal;
    private Button buttonFinalizar;
    private Button buttonEditarQuantidade;
    private Button buttonRemoverProduto;
    private Button buttonRecuperarItem;
    private EditText editTextQuantidade;
    private AdapterProduto adapterProduto;
    private List<Produto> produtosCarrinho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_carrinho);

        recyclerViewCarrinho = findViewById(R.id.recycler_view_carrinho);
        textTotal = findViewById(R.id.text_total);
        buttonFinalizar = findViewById(R.id.button_finalizar);
        buttonEditarQuantidade = findViewById(R.id.button_editar_quantidade);
        buttonRemoverProduto = findViewById(R.id.button_remover_produto);
        buttonRecuperarItem = findViewById(R.id.button_recuperar_item);
        editTextQuantidade = findViewById(R.id.editTextQuantidade);

        recyclerViewCarrinho.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCarrinho.setHasFixedSize(true);

        produtosCarrinho = Carrinho.getInstance().getProdutos();

        adapterProduto = new AdapterProduto(produtosCarrinho, produto -> {
            // Clique no produto
        });
        recyclerViewCarrinho.setAdapter(adapterProduto);

        atualizarTotal();

        buttonFinalizar.setOnClickListener(v -> {
            Intent intent = new Intent(tela_carrinho.this, tela_finalizar_compra.class);
            intent.putParcelableArrayListExtra("produtosCarrinho", new ArrayList<>(produtosCarrinho));
            startActivity(intent);
        });

        buttonEditarQuantidade.setOnClickListener(this::editarQuantidade);
        buttonRemoverProduto.setOnClickListener(this::removerProduto);
        buttonRecuperarItem.setOnClickListener(this::recuperarItem);
    }

    @Override
    protected void onResume() {
        super.onResume();
        produtosCarrinho = Carrinho.getInstance().getProdutos();
        adapterProduto.notifyDataSetChanged();
        atualizarTotal();
    }

    public void editarQuantidade(View view) {
        Produto produto = adapterProduto.getProdutoSelecionado();
        if (produto != null) {
            String quantidadeStr = editTextQuantidade.getText().toString();
            if (!quantidadeStr.isEmpty()) {
                int novaQuantidade = Integer.parseInt(quantidadeStr);
                if (novaQuantidade > 0) {
                    produto.setQuantidade(novaQuantidade);
                    adapterProduto.notifyDataSetChanged();
                    atualizarTotal();
                    Toast.makeText(this, "Quantidade editada com sucesso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Por favor, insira uma quantidade válida", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Por favor, insira uma quantidade válida", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Nenhum produto selecionado", Toast.LENGTH_SHORT).show();
        }
    }

    public void removerProduto(View view) {
        Produto produto = adapterProduto.getProdutoSelecionado();
        if (produto != null) {
            Carrinho.getInstance().removerProduto(produto);
            adapterProduto.notifyDataSetChanged();
            atualizarTotal();
            Toast.makeText(this, "Produto removido com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Nenhum produto selecionado", Toast.LENGTH_SHORT).show();
        }
    }

    public void recuperarItem(View view) {
        List<Produto> produtosRemovidos = Carrinho.getInstance().getProdutosRemovidos();
        if (!produtosRemovidos.isEmpty()) {
            Produto produtoRecuperado = produtosRemovidos.get(produtosRemovidos.size() - 1);
            Carrinho.getInstance().adicionarProduto(produtoRecuperado);
            adapterProduto.notifyDataSetChanged();
            atualizarTotal();
            Toast.makeText(this, "Produto recuperado com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Nenhum produto removido para recuperar", Toast.LENGTH_SHORT).show();
        }
    }

    private void atualizarTotal() {
        double total = Carrinho.getInstance().calcularTotal();
        textTotal.setText(String.format("Total: R$ %.2f", total));
    }

    public void menu(View view) {
        Intent in = new Intent(tela_carrinho.this, tela_menu_geral.class);
        startActivity(in);
    }
}