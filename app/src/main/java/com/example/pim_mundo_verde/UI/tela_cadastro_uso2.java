package com.example.pim_mundo_verde.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pim_mundo_verde.R;

public class tela_cadastro_uso2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_cadastro_uso2);

        // Define insets para a tela principal
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Recebe os dados passados pela Intent
        Intent intent = getIntent();
        String nome = intent.getStringExtra("nome");
        String email = intent.getStringExtra("email");
        String senha = intent.getStringExtra("senha");
        String cep = intent.getStringExtra("cep");
        String ef = intent.getStringExtra("ef");
        String pais = intent.getStringExtra("pais");
        String tipoDocumento = intent.getStringExtra("tipo_documento");
        String documento = intent.getStringExtra("documento");
        String telefone = intent.getStringExtra("telefone");
        String cidade = intent.getStringExtra("cidade");
        String numero = intent.getStringExtra("numero");
        String rua = intent.getStringExtra("rua");

        // Encontra as TextViews e define os dados recebidos
        TextView nomeTextView = findViewById(R.id.textView_nome);
        TextView emailTextView = findViewById(R.id.textView_email);
        TextView senhaTextView = findViewById(R.id.textView_senha);
        TextView cepTextView = findViewById(R.id.textView_cep);
        TextView efTextView = findViewById(R.id.textView_ef);
        TextView paisTextView = findViewById(R.id.textView_pais);
        TextView tipoDocumentoTextView = findViewById(R.id.textView_tipo_documento);
        TextView documentoTextView = findViewById(R.id.textView_documento);
        TextView telefoneTextView = findViewById(R.id.textView_telefone);
        TextView cidadeTextView = findViewById(R.id.textView_cidade);
        TextView numeroTextView = findViewById(R.id.textView_numero);
        TextView ruaTextView = findViewById(R.id.textView_rua);

        // Define os textos, garantindo que não sejam nulos ou vazios
        nomeTextView.setText(!TextUtils.isEmpty(nome) ? nome : "Não informado");
        emailTextView.setText(!TextUtils.isEmpty(email) ? email : "Não informado");
        senhaTextView.setText(!TextUtils.isEmpty(senha) ? senha : "Não informado");
        cepTextView.setText(!TextUtils.isEmpty(cep) ? cep : "Não informado");
        efTextView.setText(!TextUtils.isEmpty(ef) ? ef : "Não informado");
        paisTextView.setText(!TextUtils.isEmpty(pais) ? pais : "Não informado");
        tipoDocumentoTextView.setText(!TextUtils.isEmpty(tipoDocumento) ? tipoDocumento : "Não informado");
        documentoTextView.setText(!TextUtils.isEmpty(documento) ? documento : "Não informado");
        telefoneTextView.setText(!TextUtils.isEmpty(telefone) ? telefone : "Não informado");
        cidadeTextView.setText(!TextUtils.isEmpty(cidade) ? cidade : "Não informado");
        numeroTextView.setText(!TextUtils.isEmpty(numero) ? numero : "Não informado");
        ruaTextView.setText(!TextUtils.isEmpty(rua) ? rua : "Não informado");

        // Log para verificar os valores recebidos
        Log.d("Cadastro", "Nome: " + nome + ", Email: " + email + ", Senha: " + senha +
                ", CEP: " + cep + ", EF: " + ef + ", País: " + pais);
    }

    // Método para ir para a tela inicial com validações
    public void home(View view) {
        // Recebe os dados passados pela Intent
        Intent intent = getIntent();
        String nome = intent.getStringExtra("nome");
        String email = intent.getStringExtra("email");
        String senha = intent.getStringExtra("senha");
        String telefone = intent.getStringExtra("telefone");

        // Valida se todos os campos obrigatórios foram preenchidos
        if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(email) || TextUtils.isEmpty(senha) || TextUtils.isEmpty(telefone)) {
            // Exibe mensagem de erro se algum campo estiver vazio
            Toast.makeText(this, "Por favor, preencha todos os campos obrigatórios.", Toast.LENGTH_SHORT).show();
            return; // Impede a navegação para a próxima tela
        }

        // Verificação simples para o formato do e-mail
        if (!email.contains("@") || !email.contains(".")) {
            Toast.makeText(this, "Por favor, insira um e-mail válido.", Toast.LENGTH_SHORT).show();
            return; // Impede a navegação para a próxima tela
        }

        // Verificação simples para o telefone (mínimo de 10 caracteres)
        if (telefone.length() < 10) {
            Toast.makeText(this, "Por favor, insira um telefone válido.", Toast.LENGTH_SHORT).show();
            return; // Impede a navegação para a próxima tela
        }

        // Se todos os campos estão preenchidos corretamente, navega para a tela inicial
        Intent in = new Intent(tela_cadastro_uso2.this, tela_home.class);
        // Passa os dados para a próxima tela
        in.putExtra("nome", nome);
        in.putExtra("email", email);
        in.putExtra("senha", senha);
        in.putExtra("telefone", telefone);
        startActivity(in);
    }

    // Método para navegar de volta para a tela de cadastro 1
    public void cadastro2(View view) {
        Intent in = new Intent(tela_cadastro_uso2.this, tela_cadastro_uso1.class);
        startActivity(in);
    }
}