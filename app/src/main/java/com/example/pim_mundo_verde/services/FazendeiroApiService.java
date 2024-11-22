package com.example.pim_mundo_verde.services;

import com.example.pim_mundo_verde.model.Fazendeiro;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

// Interface para os endpoints da API relacionados aos fazendeiros
public interface FazendeiroApiService {

    // Definindo o método para salvar um novo fazendeiro
    @POST("api/fazendeiro/salvar")  // Substitua o URL com o endpoint correto
    Call<Void> salvarFazendeiro(@Body Fazendeiro fazendeiro);  // Envia o objeto Fazendeiro para o servidor
}
