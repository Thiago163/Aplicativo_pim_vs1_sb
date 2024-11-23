package com.example.pim_mundo_verde.services;

import android.os.AsyncTask;

import com.example.pim_mundo_verde.model.Cliente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe ClienteApiService
 *
 * Propósito: Esta classe fornece métodos para comunicação com a API REST de clientes.
 * Permite listar, adicionar, atualizar e deletar clientes ao enviar requisições HTTP
 * para o servidor.
 *
 * Uso: Esta classe é usada principalmente para operações CRUD (Create, Read, Update, Delete)
 * em clientes, onde cada método realiza uma operação específica de comunicação com a API.
 */

public class ClienteApiService {

    private static final String BASE_URL = "http://10.0.2.2:8085/api/clientes";
    //private static final String BASE_URL = "http://localhost:8085/api/clientes";
    // URL base para todas as requisições API. O endereço "10.0.2.2" é usado para o Android Emulator acessar o "localhost".

    /**
     * Método getAllClientes()
     *
     * Propósito: Envia uma requisição HTTP GET para obter todos os clientes cadastrados na API.
     *
     * @return List<Cliente> - Uma lista de objetos Cliente representando todos os clientes no banco de dados.
     * @throws Exception - Pode lançar exceções relacionadas a operações de rede e JSON.
     */
    public static List<Cliente> getAllClientes() throws Exception {
        URL url = new URL(BASE_URL + "/listar"); // Cria a URL completa para a operação de listagem.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Abre uma conexão HTTP.
        conn.setRequestMethod("GET"); // Define o método da requisição como GET.
        conn.setRequestProperty("Content-Type", "application/json"); // Define o tipo de conteúdo como JSON.

        // Buffer para ler a resposta da API.
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) { // Lê cada linha da resposta.
            result.append(line);
        }
        reader.close();

        List<Cliente> clientes = new ArrayList<>(); // Lista para armazenar os clientes.
        JSONArray jsonArray = new JSONArray(result.toString()); // Converte a resposta para um array JSON.
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i); // Extrai cada objeto JSON.
            Cliente cliente = new Cliente(); // Cria um novo objeto Cliente.
            cliente.setId(jsonObject.getLong("id")); // Define o ID do cliente.
            cliente.setNome(jsonObject.getString("nome")); // Define o nome do cliente.
            cliente.setEmail(jsonObject.getString("email")); // Define o email do cliente.
            clientes.add(cliente); // Adiciona o cliente à lista.
        }
        return clientes; // Retorna a lista completa de clientes.
    }

    /**
     * Método addCliente()
     *
     * Propósito: Envia uma requisição HTTP POST para adicionar um novo cliente à API.
     *
     * @param cliente - Objeto Cliente que contém os dados do cliente a ser adicionado.
     * @return String - Resposta da API indicando sucesso ou erro da operação.
     * @throws Exception - Pode lançar exceções relacionadas a operações de rede e JSON.
     */
    public static String addCliente(Cliente cliente) throws Exception {
        URL url = new URL(BASE_URL + "/adicionar"); // Cria a URL completa para a operação de adicionar.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Abre uma conexão HTTP.
        conn.setRequestMethod("POST"); // Define o método da requisição como POST.
        conn.setRequestProperty("Content-Type", "application/json"); // Define o tipo de conteúdo como JSON.
        conn.setDoOutput(true); // Permite enviar dados na requisição.

        // Cria um objeto JSON com os dados do cliente.
        JSONObject jsonCliente = new JSONObject();
        jsonCliente.put("nome", cliente.getNome()); // Adiciona o nome ao objeto JSON.
        jsonCliente.put("email", cliente.getEmail()); // Adiciona o email ao objeto JSON.

        // Envia o JSON com os dados do cliente.
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        writer.write(jsonCliente.toString()); // Escreve o JSON no corpo da requisição.
        writer.flush();
        writer.close();

        // Lê a resposta da API.
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) { // Lê cada linha da resposta.
            result.append(line);
        }
        reader.close();
        return result.toString(); // Retorna a resposta da API.
    }

    /**
     * Método updateCliente()
     *
     * Propósito: Envia uma requisição HTTP PUT para atualizar os dados de um cliente existente na API.
     *
     * @param id - ID do cliente a ser atualizado.
     * @param cliente - Objeto Cliente contendo os novos dados do cliente.
     * @return String - Resposta da API indicando sucesso ou erro da operação.
     * @throws Exception - Pode lançar exceções relacionadas a operações de rede e JSON.
     */
    public static String updateCliente(long id, Cliente cliente) throws Exception {
        URL url = new URL(BASE_URL + "/atualizar/" + id); // Cria a URL completa para a operação de atualização.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Abre uma conexão HTTP.
        conn.setRequestMethod("PUT"); // Define o método da requisição como PUT.
        conn.setRequestProperty("Content-Type", "application/json"); // Define o tipo de conteúdo como JSON.
        conn.setDoOutput(true); // Permite enviar dados na requisição.

        // Cria um objeto JSON com os dados atualizados do cliente.
        JSONObject jsonCliente = new JSONObject();
        jsonCliente.put("nome", cliente.getNome()); // Adiciona o nome atualizado ao objeto JSON.
        jsonCliente.put("email", cliente.getEmail()); // Adiciona o email atualizado ao objeto JSON.

        // Envia o JSON com os dados atualizados do cliente.
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        writer.write(jsonCliente.toString()); // Escreve o JSON no corpo da requisição.
        writer.flush();
        writer.close();

        // Lê a resposta da API.
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) { // Lê cada linha da resposta.
            result.append(line);
        }
        reader.close();
        return result.toString(); // Retorna a resposta da API.
    }

    /**
     * Método deleteCliente()
     *
     * Propósito: Envia uma requisição HTTP DELETE para deletar um cliente existente na API.
     *
     * @param id - ID do cliente a ser deletado.
     * @return String - Resposta da API indicando sucesso ou erro da operação.
     * @throws Exception - Pode lançar exceções relacionadas a operações de rede.
     */
    public static String deleteCliente(long id) throws Exception {
        URL url = new URL(BASE_URL + "/deletar/" + id); // Cria a URL completa para a operação de exclusão.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Abre uma conexão HTTP.
        conn.setRequestMethod("DELETE"); // Define o método da requisição como DELETE.
        conn.setRequestProperty("Content-Type", "application/json"); // Define o tipo de conteúdo como JSON.

        // Lê a resposta da API.
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) { // Lê cada linha da resposta.
            result.append(line);
        }
        reader.close();
        return result.toString(); // Retorna a resposta da API.
    }
}
