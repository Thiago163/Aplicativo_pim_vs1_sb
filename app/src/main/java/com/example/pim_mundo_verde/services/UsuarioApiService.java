package com.example.pim_mundo_verde.services;

import android.os.Handler;
import android.os.Looper;

import com.example.pim_mundo_verde.model.Usuario;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UsuarioApiService {

    private static final String BASE_URL = "http://192.168.1.100:8085/api/usuarios"; // URL base da API
    private final ExecutorService executor;

    public UsuarioApiService() {
        this.executor = Executors.newSingleThreadExecutor(); // ExecutorService para tarefas assíncronas
    }

    // Interface de Callback
    public interface ApiCallback<T> {
        void onSuccess(T result);
        void onFailure(String error);
    }

    // Método para obter todos os usuários de forma assíncrona
    public void listarUsuarios(final ApiCallback<List<Usuario>> callback) {
        executor.execute(() -> {
            try {
                URL url = new URL(BASE_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new Exception("Erro ao listar usuários: " + responseCode);
                }

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    List<Usuario> usuarios = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(response.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Usuario usuario = new Usuario(
                                jsonObject.getLong("id_usuario"),
                                jsonObject.getString("documento"),
                                jsonObject.getString("tipo_documento"),
                                jsonObject.getString("nome"),
                                jsonObject.getString("email"),
                                jsonObject.getString("telefone"),
                                jsonObject.getString("senha")
                        );
                        usuarios.add(usuario);
                    }

                    // Retornar no thread principal
                    new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(usuarios));
                } finally {
                    connection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Handler(Looper.getMainLooper()).post(() -> callback.onFailure("Erro ao obter usuários."));
            }
        });
    }

    // Método para adicionar um novo usuário de forma assíncrona
    public void adicionarUsuario(final Usuario usuario, final ApiCallback<String> callback) {
        executor.execute(() -> {
            try {
                URL url = new URL(BASE_URL + "/adicionar");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setDoOutput(true);

                JSONObject json = new JSONObject();
                json.put("documento", usuario.getDocumento());
                json.put("tipo_documento", usuario.getTipoDocumento());
                json.put("nome", usuario.getNome());
                json.put("email", usuario.getEmail());
                json.put("telefone", usuario.getTelefone());
                json.put("senha", usuario.getSenha());

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = json.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = connection.getResponseCode();
                connection.disconnect();

                String result = (responseCode == HttpURLConnection.HTTP_OK)
                        ? "Usuário adicionado com sucesso!"
                        : "Erro ao adicionar usuário: " + responseCode;

                new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(result));
            } catch (Exception e) {
                e.printStackTrace();
                new Handler(Looper.getMainLooper()).post(() -> callback.onFailure("Erro ao adicionar usuário."));
            }
        });
    }

    // Método para atualizar um usuário de forma assíncrona
    public void atualizarUsuario(final long id, final Usuario usuario, final ApiCallback<String> callback) {
        executor.execute(() -> {
            try {
                URL url = new URL(BASE_URL + "/atualizar/" + id);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setDoOutput(true);

                JSONObject json = new JSONObject();
                json.put("documento", usuario.getDocumento());
                json.put("tipo_documento", usuario.getTipoDocumento());
                json.put("nome", usuario.getNome());
                json.put("email", usuario.getEmail());
                json.put("telefone", usuario.getTelefone());
                json.put("senha", usuario.getSenha());

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = json.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = connection.getResponseCode();
                connection.disconnect();

                String result = (responseCode == HttpURLConnection.HTTP_OK)
                        ? "Usuário atualizado com sucesso!"
                        : "Erro ao atualizar usuário: " + responseCode;

                new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(result));
            } catch (Exception e) {
                e.printStackTrace();
                new Handler(Looper.getMainLooper()).post(() -> callback.onFailure("Erro ao atualizar usuário."));
            }
        });
    }

    // Método para deletar um usuário de forma assíncrona
    public void deletarUsuario(final long id, final ApiCallback<String> callback) {
        executor.execute(() -> {
            try {
                URL url = new URL(BASE_URL + "/deletar/" + id);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("DELETE");

                int responseCode = connection.getResponseCode();
                connection.disconnect();

                String result = (responseCode == HttpURLConnection.HTTP_OK)
                        ? "Usuário deletado com sucesso!"
                        : "Erro ao deletar usuário: " + responseCode;

                new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(result));
            } catch (Exception e) {
                e.printStackTrace();
                new Handler(Looper.getMainLooper()).post(() -> callback.onFailure("Erro ao deletar usuário."));
            }
        });
    }
}