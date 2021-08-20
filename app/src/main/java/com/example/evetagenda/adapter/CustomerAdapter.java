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
import com.example.evetagenda.model.Producer;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.HeroViewHolder> {

    Context mCtx;
    List<Producer> heroList;
    private OnListItemClickListener onlistItemClickListener;
    private static final String TAG = "CustomerAdapter";

    public CustomerAdapter(Context mCtx, List<Producer> producerList,OnListItemClickListener onlistItemClickListener) {
        this.mCtx = mCtx;
        this.heroList = producerList;
        this.onlistItemClickListener = onlistItemClickListener;
    }

    public CustomerAdapter(Context mCtx, List<Producer> producerList) {
        this.mCtx = mCtx;
        this.heroList = producerList;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.customer_recycleview_layout, parent, false);
        return new HeroViewHolder(view,onlistItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, int position) {
        Producer hero = heroList.get(position);
        holder.prodFLname.setText(String.valueOf(hero.getProdFLname()));
        holder.prodPhone.setText(String.valueOf(hero.getProdPhone()));
        holder.prodEmail.setText(String.valueOf(hero.getProdEmail()));
        holder.prodCodeEktrofis.setText(String.valueOf(hero.getProdCodeEktrofis()));
        holder.prodAFM.setText(String.valueOf(hero.getProdAFM()));
        holder.prodDOY.setText(String.valueOf(hero.getProdDOY()));
        holder.prodArea.setText(String.valueOf(hero.getProdArea()));
        holder.prodNumAnimals.setText(String.valueOf(hero.getProdNumAnimals()));
        holder.prodFiliAnimals.setText(String.valueOf(hero.getProdFiliAnimals()));
        holder.registerDate.setText(String.valueOf(hero.getRegisterDate()));
        holder.prodTypeAnimals.setText(String.valueOf(hero.getProdTypeAnimals()));
    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

    class HeroViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView prodFLname;
        TextView prodPhone;
        TextView prodEmail;
        TextView prodCodeEktrofis;
        TextView prodAFM;
        TextView prodDOY;
        TextView prodArea;
        TextView prodNumAnimals;
        TextView prodFiliAnimals;
        TextView registerDate;
        TextView prodTypeAnimals;
        OnListItemClickListener onListItemClickListener;

        public HeroViewHolder(View itemView, OnListItemClickListener onListItemClickListener) {
            super(itemView);
            prodFLname = itemView.findViewById(R.id.prodFLname);
            prodPhone = itemView.findViewById(R.id.prodPhone);
            prodEmail= itemView.findViewById(R.id.prodEmail);
            prodCodeEktrofis= itemView.findViewById(R.id.prodCodeEktrofis);
            prodAFM= itemView.findViewById(R.id.prodAFM);
            prodDOY= itemView.findViewById(R.id.prodDOY);
            prodArea= itemView.findViewById(R.id.prodArea);
            prodNumAnimals= itemView.findViewById(R.id.prodNumAnimals);
            prodFiliAnimals= itemView.findViewById(R.id.prodFiliAnimals);
            prodTypeAnimals= itemView.findViewById(R.id.prodTypeAnimals);
            registerDate= itemView.findViewById(R.id.registerDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: " + getAdapterPosition());
            onlistItemClickListener.clickPosition(getAdapterPosition(), v.getId());
        }
    }

}