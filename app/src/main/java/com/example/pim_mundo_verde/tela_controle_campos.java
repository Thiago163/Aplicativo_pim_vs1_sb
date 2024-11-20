package com.example.pim_mundo_verde;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pim_mundo_verde.Adapter.AdpterCampo;

import java.util.ArrayList;
import java.util.List;

public class tela_controle_campos extends AppCompatActivity {

    private AdpterCampo adpterCampo;
    private List<com.example.pim_mundo_verde.Campo> listaCampos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_controle_campos);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializando RecyclerView e lista de campos
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        listaCampos = new ArrayList<>();
        adpterCampo = new AdpterCampo(listaCampos);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adpterCampo);

        // Adicionando campos de exemplo
        adicionarCamposExemplo();
    }

    private void adicionarCamposExemplo() {
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

        // Notifica o adaptador sobre a mudança de dados
        adpterCampo.notifyDataSetChanged();
    }

    public void inicio(View view) {
        Intent in = new Intent(tela_controle_campos.this, tela_home.class);
        startActivity(in);
    }

    public void fazendeiro(View view) {
        Intent in = new Intent(tela_controle_campos.this, tela_fazendeiro.class);
        startActivity(in);
    }

    public void menu(View view) {
        Intent in = new Intent(tela_controle_campos.this, tela_menu_geral.class);
        startActivity(in);
    }
}
