package com.example.pim_mundo_verde;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pim_mundo_verde.model.Fazendeiro;
import com.example.pim_mundo_verde.services.FazendeiroApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class tela_fazendeiro extends AppCompatActivity {

    // Campos de entrada
    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextTelefone;
    private EditText editTextDocumento;
    private EditText editTextTipoDocumento;
    private EditText editTextSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_fazendeiro);

        // Inicializa os campos de edição
        editTextNome = findViewById(R.id.editText_nome);
        editTextEmail = findViewById(R.id.editText_email);
        editTextTelefone = findViewById(R.id.editText_telefone);
        editTextDocumento = findViewById(R.id.editText_documento);
        editTextTipoDocumento = findViewById(R.id.editText_tipo_documento);
        editTextSenha = findViewById(R.id.editText_senha);
    }

    // Método para salvar as informações do fazendeiro na API
    public void salvar(View view) {
        // Captura os dados inseridos nos campos
        String nome = editTextNome.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String telefone = editTextTelefone.getText().toString().trim();
        String documento = editTextDocumento.getText().toString().trim();
        String tipoDocumento = editTextTipoDocumento.getText().toString().trim();
        String senha = editTextSenha.getText().toString().trim();

        // Validação simples dos campos (já mostrada antes)
        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || documento.isEmpty() || tipoDocumento.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validações omitidas aqui por simplicidade (como email, telefone, documento, etc)

        // Criando o objeto Fazendeiro
        Fazendeiro fazendeiro = new Fazendeiro(nome, email, telefone, documento, tipoDocumento, senha);

        // Configurando o Retrofit para fazer a requisição
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://<ip_do_servidor>:8085/api/fazendeiros") // URL base da sua API (substitua pelo seu servidor de produção ou desenvolvimento)
                .addConverterFactory(GsonConverterFactory.create()) // Converter resposta JSON para objetos
                .build();

        // Criando a instância da interface da API
        FazendeiroApiService apiService = retrofit.create(FazendeiroApiService.class);

        // Fazendo a requisição POST para salvar o fazendeiro
        Call<Void> call = apiService.salvarFazendeiro(fazendeiro);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Se a requisição for bem-sucedida
                    Toast.makeText(tela_fazendeiro.this, "Informações salvas com sucesso!", Toast.LENGTH_SHORT).show();
                    // Redireciona para outra tela, como tela_menu_geral
                    Intent in = new Intent(tela_fazendeiro.this, tela_menu_geral.class);
                    startActivity(in);
                    finish();
                } else {
                    // Se houver erro na requisição
                    Toast.makeText(tela_fazendeiro.this, "Erro ao salvar informações.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Se ocorrer erro de rede ou outro erro
                Toast.makeText(tela_fazendeiro.this, "Falha na conexão.", Toast.LENGTH_SHORT).show();
            }
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
}