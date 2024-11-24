package com.example.pim_mundo_verde.services;

import com.example.pim_mundo_verde.model.Fazendeiro;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Classe FazendeiroApiService
 *
 * Propósito: Esta classe fornece métodos para comunicação com a API REST de fazendeiros.
 * Permite listar, adicionar, atualizar e deletar fazendeiros ao enviar requisições HTTP
 * para o servidor.
 *
 * Uso: Esta classe é usada principalmente para operações CRUD (Create, Read, Update, Delete)
 * em fazendeiros, onde cada método realiza uma operação específica de comunicação com a API.
 */

public class FazendeiroApiService {

    private static final String BASE_URL = "http://10.0.2.2:8085/api/USUARIO";
    // URL base para todas as requisições API. O endereço "10.0.2.2" é usado para o Android Emulator acessar o "localhost".

    /**
     * Método getAllFazendeiros()
     *
     * Propósito: Envia uma requisição HTTP GET para obter todos os fazendeiros cadastrados na API.
     *
     * @return List<Fazendeiro> - Uma lista de objetos Fazendeiro representando todos os fazendeiros no banco de dados.
     * @throws Exception - Pode lançar exceções relacionadas a operações de rede e JSON.
     */
    public static List<Fazendeiro> getAllFazendeiros() throws Exception {
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

        List<Fazendeiro> fazendeiros = new ArrayList<>(); // Lista para armazenar os fazendeiros.
        JSONArray jsonArray = new JSONArray(result.toString()); // Converte a resposta para um array JSON.
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i); // Extrai cada objeto JSON.
            Fazendeiro fazendeiro = new Fazendeiro(
                    jsonObject.getString("nome"),
                    jsonObject.getString("email"),
                    jsonObject.getString("telefone"),
                    jsonObject.getString("documento"),
                    jsonObject.getString("tipoDocumento"),
                    jsonObject.getString("senha")
            ); // Cria um novo objeto Fazendeiro com os dados.
            fazendeiros.add(fazendeiro); // Adiciona o fazendeiro à lista.
        }
        return fazendeiros; // Retorna a lista completa de fazendeiros.
    }

    /**
     * Método addFazendeiro()
     *
     * Propósito: Envia uma requisição HTTP POST para adicionar um novo fazendeiro à API.
     *
     * @param fazendeiro - Objeto Fazendeiro que contém os dados do fazendeiro a ser adicionado.
     * @return String - Resposta da API indicando sucesso ou erro da operação.
     * @throws Exception - Pode lançar exceções relacionadas a operações de rede e JSON.
     */
    public static String addFazendeiro(Fazendeiro fazendeiro) throws Exception {
        URL url = new URL(BASE_URL + "/adicionar"); // Cria a URL completa para a operação de adicionar.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Abre uma conexão HTTP.
        conn.setRequestMethod("POST"); // Define o método da requisição como POST.
        conn.setRequestProperty("Content-Type", "application/json"); // Define o tipo de conteúdo como JSON.
        conn.setDoOutput(true); // Permite enviar dados na requisição.

        // Cria um objeto JSON com os dados do fazendeiro.
        JSONObject jsonFazendeiro = new JSONObject();
        jsonFazendeiro.put("nome", fazendeiro.getNome()); // Adiciona o nome ao objeto JSON.
        jsonFazendeiro.put("email", fazendeiro.getEmail()); // Adiciona o email ao objeto JSON.
        jsonFazendeiro.put("telefone", fazendeiro.getTelefone()); // Adiciona o telefone ao objeto JSON.
        jsonFazendeiro.put("documento", fazendeiro.getDocumento()); // Adiciona o documento ao objeto JSON.
        jsonFazendeiro.put("tipoDocumento", fazendeiro.getTipoDocumento()); // Adiciona o tipo de documento ao objeto JSON.
        jsonFazendeiro.put("senha", fazendeiro.getSenha()); // Adiciona a senha ao objeto JSON.

        // Envia o JSON com os dados do fazendeiro.
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        writer.write(jsonFazendeiro.toString()); // Escreve o JSON no corpo da requisição.
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
     * Método updateFazendeiro()
     *
     * Propósito: Envia uma requisição HTTP PUT para atualizar os dados de um fazendeiro existente na API.
     *
     * @param id - ID do fazendeiro a ser atualizado.
     * @param fazendeiro - Objeto Fazendeiro contendo os novos dados do fazendeiro.
     * @return String - Resposta da API indicando sucesso ou erro da operação.
     * @throws Exception - Pode lançar exceções relacionadas a operações de rede e JSON.
     */
    public static String updateFazendeiro(long id, Fazendeiro fazendeiro) throws Exception {
        URL url = new URL(BASE_URL + "/atualizar/" + id); // Cria a URL completa para a operação de atualização.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Abre uma conexão HTTP.
        conn.setRequestMethod("PUT"); // Define o método da requisição como PUT.
        conn.setRequestProperty("Content-Type", "application/json"); // Define o tipo de conteúdo como JSON.
        conn.setDoOutput(true); // Permite enviar dados na requisição.

        // Cria um objeto JSON com os dados atualizados do fazendeiro.
        JSONObject jsonFazendeiro = new JSONObject();
        jsonFazendeiro.put("nome", fazendeiro.getNome()); // Adiciona o nome atualizado ao objeto JSON.
        jsonFazendeiro.put("email", fazendeiro.getEmail()); // Adiciona o email atualizado ao objeto JSON.
        jsonFazendeiro.put("telefone", fazendeiro.getTelefone()); // Adiciona o telefone atualizado ao objeto JSON.
        jsonFazendeiro.put("documento", fazendeiro.getDocumento()); // Adiciona o documento atualizado ao objeto JSON.
        jsonFazendeiro.put("tipoDocumento", fazendeiro.getTipoDocumento()); // Adiciona o tipo de documento atualizado.
        jsonFazendeiro.put("senha", fazendeiro.getSenha()); // Adiciona a senha atualizada ao objeto JSON.

        // Envia o JSON com os dados atualizados do fazendeiro.
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        writer.write(jsonFazendeiro.toString()); // Escreve o JSON no corpo da requisição.
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
     * Método deleteFazendeiro()
     *
     * Propósito: Envia uma requisição HTTP DELETE para deletar um fazendeiro existente na API.
     *
     * @param id - ID do fazendeiro a ser deletado.
     * @return String - Resposta da API indicando sucesso ou erro da operação.
     * @throws Exception - Pode lançar exceções relacionadas a operações de rede.
     */
    public static String deleteFazendeiro(long id) throws Exception {
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

    public Call<Void> salvarFazendeiro(Fazendeiro fazendeiro) {
        return null;
    }
}
