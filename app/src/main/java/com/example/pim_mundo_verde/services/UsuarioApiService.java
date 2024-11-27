package com.example.pim_mundo_verde.services;

import android.os.AsyncTask;

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

public class UsuarioApiService {

    private static final String BASE_URL = "http://192.168.1.100:8085/api/usuarios"; // URL base da API

    // Interface de Callback
    public interface ApiCallback<T> {
        void onSuccess(T result);
        void onFailure(String error);
    }

    // Método para obter todos os usuários de forma assíncrona
    public void listarUsuarios(final ApiCallback<List<Usuario>> callback) {
        new AsyncTask<Void, Void, List<Usuario>>() {

            @Override
            protected List<Usuario> doInBackground(Void... voids) {
                try {
                    URL url = new URL(BASE_URL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

                    int responseCode = connection.getResponseCode();
                    if (responseCode != HttpURLConnection.HTTP_OK) {
                        throw new Exception("Erro ao listar usuários: " + responseCode);
                    }

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    reader.close();
                    connection.disconnect();

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

                    return usuarios;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<Usuario> usuarios) {
                if (usuarios != null) {
                    callback.onSuccess(usuarios);
                } else {
                    callback.onFailure("Erro ao obter usuários.");
                }
            }
        }.execute();
    }

    // Método para adicionar um novo usuário de forma assíncrona
    public void adicionarUsuario(final Usuario usuario, final ApiCallback<String> callback) {
        new AsyncTask<Usuario, Void, String>() {

            @Override
            protected String doInBackground(Usuario... usuarios) {
                try {
                    Usuario usuarioToAdd = usuarios[0];
                    URL url = new URL(BASE_URL + "/adicionar");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json; utf-8");
                    connection.setDoOutput(true);

                    JSONObject json = new JSONObject();
                    json.put("documento", usuarioToAdd.getDocumento());
                    json.put("tipo_documento", usuarioToAdd.getTipoDocumento());
                    json.put("nome", usuarioToAdd.getNome());
                    json.put("email", usuarioToAdd.getEmail());
                    json.put("telefone", usuarioToAdd.getTelefone());
                    json.put("senha", usuarioToAdd.getSenha());

                    try (OutputStream os = connection.getOutputStream()) {
                        byte[] input = json.toString().getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    int responseCode = connection.getResponseCode();
                    connection.disconnect();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        return "Usuário adicionado com sucesso!";
                    } else {
                        throw new Exception("Erro ao adicionar usuário: " + responseCode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Erro ao adicionar usuário.";
                }
            }

            @Override
            protected void onPostExecute(String result) {
                callback.onSuccess(result);
            }
        }.execute(usuario);
    }

    // Método para atualizar um usuário de forma assíncrona
    public void atualizarUsuario(final long id, final Usuario usuario, final ApiCallback<String> callback) {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(BASE_URL + "/atualizar/" + id); // A URL agora inclui o ID
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
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        return "Usuário atualizado com sucesso!";
                    } else {
                        throw new Exception("Erro ao atualizar usuário: " + responseCode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Erro ao atualizar usuário.";
                }
            }

            @Override
            protected void onPostExecute(String result) {
                callback.onSuccess(result);
            }
        }.execute();
    }

    // Método para deletar um usuário de forma assíncrona
    public void deletarUsuario(final long id, final ApiCallback<String> callback) {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(BASE_URL + "/deletar/" + id); // A URL agora inclui o ID
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("DELETE");

                    int responseCode = connection.getResponseCode();
                    connection.disconnect();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        return "Usuário deletado com sucesso!";
                    } else {
                        throw new Exception("Erro ao deletar usuário: " + responseCode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Erro ao deletar usuário.";
                }
            }

            @Override
            protected void onPostExecute(String result) {
                callback.onSuccess(result);
            }
        }.execute();
    }
}