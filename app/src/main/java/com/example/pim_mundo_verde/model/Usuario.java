package com.example.pim_mundo_verde.model;

import java.util.Objects;

public class Usuario {
    private long idUsuario;        // ID do usuário
    private String documento;      // Documento (CPF, RG, etc.)
    private String tipoDocumento;  // Tipo do documento
    private String nome;           // Nome do usuário
    private String email;          // Email do usuário
    private String telefone;       // Telefone do usuário
    private String senha;          // Senha do usuário

    // Construtor padrão
    public Usuario() {
    }

    // Construtor completo
    public Usuario(long idUsuario, String documento, String tipoDocumento, String nome, String email, String telefone, String senha) {
        this.idUsuario = idUsuario;
        this.documento = documento;
        this.tipoDocumento = tipoDocumento;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
    }

    // Getters e Setters
    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        // Exemplo de validação do formato do e-mail
        if (email != null && email.contains("@")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("E-mail inválido");
        }
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        // A senha pode ser validada ou criptografada, se necessário
        this.senha = senha;
    }

    // Método toString() para visualização
    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", documento='" + documento + '\'' +
                ", tipoDocumento='" + tipoDocumento + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
        // Senha omitida por segurança
    }

    // hashCode e equals para comparação e integridade dos objetos
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return idUsuario == usuario.idUsuario &&
                Objects.equals(documento, usuario.documento) &&
                Objects.equals(tipoDocumento, usuario.tipoDocumento) &&
                Objects.equals(nome, usuario.nome) &&
                Objects.equals(email, usuario.email) &&
                Objects.equals(telefone, usuario.telefone) &&
                Objects.equals(senha, usuario.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, documento, tipoDocumento, nome, email, telefone, senha);
    }
}