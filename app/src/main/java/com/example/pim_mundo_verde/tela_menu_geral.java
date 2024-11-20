package com.example.pim_mundo_verde;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class tela_menu_geral extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_menu_geral);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void fazendeiro(View view) {
        Intent in = new Intent(tela_menu_geral.this, tela_fazendeiro.class); // Use o nome correto da classe
        startActivity(in);
    }

    public void inicio(View view) {
        Intent in = new Intent(tela_menu_geral.this, tela_home.class); // Use o nome correto da classe
        startActivity(in);
    }

    public void vendas(View view) {
        Intent in = new Intent(tela_menu_geral.this, tela_vendas.class); // Use o nome correto da classe
        startActivity(in);
    }

    public void campos(View view) {
        Intent in = new Intent(tela_menu_geral.this, tela_controle_campos.class); // Use o nome correto da classe
        startActivity(in);
    }

    public void carrinho(View view) {
        Intent in = new Intent(tela_menu_geral.this, tela_carrinho.class); // Use o nome correto da classe
        startActivity(in);
    }

    public void fechamento(View view) {
        Intent in = new Intent(tela_menu_geral.this, tela_fechamento.class); // Use o nome correto da classe
        startActivity(in);
    }
}