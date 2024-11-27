package com.example.pim_mundo_verde.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pim_mundo_verde.R;

public class tela_home extends AppCompatActivity {

    private ImageView imageView;
    private int[] images = {R.drawable.frutas, R.drawable.alfaces, R.drawable.logo_mv, R.drawable.logo}; // Substitua com suas imagens reais
    private int currentImageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_home);

        // Inicializa o ImageView
        imageView = findViewById(R.id.imageView);

        // Configura o Handler para trocar a imagem a cada 30 segundos (30000 ms)
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Troca a imagem
                trocarImagem();
                // Chama o método novamente após alguns segundos
                handler.postDelayed(this, 2000);
            }
        };

        // Inicia o processo imediatamente
        handler.post(runnable);

        // Configura o listener para ajustar o padding com base na barra de status
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Método para trocar a imagem
    private void trocarImagem() {
        // Atualiza o índice da imagem para a próxima
        currentImageIndex = (currentImageIndex + 1) % images.length;
        // Troca a imagem no ImageView
        imageView.setImageResource(images[currentImageIndex]);
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