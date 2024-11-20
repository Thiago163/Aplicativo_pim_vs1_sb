package com.example.pim_mundo_verde.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pim_mundo_verde.R;

import java.util.List;

public class AdpterCampo extends RecyclerView.Adapter<AdpterCampo.CampoViewHolder> {

    private List<com.example.pim_mundo_verde.Campo> listaCampos;

    public AdpterCampo(List<com.example.pim_mundo_verde.Campo> listaCampos) {
        this.listaCampos = listaCampos;
    }

    public static class CampoViewHolder extends RecyclerView.ViewHolder {
        public TextView nomeCampo;
        public TextView descricaoCampo;
        public TextView statusCampo;
        public TextView dataColheita;
        public TextView distancia;

        public CampoViewHolder(View itemView) {
            super(itemView);
            nomeCampo = itemView.findViewById(R.id.nomeCampo);
            descricaoCampo = itemView.findViewById(R.id.descricaoCampo);
            statusCampo = itemView.findViewById(R.id.statusCampo);
            dataColheita = itemView.findViewById(R.id.dataColheita);
            distancia = itemView.findViewById(R.id.distancia);
        }
    }

    @NonNull
    @Override
    public CampoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.campo, parent, false);
        return new CampoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CampoViewHolder holder, int position) {
        com.example.pim_mundo_verde.Campo campo = listaCampos.get(position);
        holder.nomeCampo.setText(campo.getNome());
        holder.descricaoCampo.setText(campo.getDescricao());
        holder.statusCampo.setText(campo.getStatus());
        holder.dataColheita.setText(campo.getDataColheita());
        holder.distancia.setText(campo.getDistancia());
    }

    @Override
    public int getItemCount() {
        return listaCampos.size();
    }
}
