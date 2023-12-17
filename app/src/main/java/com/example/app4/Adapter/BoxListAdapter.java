package com.example.app4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app4.Models.Box;
import com.example.app4.BoxClickListener;
import com.example.app4.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoxListAdapter extends RecyclerView.Adapter<BoxViewHolder> {

    Context context;
    List<Box> list;

    BoxClickListener listener;

    public BoxListAdapter(Context context, List<Box> list, BoxClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BoxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BoxViewHolder(LayoutInflater.from(context).inflate(R.layout.box_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BoxViewHolder holder, int position) {

        holder.textView_date.setText(list.get(position).getDate());
        holder.textView_title.setText(list.get(position).getTitle());
        holder.textView_title.setSelected(true);
        holder.textView_notes.setText(list.get(position).getNotes());

        int color_code = list.get(position).getColor();
        holder.notes_container.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code, null));

        holder.notes_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(list.get(holder.getAdapterPosition()));

            }
        });

        holder.notes_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(list.get(holder.getAdapterPosition()), holder.notes_container);
                return true;
            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Box> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }
}

class BoxViewHolder extends RecyclerView.ViewHolder {

    CardView notes_container;
    TextView textView_title, textView_notes, textView_date;
    public BoxViewHolder(@NonNull View itemView) {
        super(itemView);

        notes_container = itemView.findViewById(R.id.card_container);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_notes = itemView.findViewById(R.id.textView_notes);
        textView_date = itemView.findViewById(R.id.textView_date);
    }
}
