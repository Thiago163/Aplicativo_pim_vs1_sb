package com.example.pim_mundo_verde.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Produto implements Parcelable {
    private final String id; // Identificador único do produto
    private final int foto;
    private final String nome;
    private final String descricao;
    private final String preco;
    private int quantidade;

    // Construtor
    public Produto(String id, int foto, String nome, String descricao, String preco) {
        this.id = id;
        this.foto = foto;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = 1; // Inicializa a quantidade como 1
    }

    // Construtor para o Parcel
    protected Produto(Parcel in) {
        id = in.readString();
        foto = in.readInt();
        nome = in.readString();
        descricao = in.readString();
        preco = in.readString();
        quantidade = in.readInt();
    }

    // Métodos getters
    public String getId() {
        return id;
    }

    public int getFoto() {
        return foto;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    // Método setter para quantidade
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    // Método para converter o preço para um valor double
    public double getPrecoAsDouble() {
        return Double.parseDouble(preco.replace(",", ".").replace("R$", "").trim());
    }

    // Implementação do Parcelable
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(foto);
        dest.writeString(nome);
        dest.writeString(descricao);
        dest.writeString(preco);
        dest.writeInt(quantidade);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Produto> CREATOR = new Creator<Produto>() {
        @Override
        public Produto createFromParcel(Parcel in) {
            return new Produto(in);
        }

        @Override
        public Produto[] newArray(int size) {
            return new Produto[size];
        }
    };

    @Override
    public String toString() {
        return "Produto{" +
                "id='" + id + '\'' +
                ", foto=" + foto +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco='" + preco + '\'' +
                ", quantidade=" + quantidade +
                '}';
    }
}