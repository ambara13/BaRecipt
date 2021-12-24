package com.example.barecipt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{
    private List<ReciptHandler> resepHandlerList;
    private Context context;
    private RecyclerView recyclerView;
    private ImageButton btnDelete, btnEdit;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView namaResep;
        TextView pilihanResep;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaResep = itemView.findViewById(R.id.resep_nama_txt);
            pilihanResep = itemView.findViewById(R.id.resep_pilihan_txt);
//            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnEdit = itemView.findViewById(R.id.btn_edit);
        }
    }

    public MainAdapter(List<ReciptHandler> resepHandlerList, Context context, RecyclerView recyclerView) {
        this.resepHandlerList = resepHandlerList;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recipt_row, parent, false);
        MainAdapter.ViewHolder viewHolder = new MainAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        ReciptHandler resepHandler = resepHandlerList.get(position);
        holder.namaResep.setText(String.valueOf(resepHandler.getNamaResep()));
        holder.pilihanResep.setText(String.valueOf(resepHandler.getPilihan()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer itemId = Integer.valueOf(resepHandler.getId());
                Intent detailIntent = new Intent(holder.itemView.getContext(), ShowRecipt.class);
                detailIntent.putExtra("id", itemId);
                holder.itemView.getContext().startActivity(detailIntent);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Integer itemId = Integer.valueOf(resepHandler.getId());
                Intent detailIntent = new Intent(holder.itemView.getContext(), EditRecipt.class);
                detailIntent.putExtra("id", itemId);
                holder.itemView.getContext().startActivity(detailIntent);
            }
        });
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return resepHandlerList.size();
    }


}
