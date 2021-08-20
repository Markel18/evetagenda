package com.example.evetagenda.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.evetagenda.R;
import com.example.evetagenda.interfaces.OnListItemClickListener;
import com.example.evetagenda.model.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.HeroViewHolder> {

    Context mCtx;
    List<Note> heroList;
    private OnListItemClickListener onlistItemClickListener;
    private static final String TAG = "NoteAdapter";

    public NoteAdapter(Context mCtx, List<Note> producerList, OnListItemClickListener onlistItemClickListener) {
        this.mCtx = mCtx;
        this.heroList = producerList;
        this.onlistItemClickListener = onlistItemClickListener;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.note_recycleview_layout, parent, false);
        return new HeroViewHolder(view,onlistItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, int position) {
        Note hero = heroList.get(position);
        holder.note.setText(String.valueOf(hero.getMessage()));
        holder.dateTime.setText(String.valueOf(hero.getDate()));
    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

    class HeroViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView note;
        TextView dateTime;

        public HeroViewHolder(View itemView,OnListItemClickListener onListItemClickListener) {
            super(itemView);
            note = itemView.findViewById(R.id.note);
            dateTime = itemView.findViewById(R.id.date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: " + getAdapterPosition());
            onlistItemClickListener.clickPosition(getAdapterPosition(), v.getId());
        }
    }
}