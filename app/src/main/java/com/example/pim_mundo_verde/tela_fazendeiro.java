package com.example.pim_mundo_verde;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class tela_fazendeiro extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextTelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Para trabalhar com a borda da tela
        setContentView(R.layout.activity_tela_fazendeiro);

        // Inicializa os campos de edição
        editTextNome = findViewById(R.id.editText_nome);
        editTextEmail = findViewById(R.id.editText_email);
        editTextTelefone = findViewById(R.id.editText_telefone);

        // Aplica o padding para evitar sobreposição com a barra de status
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Método para voltar à tela inicial
    public void inicio(View view) {
        Intent in = new Intent(tela_fazendeiro.this, tela_home.class); // Ajuste para sua tela inicial
        startActivity(in);
        finish(); // Opcional: fecha a tela atual para evitar voltar
    }

    // Método para navegar ao menu geral
    public void menu(View view) {
        Intent in = new Intent(tela_fazendeiro.this, tela_menu_geral.class);
        startActivity(in);
    }

    // Método para sair e voltar à tela principal (MainActivity)
    public void sair(View view) {
        Intent in = new Intent(tela_fazendeiro.this, MainActivity.class);
        startActivity(in);
        finish(); // Opcional: fechar a tela atual
    }

    // Método para salvar as informações do usuário
    public void salvar(View view) {
        // Captura os dados inseridos nos campos
        String nome = editTextNome.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String telefone = editTextTelefone.getText().toString().trim();

        // Validação simples dos campos
        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validação do e-mail (simples)
        if (!email.contains("@") || !email.contains(".")) {
            Toast.makeText(this, "Por favor, insira um e-mail válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validação do telefone (simples - apenas um número mínimo de dígitos)
        if (telefone.length() < 10) {
            Toast.makeText(this, "Por favor, insira um telefone válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Aqui você pode adicionar a lógica para salvar as informações do usuário
        // Por exemplo, usando SharedPreferences ou um banco de dados SQLite.

        // Exemplo de exibição de uma mensagem de sucesso
        Toast.makeText(this, "Informações salvas com sucesso!", Toast.LENGTH_SHORT).show();

        // Aqui você pode navegar para outra tela ou manter o usuário na mesma tela
        // Exemplo de navegação para a tela de menu
        Intent in = new Intent(tela_fazendeiro.this, tela_menu_geral.class);
        startActivity(in);
        finish();
    }
}