package com.example.pim_mundo_verde.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pim_mundo_verde.R;
import com.example.pim_mundo_verde.model.Produto;

import java.util.List;

public class AdapterProduto extends RecyclerView.Adapter<AdapterProduto.ProdutoViewHolder> {

    private final List<Produto> produtos;
    private final OnItemClickListener onItemClickListener;
    private Produto produtoSelecionado;

    // Construtor correto
    public AdapterProduto(List<Produto> produtos, OnItemClickListener onItemClickListener) {
        this.produtos = produtos;
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Produto produto);
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflando a view para cada item do RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.produto, parent, false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        Produto produto = produtos.get(position);

        // Configurando os dados para cada item
        holder.foto.setImageResource(produto.getFoto());
        holder.nome.setText(produto.getNome());
        holder.descricao.setText(produto.getDescricao());
        holder.preco.setText(produto.getPreco());
        holder.quantidade.setText("Quantidade: " + produto.getQuantidade());

        // Configura o clique e armazena o produto selecionado
        holder.itemView.setOnClickListener(v -> {
            produtoSelecionado = produto;
            onItemClickListener.onItemClick(produto);
            notifyItemChanged(position);  // Notifica apenas o item selecionado
        });

        // Altera a aparência se o produto estiver selecionado
        if (produto.equals(produtoSelecionado)) {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.selecionado)); // Usando cor de seleção
        } else {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.nao_selecionado)); // Cor padrão
        }
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    // Método para obter o produto atualmente selecionado
    public Produto getProdutoSelecionado() {
        return produtoSelecionado;
    }

    // ViewHolder para o item de produto
    public static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        ImageView foto;
        TextView nome;
        TextView descricao;
        TextView preco;
        TextView quantidade;

        public ProdutoViewHolder(View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.FotoProduto);
            nome = itemView.findViewById(R.id.nomeProduto);
            descricao = itemView.findViewById(R.id.descricaoProduto);
            preco = itemView.findViewById(R.id.precoProduto);
            quantidade = itemView.findViewById(R.id.quantidadeProduto);
        }
    }

    // Método para atualizar a quantidade de um produto específico
    public void atualizarQuantidade(Produto produto, int novaQuantidade) {
        for (Produto p : produtos) {
            if (p.getId().equals(produto.getId())) {
                p.setQuantidade(novaQuantidade);
                notifyItemChanged(produtos.indexOf(p));  // Atualiza apenas o item alterado
                break;
            }
        }
    }
}