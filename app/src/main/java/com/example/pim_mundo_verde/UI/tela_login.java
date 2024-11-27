package com.example.pim_mundo_verde.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.Color;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pim_mundo_verde.R;

import java.util.regex.Pattern;

public class tela_login extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextSenha;
    private ImageView imgVisibility;
    private boolean isPasswordVisible = false;

    // E-mail e senha padrão para autenticação
    private static final String EMAIL_PADRAO = "thiago@gmail.com";
    private static final String SENHA_PADRAO = "senha123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_login);

        // Inicializa os campos de entrada
        editTextEmail = findViewById(R.id.editText_email);
        editTextSenha = findViewById(R.id.editTextSenha);
        imgVisibility = findViewById(R.id.img_visibility);

        // Adiciona listener para aplicar insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Adiciona um TextWatcher para validar o e-mail à medida que o usuário digita
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Valida o formato do e-mail
                if (!isEmailValid(charSequence.toString())) {
                    editTextEmail.setTextColor(Color.RED); // Cor do texto vermelho se inválido
                } else {
                    editTextEmail.setTextColor(Color.BLACK); // Cor do texto preta se válido
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    // Função para verificar se o e-mail é válido
    private boolean isEmailValid(String email) {
        // Expressão regular simples para validar um e-mail
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public void togglePasswordVisibility(View view) {
        if (isPasswordVisible) {
            editTextSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            imgVisibility.setImageResource(R.drawable.ic_visibility_off); // Ícone de olho fechado
        } else {
            editTextSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            imgVisibility.setImageResource(R.drawable.ic_visibility); // Ícone de olho aberto
        }
        editTextSenha.setSelection(editTextSenha.length()); // Manter o cursor no final
        isPasswordVisible = !isPasswordVisible;
    }

    public void home(View view) {
        String email = editTextEmail.getText().toString().trim();
        String senha = editTextSenha.getText().toString().trim();

        // Validação simples dos campos
        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificação do e-mail e senha com os valores padrão
        if (!email.equals(EMAIL_PADRAO) || !senha.equals(SENHA_PADRAO)) {
            Toast.makeText(this, "E-mail ou senha inválidos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Se a autenticação for bem-sucedida, navega para a tela inicial
        Intent in = new Intent(tela_login.this, tela_home.class);
        startActivity(in);
        finish(); // Finaliza a atividade de login para que não seja possível voltar a ela
    }

    public void esquecisenha(View view) {
        Intent in = new Intent(tela_login.this, tela_esqueci_senha.class);
        startActivity(in);
    }

    public void cadastre(View view) {
        Intent in = new Intent(tela_login.this, tela_cadastro_uso.class);
        startActivity(in);
    }
}