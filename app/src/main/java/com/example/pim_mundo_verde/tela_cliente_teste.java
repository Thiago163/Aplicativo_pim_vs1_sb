package com.example.pim_mundo_verde;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pim_mundo_verde.model.Cliente;
import com.example.pim_mundo_verde.services.ClienteApiService;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe MainActivity
 *
 * Propósito: A MainActivity serve como interface gráfica para o usuário interagir com a API de Clientes.
 * Ela permite carregar, adicionar, atualizar e deletar clientes, exibindo os dados no Spinner e permitindo
 * a edição dos campos de nome e email.
 *
 * Nota Importante: **A API DEVE ESTAR RODANDO NO ECLIPSE** antes de usar esta aplicação. Caso contrário,
 * os métodos que fazem requisições à API (como carregar, adicionar, atualizar e deletar clientes) irão
 * retornar erro ao tentar conectar-se ao servidor. Verifique se o servidor da API está ativo para evitar
 * erros de conexão.
 */
public class tela_cliente_teste extends AppCompatActivity {

    private static final String TAG = "NetworkCheck"; // Tag para logs de diagnóstico de rede.
    private Spinner spinnerClientes; // Dropdown para exibir a lista de clientes.
    private EditText editTextId, editTextNome, editTextEmail; // Campos de entrada para ID, Nome e Email do cliente.
    private Button buttonLoadClientes, buttonAddCliente, buttonUpdateCliente, buttonDeleteCliente; // Botões para ações CRUD.
    private List<Cliente> clientes = new ArrayList<>(); // Lista de objetos Cliente para armazenar dados carregados.
    private ArrayAdapter<String> adapter; // Adaptador para manipular o Spinner.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cliente_teste); // Define o layout da atividade.

        // Log de diagnóstico de rede para verificar a conexão com o servidor da API.
        Log.d(TAG, "Verificando configurações de rede...");
        new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2:8085/api/clientes/listar"); // URL do endpoint da API.
                HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // Abre conexão HTTP.
                connection.setRequestMethod("GET"); // Define o método HTTP como GET.
                connection.connect(); // Conecta ao servidor.

