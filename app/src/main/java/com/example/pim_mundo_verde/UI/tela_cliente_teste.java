package com.example.pim_mundo_verde.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pim_mundo_verde.R;
import com.example.pim_mundo_verde.model.Cliente;
import com.example.pim_mundo_verde.services.ClienteApiService;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class tela_cliente_teste extends AppCompatActivity {

    private static final String TAG = "NetworkCheck"; // Tag para logs de rede
    private Spinner spinnerClientes;
    private EditText editTextId, editTextNome, editTextEmail;
    private Button buttonLoadClientes, buttonAddCliente, buttonUpdateCliente, buttonDeleteCliente;
    private List<Cliente> clientes = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cliente_teste);

        // Permitir operações de rede no thread principal temporariamente
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Log.d(TAG, "Verificando conexão com a API...");
        verificarConexaoAPI();

        inicializarComponentes();
        configurarSpinner();
        configurarBotoes();
    }

    private void verificarConexaoAPI() {
        new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2:8085/api/clientes/listar"); // Use o IP correto
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.d(TAG, "Conexão bem-sucedida com a API.");
                } else {
                    Log.e(TAG, "Falha na conexão com a API. Código: " + responseCode);
                }
                connection.disconnect();
            } catch (Exception e) {
                Log.e(TAG, "Erro ao conectar com a API: " + e.getMessage());
            }
        }).start();
    }

    private void inicializarComponentes() {
        spinnerClientes = findViewById(R.id.spinnerClientes);
        editTextId = findViewById(R.id.editTextId);
        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonLoadClientes = findViewById(R.id.buttonLoadClientes);
        buttonAddCliente = findViewById(R.id.buttonAddCliente);
        buttonUpdateCliente = findViewById(R.id.buttonUpdateCliente);
        buttonDeleteCliente = findViewById(R.id.buttonDeleteCliente);
    }

    private void configurarSpinner() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClientes.setAdapter(adapter);

        spinnerClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cliente cliente = clientes.get(position);
                editTextId.setText(String.valueOf(cliente.getId()));
                editTextNome.setText(cliente.getNome());
                editTextEmail.setText(cliente.getEmail());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                editTextId.setText("");
                editTextNome.setText("");
                editTextEmail.setText("");
            }
        });
    }

    private void configurarBotoes() {
        buttonLoadClientes.setOnClickListener(v -> loadClientes());
        buttonAddCliente.setOnClickListener(v -> addCliente());
        buttonUpdateCliente.setOnClickListener(v -> updateCliente());
        buttonDeleteCliente.setOnClickListener(v -> deleteCliente());
    }

    private void loadClientes() {
        new Thread(() -> {
            try {
                clientes = ClienteApiService.getAllClientes();
                runOnUiThread(() -> {
                    adapter.clear();
                    for (Cliente cliente : clientes) {
                        adapter.add(cliente.getNome());
                    }
                    adapter.notifyDataSetChanged();
                    Toast.makeText(tela_cliente_teste.this, "Clientes carregados", Toast.LENGTH_SHORT).show();
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(tela_cliente_teste.this, "Erro ao carregar clientes", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void addCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome(editTextNome.getText().toString());
        cliente.setEmail(editTextEmail.getText().toString());

        new Thread(() -> {
            try {
                String response = ClienteApiService.addCliente(cliente);
                runOnUiThread(() -> {
                    Toast.makeText(tela_cliente_teste.this, response, Toast.LENGTH_SHORT).show();
                    loadClientes();
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(tela_cliente_teste.this, "Erro ao adicionar cliente", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void updateCliente() {
        long id = Long.parseLong(editTextId.getText().toString());
        Cliente cliente = new Cliente();
        cliente.setNome(editTextNome.getText().toString());
        cliente.setEmail(editTextEmail.getText().toString());

        new Thread(() -> {
            try {
                String response = ClienteApiService.updateCliente(id, cliente);
                runOnUiThread(() -> {
                    Toast.makeText(tela_cliente_teste.this, response, Toast.LENGTH_SHORT).show();
                    loadClientes();
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(tela_cliente_teste.this, "Erro ao atualizar cliente", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void deleteCliente() {
        long id = Long.parseLong(editTextId.getText().toString());

        new Thread(() -> {
            try {
                String response = ClienteApiService.deleteCliente(id);
                runOnUiThread(() -> {
                    Toast.makeText(tela_cliente_teste.this, response, Toast.LENGTH_SHORT).show();
                    loadClientes();
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(tela_cliente_teste.this, "Erro ao deletar cliente", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    public void Main(View view) {
        Intent in = new Intent(tela_cliente_teste.this, MainActivity.class);
        startActivity(in);
    }
}
