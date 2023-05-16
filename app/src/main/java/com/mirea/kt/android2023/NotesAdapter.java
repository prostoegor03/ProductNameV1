package com.mirea.kt.android2023;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    Context context;
    List<Notes> list;
    NotesClickListener listener;

    public NotesAdapter(Context context, List<Notes> list, NotesClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder {

        CardView notes_conteiner;
        TextView tvTitle,tvContent;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            notes_conteiner = itemView.findViewById(R.id.notes_conteiner);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
        }
    }








    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_list ,parent ,false ));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvTitle.setSelected(true);//разрешаем прокрутку

        holder.tvContent.setText(list.get(position).getContent());

        holder.notes_conteiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });

        holder.notes_conteiner.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(list.get(holder.getAdapterPosition()),holder.notes_conteiner);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}