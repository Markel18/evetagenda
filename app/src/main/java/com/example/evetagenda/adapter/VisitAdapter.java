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
import com.example.evetagenda.model.Event;
import java.util.List;

public class VisitAdapter extends RecyclerView.Adapter<VisitAdapter.HeroViewHolder> {

    Context mCtx;
    List<Event> heroList;
    private OnListItemClickListener onlistItemClickListener;
    private static final String TAG = "VisitAdapter";

    public VisitAdapter(Context mCtx, List<Event> producerList,OnListItemClickListener onlistItemClickListener) {
        this.mCtx = mCtx;
        this.heroList = producerList;
        this.onlistItemClickListener = onlistItemClickListener;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.visit_recycleview_layout, parent, false);
        return new HeroViewHolder(view,onlistItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, int position) {
        Event hero = heroList.get(position);
        holder.startEvent.setText(String.valueOf(hero.getStart_event()));
        holder.endEvent.setText(String.valueOf(hero.getEnd_event()));
        holder.title.setText(String.valueOf(hero.getTitle()));
        holder.prodName.setText(String.valueOf(hero.getProd_name()));
        holder.perioxi.setText(String.valueOf(hero.getPerioxi()));
        holder.aitiologiaEpiskepsis.setText(String.valueOf(hero.getAitiologia_episkepsis()));
        holder.prodPhone.setText(String.valueOf(hero.getProd_phone()));
    }

    @Override
    public int getItemCount() {
        if(heroList != null){
            return heroList.size();
        }else{
            return 0;
        }
    }

    class HeroViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView startEvent;
        TextView endEvent;
        TextView title;
        TextView prodName;
        TextView perioxi;
        TextView aitiologiaEpiskepsis;
        TextView prodPhone;


        public HeroViewHolder(View itemView,OnListItemClickListener onListItemClickListener) {
            super(itemView);
            startEvent = itemView.findViewById(R.id.start_event);
            endEvent = itemView.findViewById(R.id.end_event);
            title = itemView.findViewById(R.id.title);
            prodName = itemView.findViewById(R.id.prod_name);
            perioxi= itemView.findViewById(R.id.perioxi);
            aitiologiaEpiskepsis = itemView.findViewById(R.id.aitiologia_episkepsis);
            prodPhone = itemView.findViewById(R.id.prod_phone);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: " + getAdapterPosition());
            onlistItemClickListener.clickPosition(getAdapterPosition(), v.getId());
        }
    }
}