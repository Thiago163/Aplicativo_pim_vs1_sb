package com.example.pim_mundo_verde.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User {
    private String email;
    private String senha;

    // Construtor
    public User(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    // Getters e setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
