package com.example.pim_mundo_verde.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pim_mundo_verde.Adapter.AdapterProduto;
import com.example.pim_mundo_verde.Adapter.AdpterCampo;
import com.example.pim_mundo_verde.Campo;
import com.example.pim_mundo_verde.R;
import com.example.pim_mundo_verde.model.Produto;  // Corrigido para importar Produto

import java.util.ArrayList;
import java.util.List;

public class tela_fechamento extends AppCompatActivity {

    private RecyclerView recyclerViewCampos;
    private RecyclerView recyclerViewProdutos;
    private AdpterCampo adpterCampo;
    private AdapterProduto adpterProduto;
    private ArrayList<Campo> listaCampos;  // Lista de objetos Campo
    private ArrayList<Produto> listaProdutos;  // Lista de objetos Produto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_fechamento);

        // Inicializando as listas
        listaCampos = new ArrayList<>();
        listaProdutos = new ArrayList<>();

        // Criando os objetos que serão passados para os adaptadores
        // Exemplo de como você pode adicionar campos e produtos às listas
        // (esses objetos podem ser instanciados com dados reais)

        listaCampos.add(new com.example.pim_mundo_verde.Campo("Plantação de Alfaces",
                "Quantidade de mudas: 100\nTamanho: 500 m²\nQualidade do solo: Boa",
                "Status: Pronto para colheita",
                "Última colheita: 01/10/2024\nPróxima previsão: 15/11/2024",
                "Localização:\n- Longitude: -45.123456\n- Latitude: -23.987654"));

        listaCampos.add(new com.example.pim_mundo_verde.Campo("Plantação de Tomates",
                "Quantidade de mudas: 150\nTamanho: 300 m²\nQualidade do solo: Excelente",
                "Status: Em crescimento",
                "Última colheita: 10/10/2024\nPróxima previsão: 20/11/2024",
                "Localização:\n- Longitude: -45.123456\n- Latitude: -23.987654"));

        List<Produto> listaProdutos = new ArrayList<>();
        listaProdutos.add(new Produto("id_beterraba", R.drawable.beterraba, "Beringela", "Beringela nutritiva, ótima para saladas e sucos.", "R$ 8,00"));
        listaProdutos.add(new Produto("id_cenoura", R.drawable.cenoura, "Cenoura", "Cenoura fresca, excelente para melhorar a visão.", "R$ 6,50"));
        listaProdutos.add(new Produto("id_tomate", R.drawable.tomate, "Tomate", "Tomate orgânico, ideal para saladas e molhos.", "R$ 4,00"));
        listaProdutos.add(new Produto("id_beterraba", R.drawable.beterraba, "Beringela", "Beringela nutritiva, ótima para saladas e sucos.", "R$ 8,00"));
        listaProdutos.add(new Produto("id_cenoura", R.drawable.cenoura, "Cenoura", "Cenoura fresca, excelente para melhorar a visão.", "R$ 6,50"));
        listaProdutos.add(new Produto("id_tomate", R.drawable.tomate, "Tomate", "Tomate orgânico, ideal para saladas e molhos.", "R$ 4,00"));

        // Inicializando os adaptadores
        adpterCampo = new AdpterCampo(listaCampos);  // Passando lista de Campos
        adpterProduto = new AdapterProduto(listaProdutos, new AdapterProduto.OnItemClickListener() {
            @Override
            public void onItemClick(Produto produto) {
            }
        });

        // Configuração do RecyclerView para exibir produtos
        recyclerViewProdutos = findViewById(R.id.recyclerViewProdutos);
        recyclerViewProdutos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewProdutos.setHasFixedSize(true);
        recyclerViewProdutos.setAdapter(adpterProduto);

        // Configuração do RecyclerView para exibir campos
        recyclerViewCampos = findViewById(R.id.recyclerViewCampos);
        recyclerViewCampos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewCampos.setHasFixedSize(true);
        recyclerViewCampos.setAdapter(adpterCampo);
    }

    // Métodos para navegar entre as telas
    public void inicio(View view) {
        Intent in = new Intent(tela_fechamento.this, tela_home.class);
        startActivity(in);
    }

    public void menu(View view) {
        Intent in = new Intent(tela_fechamento.this, tela_menu_geral.class);
        startActivity(in);
    }

    public void fazendeiro(View view) {
        Intent in = new Intent(tela_fechamento.this, tela_fazendeiro.class);
        startActivity(in);
    }
}
