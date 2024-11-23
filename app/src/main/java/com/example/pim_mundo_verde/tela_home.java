package com.example.pim_mundo_verde;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class tela_home extends AppCompatActivity {

    private ImageView imageView;
    private int[] images = {R.drawable.frutas, R.drawable.alfaces, R.drawable.logo_mv, R.drawable.logo,}; // Substitua com suas imagens reais
    private int currentImageIndex = 0;

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_home);

        // Inicializa o ImageView
        imageView = findViewById(R.id.imageView);

        // Configura o GestureDetector para detectar os gestos de deslizar
        gestureDetector = new GestureDetector(this, new GestureListener());

        // Configura o Handler para trocar a imagem a cada 30 segundos (30000 ms)
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Troca a imagem
                trocarImagem();
                // Chama o método novamente após 30 segundos
                handler.postDelayed(this, 1500);
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

    // Classe interna para lidar com os gestos de deslizar
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // Detecta se foi um deslizar para a direita ou para a esquerda
            if (e1.getX() < e2.getX()) {
                // Deslizou para a direita (troca a imagem)
                trocarImagem();
            } else if (e1.getX() > e2.getX()) {
                // Deslizou para a esquerda (troca a imagem)
                trocarImagem();
            }
            return true;
        }
    }

    // Captura os gestos de toque na tela para permitir o deslizar
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
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
