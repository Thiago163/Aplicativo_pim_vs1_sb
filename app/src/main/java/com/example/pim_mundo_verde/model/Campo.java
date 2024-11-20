package com.example.pim_mundo_verde;

public class Campo {
    private String nome;
    private String descricao;
    private String status;
    private String dataColheita;
    private String distancia;

    public Campo(String nome, String descricao, String status, String dataColheita, String distancia) {
        this.nome = nome;
        this.descricao = descricao;
        this.status = status;
        this.dataColheita = dataColheita;
        this.distancia = distancia;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getStatus() {
        return status;
    }

    public String getDataColheita() {
        return dataColheita;
    }

    public String getDistancia() {
        return distancia;
    }
}
