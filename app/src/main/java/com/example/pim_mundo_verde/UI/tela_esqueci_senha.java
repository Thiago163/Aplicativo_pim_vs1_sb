package com.example.pim_mundo_verde.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Patterns;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pim_mundo_verde.R;

public class tela_esqueci_senha extends AppCompatActivity {

    private EditText editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_esqueci_senha);

        // Inicializando o EditText
        editTextEmail = findViewById(R.id.editTextEmail);
    }

    // Método para voltar para a tela de login
    public void principal(View view) {
        Intent intent = new Intent(tela_esqueci_senha.this, tela_login.class);
        startActivity(intent);
        finish();
    }

    // Método para recuperar a senha
    public void recuperarSenha(View view) {
        String email = editTextEmail.getText().toString().trim();

        // Verificando se o campo de e-mail está vazio
        if (email.isEmpty()) {
            Toast.makeText(this, "Por favor, insira seu e-mail.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificando se o e-mail inserido é válido
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Por favor, insira um e-mail válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lógica de recuperação de senha
        // Neste caso, vamos simular o envio de um e-mail. Em um aplicativo real, você precisaria de integração com um servidor para isso.
        enviarEmailRecuperacao(email);
    }

    // Simulação de envio de e-mail
    private void enviarEmailRecuperacao(String email) {
        // Aqui você poderia usar uma API real para enviar um e-mail para o usuário.
        // Para o exemplo, vamos apenas mostrar um Toast confirmando o envio.
        Toast.makeText(this, "Instruções para recuperação de senha enviadas para " + email, Toast.LENGTH_LONG).show();

        // Após enviar o e-mail (simulado), voltamos para a tela de login
        Intent intent = new Intent(tela_esqueci_senha.this, tela_login.class);
        startActivity(intent);
        finish();
    }
}