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

public class tela_cadastro_uso1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_cadastro_uso1);

        // Configurações de layout com EdgeToEdge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void Cad1(View view) {
        Intent intent = new Intent(tela_cadastro_uso1.this, tela_cadastro_uso2.class);

        // Recupera os valores dos campos de entrada
        String telefone = ((EditText) findViewById(R.id.editText_nome)).getText().toString();
        String cidade = ((EditText) findViewById(R.id.editText_cidade)).getText().toString();
        String numero = ((EditText) findViewById(R.id.editText_numero)).getText().toString();
        String rua = ((EditText) findViewById(R.id.editText_rua)).getText().toString();
        String tipoDocumento = ((EditText) findViewById(R.id.editText_tipo_documento)).getText().toString();
        String documento = ((EditText) findViewById(R.id.editText_documento)).getText().toString();

        // Passando os dados para a próxima atividade
        intent.putExtra("telefone", telefone);
        intent.putExtra("cidade", cidade);
        intent.putExtra("numero", numero);
        intent.putExtra("rua", rua);
        intent.putExtra("tipo_documento", tipoDocumento);
        intent.putExtra("documento", documento);

        // Passando também os dados da tela anterior
        intent.putExtra("nome", getIntent().getStringExtra("nome"));
        intent.putExtra("email", getIntent().getStringExtra("email"));
        intent.putExtra("senha", getIntent().getStringExtra("senha"));
        intent.putExtra("cep", getIntent().getStringExtra("cep"));
        intent.putExtra("ef", getIntent().getStringExtra("ef"));
        intent.putExtra("pais", getIntent().getStringExtra("pais"));

        startActivity(intent);
    }

    public void Cad(View view) {
        Intent intent = new Intent(tela_cadastro_uso1.this, tela_cadastro_uso.class);
        startActivity(intent);
    }
}
