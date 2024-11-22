package com.example.pim_mundo_verde;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class tela_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void colheita(View view) {
        Intent in = new Intent(tela_home.this, tela_inf_colheita.class); // Use o nome correto da classe
        startActivity(in);
    }

    public void adubo(View view) {
        Intent in = new Intent(tela_home.this, tela_inf_adubo.class); // Use o nome correto da classe
        startActivity(in);
    }

    public void agua(View view) {
        Intent in = new Intent(tela_home.this, tela_inf_agua.class); // Use o nome correto da classe
        startActivity(in);
    }

    public void fazendeiro(View view) {
        Intent in = new Intent(tela_home.this, tela_fazendeiro.class); // Use o nome correto da classe
        startActivity(in);
    }

    public void menu(View view) {
        Intent in = new Intent(tela_home.this, tela_menu_geral.class); // Use o nome correto da classe
        startActivity(in);
    }

    public void Controle_campos(View view) {
        Intent in = new Intent(tela_home.this, tela_vendas.class); // Use o nome correto da classe
        startActivity(in);
    }
}