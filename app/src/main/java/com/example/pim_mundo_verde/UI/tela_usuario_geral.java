package com.example.pim_mundo_verde.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pim_mundo_verde.R;
import com.example.pim_mundo_verde.model.Usuario;
import com.example.pim_mundo_verde.services.UsuarioApiService;

import java.util.ArrayList;
import java.util.List;

public class tela_usuario_geral extends AppCompatActivity {

    private Spinner usuarioList, usuarioTipoDocumento;
    private EditText usuarioId, usuarioDocumento, usuarioNome, usuarioEmail, usuarioTelefone, usuarioSenha;
    private Button loadUsuariosButton, addUsuarioButton, updateUsuarioButton, deleteUsuarioButton;
    private TextView message;

    private UsuarioApiService usuarioApiService = new UsuarioApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_usuario_geral);

        // Inicializa os componentes da tela
        usuarioList = findViewById(R.id.usuarioList);
        usuarioTipoDocumento = findViewById(R.id.usuarioTipoDocumento);
        usuarioId = findViewById(R.id.usuarioId);
        usuarioDocumento = findViewById(R.id.usuarioDocumento);
        usuarioNome = findViewById(R.id.usuarioNome);
        usuarioEmail = findViewById(R.id.usuarioEmail);
        usuarioTelefone = findViewById(R.id.usuarioTelefone);
        usuarioSenha = findViewById(R.id.usuarioSenha);
        loadUsuariosButton = findViewById(R.id.loadUsuariosButton);
        addUsuarioButton = findViewById(R.id.addUsuarioButton);
        updateUsuarioButton = findViewById(R.id.updateUsuarioButton);
        deleteUsuarioButton = findViewById(R.id.deleteUsuarioButton);
        message = findViewById(R.id.message);

        loadUsuariosButton.setOnClickListener(v -> loadUsuarios());
        addUsuarioButton.setOnClickListener(v -> addUsuario());
        updateUsuarioButton.setOnClickListener(v -> updateUsuario());
        deleteUsuarioButton.setOnClickListener(v -> deleteUsuario());
    }

    private void loadUsuarios() {
        // Carregar lista de usuários da API e atualizar o Spinner
        usuarioApiService.listarUsuarios(new UsuarioApiService.ApiCallback<List<Usuario>>() {
            @Override
            public void onSuccess(List<Usuario> usuarios) {
                // Verifique se a lista de usuários está vazia
                if (usuarios != null && !usuarios.isEmpty()) {
                    // Criar um ArrayAdapter para preencher o Spinner com os nomes dos usuários
                    List<String> nomesUsuarios = new ArrayList<>();
                    for (Usuario usuario : usuarios) {
                        nomesUsuarios.add(usuario.getNome()); // ou outro campo, como 'documento', conforme necessário
                    }

                    // Criar o ArrayAdapter
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(tela_usuario_geral.this, android.R.layout.simple_spinner_item, nomesUsuarios);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    usuarioList.setAdapter(adapter);

                    message.setVisibility(View.GONE); // Ocultar a mensagem de erro, se a lista foi carregada corretamente
                } else {
                    message.setText("Nenhum usuário encontrado.");
                    message.setVisibility(View.VISIBLE);
                    Toast.makeText(tela_usuario_geral.this, "Nenhum usuário encontrado.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String error) {
                // Exibir a mensagem de erro
                message.setText(error);
                message.setVisibility(View.VISIBLE);
                Toast.makeText(tela_usuario_geral.this, "Erro ao carregar os usuários: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addUsuario() {
        // Lógica para adicionar um novo usuário
        String documento = usuarioDocumento.getText().toString();
        String nome = usuarioNome.getText().toString();
        String email = usuarioEmail.getText().toString();
        String telefone = usuarioTelefone.getText().toString();
        String senha = usuarioSenha.getText().toString();
        String tipoDocumento = usuarioTipoDocumento.getSelectedItem().toString();

        if (documento.isEmpty() || nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || senha.isEmpty()) {
            message.setText("Por favor, preencha todos os campos.");
            message.setVisibility(View.VISIBLE);
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setDocumento(documento);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setTelefone(telefone);
        usuario.setSenha(senha);
        usuario.setTipoDocumento(tipoDocumento);

        usuarioApiService.adicionarUsuario(usuario, new UsuarioApiService.ApiCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(tela_usuario_geral.this, result, Toast.LENGTH_SHORT).show();
                clearFields(); // Limpar campos após adicionar
            }

            @Override
            public void onFailure(String error) {
                message.setText("Erro ao adicionar usuário: " + error);
                message.setVisibility(View.VISIBLE);
            }
        });
    }

    private void updateUsuario() {
        // Lógica para atualizar o usuário selecionado
        String idText = usuarioId.getText().toString();
        if (idText.isEmpty()) {
            message.setText("ID não pode estar vazio.");
            message.setVisibility(View.VISIBLE);
            return;
        }

        long id = Long.parseLong(idText); // Pegando o ID do usuário
        Usuario usuario = new Usuario();
        usuario.setDocumento(usuarioDocumento.getText().toString());
        usuario.setNome(usuarioNome.getText().toString());
        usuario.setEmail(usuarioEmail.getText().toString());
        usuario.setTelefone(usuarioTelefone.getText().toString());
        usuario.setSenha(usuarioSenha.getText().toString());
        usuario.setTipoDocumento(usuarioTipoDocumento.getSelectedItem().toString());

        // Passando o ID e o usuário para a API
        usuarioApiService.atualizarUsuario(id, usuario, new UsuarioApiService.ApiCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(tela_usuario_geral.this, result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String error) {
                message.setText("Erro ao atualizar usuário: " + error);
                message.setVisibility(View.VISIBLE);
            }
        });
    }

    private void deleteUsuario() {
        // Lógica para deletar o usuário selecionado
        String idText = usuarioId.getText().toString();
        if (idText.isEmpty()) {
            message.setText("ID não pode estar vazio.");
            message.setVisibility(View.VISIBLE);
            return;
        }

        long usuarioIdLong = Long.parseLong(idText);
        usuarioApiService.deletarUsuario(usuarioIdLong, new UsuarioApiService.ApiCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(tela_usuario_geral.this, result, Toast.LENGTH_SHORT).show();
                clearFields(); // Limpar campos após deletar
            }

            @Override
            public void onFailure(String error) {
                message.setText("Erro ao deletar usuário: " + error);
                message.setVisibility(View.VISIBLE);
            }
        });
    }

    private void clearFields() {
        // Limpar campos após a operação
        usuarioId.setText("");
        usuarioDocumento.setText("");
        usuarioNome.setText("");
        usuarioEmail.setText("");
        usuarioTelefone.setText("");
        usuarioSenha.setText("");
        usuarioTipoDocumento.setSelection(0); // Reseta o spinner
    }

    public void main(View view) {
        Intent in = new Intent(tela_usuario_geral.this, MainActivity.class); // Use o nome correto da classe
        startActivity(in);
    }
}