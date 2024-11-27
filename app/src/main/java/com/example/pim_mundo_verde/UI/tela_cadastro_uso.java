package com.example.pim_mundo_verde.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pim_mundo_verde.R;

public class tela_cadastro_uso extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_cadastro_uso);

        // Configurações de layout com EdgeToEdge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void Cad2(View view) {
        // Recupera os valores dos campos de entrada
        EditText nomeEditText = findViewById(R.id.editText_nome);
        EditText emailEditText = findViewById(R.id.editText_email);
        EditText senhaEditText = findViewById(R.id.editText_senha);
        EditText cepEditText = findViewById(R.id.editText_cep);
        EditText efEditText = findViewById(R.id.editText_ef);
        EditText paisEditText = findViewById(R.id.editText_pais);

        String nome = nomeEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String senha = senhaEditText.getText().toString();
        String cep = cepEditText.getText().toString();
        String ef = efEditText.getText().toString();
        String pais = paisEditText.getText().toString();

        // Cria uma Intent para ir à próxima atividade
        Intent in = new Intent(tela_cadastro_uso.this, tela_cadastro_uso1.class);

        // Passa os valores para a próxima atividade
        in.putExtra("nome", nome);
        in.putExtra("email", email);
        in.putExtra("senha", senha);
        in.putExtra("cep", cep);
        in.putExtra("ef", ef);
        in.putExtra("pais", pais);

        // Inicia a nova atividade
        startActivity(in);
    }

    public void principal(View view) {
        Intent in = new Intent(tela_cadastro_uso.this, MainActivity.class);
        startActivity(in);
    }
}