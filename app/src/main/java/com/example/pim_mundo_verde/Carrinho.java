package com.example.pim_mundo_verde;

import com.example.pim_mundo_verde.model.Produto;
import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private static Carrinho instance;
    private final List<Produto> produtos;
    private final List<Produto> produtosRemovidos;

    private Carrinho() {
        produtos = new ArrayList<>();
        produtosRemovidos = new ArrayList<>();
    }

    public static Carrinho getInstance() {
        if (instance == null) {
            instance = new Carrinho();
        }
        return instance;
    }

    public void adicionarProduto(Produto produto) {
        // Verifica se o produto já está no carrinho
        for (Produto p : produtos) {
            if (p.getId() == produto.getId()) { // Supondo que Produto tenha um método getId() que retorna um identificador único
                // Produto já existe no carrinho, não adiciona novamente
                System.out.println("Produto já foi adicionado ao carrinho.");
                return;
            }
        }
        // Se o produto não existir, adiciona ao carrinho
        produtos.add(produto);
    }

    public void removerProduto(Produto produto) {
        if (produtos.remove(produto)) {
            produtosRemovidos.add(produto);
        }
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public List<Produto> getProdutosRemovidos() {
        return produtosRemovidos;
    }

    public void limparCarrinho() {
        produtos.clear();
    }

    public void editarQuantidade(Produto produto, int novaQuantidade) {
        if (novaQuantidade <= 0) {
            removerProduto(produto);
        } else {
            produto.setQuantidade(novaQuantidade);
        }
    }

    public double calcularTotal() {
        double total = 0;
        for (Produto produto : produtos) {
            String precoString = produto.getPreco().replace("R$", "").replace(",", ".").trim();
            double precoUnitario = Double.parseDouble(precoString);
            total += precoUnitario * produto.getQuantidade(); // Calcula o total com base na quantidade do produto
        }
        return total;
    }
}