                int responseCode = connection.getResponseCode(); // Código de resposta da conexão.
                if (responseCode == 200) {
                    Log.d(TAG, "Conexão HTTP com 10.0.2.2 bem-sucedida.");
                } else {
                    Log.e(TAG, "Conexão HTTP falhou. Código de resposta: " + responseCode);
                }
                connection.disconnect(); // Fecha a conexão.
            } catch (Exception e) {
                Log.e(TAG, "Erro ao verificar a conexão HTTP com 10.0.2.2: " + e.getMessage());
            }
        }).start();

        // Inicializar views com base nos IDs definidos no layout XML.
        spinnerClientes = findViewById(R.id.spinnerClientes);
        editTextId = findViewById(R.id.editTextId);
        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonLoadClientes = findViewById(R.id.buttonLoadClientes);
        buttonAddCliente = findViewById(R.id.buttonAddCliente);
        buttonUpdateCliente = findViewById(R.id.buttonUpdateCliente);
        buttonDeleteCliente = findViewById(R.id.buttonDeleteCliente);

        // Configuração do Spinner e seu adaptador para exibir os nomes dos clientes.
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClientes.setAdapter(adapter); // Define o adaptador do Spinner.

        // Define ações para os botões.
        buttonLoadClientes.setOnClickListener(v -> loadClientes()); // Carregar clientes ao clicar no botão.
        buttonAddCliente.setOnClickListener(v -> addCliente()); // Adicionar cliente ao clicar no botão.
        buttonUpdateCliente.setOnClickListener(v -> updateCliente()); // Atualizar cliente ao clicar no botão.
        buttonDeleteCliente.setOnClickListener(v -> deleteCliente()); // Deletar cliente ao clicar no botão.

        // Define ação de seleção do Spinner, para exibir informações do cliente selecionado nos EditTexts.
        spinnerClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtém o cliente selecionado e exibe os dados nos campos de texto.
                Cliente cliente = clientes.get(position);
                editTextId.setText(String.valueOf(cliente.getId()));
                editTextNome.setText(cliente.getNome());
                editTextEmail.setText(cliente.getEmail());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Limpa os campos de texto quando nada está selecionado no Spinner.
                editTextId.setText("");
                editTextNome.setText("");
                editTextEmail.setText("");
            }
        });
    }

    /**
     * Método loadClientes()
     * Carrega a lista de clientes chamando o serviço da API. Atualiza o Spinner com os nomes dos clientes.
     */
    private void loadClientes() {
        new Thread(() -> {
            try {
                clientes = ClienteApiService.getAllClientes(); // Chama o serviço da API para obter todos os clientes.
                runOnUiThread(() -> {
                    adapter.clear(); // Limpa os dados do adaptador.
                    for (Cliente cliente : clientes) {
                        adapter.add(cliente.getNome()); // Adiciona o nome de cada cliente ao adaptador.
                    }
                    adapter.notifyDataSetChanged(); // Notifica o Spinner sobre as mudanças.
                    Toast.makeText(tela_cliente_teste.this, "Clientes carregados", Toast.LENGTH_SHORT).show(); // Mensagem de sucesso.
                });
            } catch (Exception e) {
                e.printStackTrace(); // Exibe erro no console.
                runOnUiThread(() -> Toast.makeText(tela_cliente_teste.this, "Erro ao carregar clientes", Toast.LENGTH_SHORT).show()); // Mensagem de erro.
            }
        }).start();
    }

    /**
     * Método addCliente()
     * Adiciona um novo cliente chamando o serviço da API. Exibe uma mensagem de sucesso ou erro.
     */
    private void addCliente() {
        Cliente cliente = new Cliente(); // Cria um novo cliente.
        cliente.setNome(editTextNome.getText().toString()); // Define o nome a partir do campo de texto.
        cliente.setEmail(editTextEmail.getText().toString()); // Define o email a partir do campo de texto.

        new Thread(() -> {
            try {
                String response = ClienteApiService.addCliente(cliente); // Chama o serviço para adicionar o cliente.
                runOnUiThread(() -> {
                    Toast.makeText(tela_cliente_teste.this, response, Toast.LENGTH_SHORT).show(); // Mensagem de sucesso.
                    loadClientes(); // Atualiza a lista de clientes.
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(tela_cliente_teste.this, "Erro ao adicionar cliente", Toast.LENGTH_SHORT).show()); // Mensagem de erro.
            }
        }).start();
    }

    /**
     * Método updateCliente()
     * Atualiza o cliente selecionado chamando o serviço da API. Exibe uma mensagem de sucesso ou erro.
     */
    private void updateCliente() {
        long id = Long.parseLong(editTextId.getText().toString()); // Obtém o ID do cliente a ser atualizado.
        Cliente cliente = new Cliente(); // Cria um novo cliente com dados atualizados.
        cliente.setNome(editTextNome.getText().toString());
        cliente.setEmail(editTextEmail.getText().toString());

        new Thread(() -> {
            try {
                String response = ClienteApiService.updateCliente(id, cliente); // Chama o serviço para atualizar o cliente.
                runOnUiThread(() -> {
                    Toast.makeText(tela_cliente_teste.this, response, Toast.LENGTH_SHORT).show(); // Mensagem de sucesso.
                    loadClientes(); // Atualiza a lista de clientes.
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(tela_cliente_teste.this, "Erro ao atualizar cliente", Toast.LENGTH_SHORT).show()); // Mensagem de erro.
            }
        }).start();
    }

    /**
     * Método deleteCliente()
     * Deleta o cliente selecionado chamando o serviço da API. Exibe uma mensagem de sucesso ou erro.
     */
    private void deleteCliente() {
        long id = Long.parseLong(editTextId.getText().toString()); // Obtém o ID do cliente a ser deletado.

        new Thread(() -> {
            try {
                String response = ClienteApiService.deleteCliente(id); // Chama o serviço para deletar o cliente.
                runOnUiThread(() -> {
                    Toast.makeText(tela_cliente_teste.this, response, Toast.LENGTH_SHORT).show(); // Mensagem de sucesso.
                    loadClientes(); // Atualiza a lista de clientes.
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(tela_cliente_teste.this, "Erro ao deletar cliente", Toast.LENGTH_SHORT).show()); // Mensagem de erro.
            }
        }).start();
    }
}
