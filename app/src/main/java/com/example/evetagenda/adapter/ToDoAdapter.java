package com.example.evetagenda.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.evetagenda.R;
import com.example.evetagenda.interfaces.OnListItemClickListener;
import com.example.evetagenda.model.ToDo;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.HeroViewHolder> {

    Context mCtx;
    List<ToDo> heroList;
    private OnListItemClickListener onlistItemClickListener;
    private static final String TAG = "ToDoAdapter";

    public ToDoAdapter(Context mCtx, List<ToDo> toDoList,OnListItemClickListener onlistItemClickListener) {
        this.mCtx = mCtx;
        this.heroList = toDoList;
        this.onlistItemClickListener = onlistItemClickListener;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.todo_recycleview_layout, parent, false);
        return new HeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, int position) {
        ToDo hero = heroList.get(position);
        holder.item.setText(String.valueOf(hero.getItem()));
        if(String.valueOf(hero.getIsDone()).equals("1")) {
            holder.done.setChecked(true);
        }else{
            holder.done.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

    class HeroViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView item;
        CheckBox done;

        public HeroViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            done = itemView.findViewById(R.id.done);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: " + getAdapterPosition());
            onlistItemClickListener.clickPosition(getAdapterPosition(), v.getId());
        }
    }
}