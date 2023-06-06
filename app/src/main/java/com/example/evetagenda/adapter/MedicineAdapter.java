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
import com.example.evetagenda.model.Medicine;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.HeroViewHolder> {

    Context mCtx;
    List<Medicine> heroList;
    private OnListItemClickListener onlistItemClickListener;
    private static final String TAG = "MedicineAdapter";

    public MedicineAdapter(Context mCtx, List<Medicine> producerList,OnListItemClickListener onlistItemClickListener) {
        this.mCtx = mCtx;
        this.heroList = producerList;
        this.onlistItemClickListener = onlistItemClickListener;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.medicine_recycleview_layout, parent, false);
        return new HeroViewHolder(view,onlistItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, int position) {
        Medicine hero = heroList.get(position);
        holder.medName.setText(String.valueOf(hero.getMedName()));
        holder.drastikiOusia.setText(String.valueOf(hero.getDrastikiOusia()));
        holder.medCompany.setText(String.valueOf(hero.getMedCompany()));
        holder.anamoniBooeidi.setText(String.valueOf(hero.getAnamoniBooeidi()));
        holder.anamoniAiges.setText(String.valueOf(hero.getAnamoniAiges()));
        holder.anamoniProbata.setText(String.valueOf(hero.getAnamoniProbata()));
        holder.anamoniXoiroi.setText(String.valueOf(hero.getAnamoniXoiroi()));
        holder.anamoniIndornithes.setText(String.valueOf(hero.getAnamoniIndornithes()));
        holder.anamoniMelisses.setText(String.valueOf(hero.getAnamoniMelisses()));
        holder.anamoniKreas.setText(String.valueOf(hero.getAnamoniKreas()));
        holder.anamoniGala.setText(String.valueOf(hero.getAnamoniGala()));
        holder.anamoniAuga.setText(String.valueOf(hero.getAnamoniAuga()));
        holder.anamoniMeli.setText(String.valueOf(hero.getAnamoniMeli()));
        holder.medDosologia.setText(String.valueOf(hero.getMedDosologia()));
        holder.medXronosTherapias.setText(String.valueOf(hero.getMedXronosTherapias()));
//        holder.registerMed.setText(String.valueOf(hero.getRegisterMed()));
    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

    class HeroViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView medName;
        TextView drastikiOusia;
        TextView medCompany;
        TextView anamoniBooeidi;
        TextView anamoniAiges;
        TextView anamoniProbata;
        TextView anamoniXoiroi;
        TextView anamoniIndornithes;
        TextView anamoniMelisses;
        TextView anamoniKreas;
        TextView anamoniGala;
        TextView anamoniAuga;
        TextView anamoniMeli;
        TextView medDosologia;
        TextView medXronosTherapias;
        TextView registerMed;

        public HeroViewHolder(View itemView, OnListItemClickListener onListItemClickListener) {
            super(itemView);
            medName = itemView.findViewById(R.id.medName);
            drastikiOusia = itemView.findViewById(R.id.drastikiOusia);
            medCompany = itemView.findViewById(R.id.medCompany);
            anamoniBooeidi = itemView.findViewById(R.id.anamoniBooeidi);
            anamoniAiges = itemView.findViewById(R.id.anamoniAiges);
            anamoniProbata = itemView.findViewById(R.id.anamoniProbata);
            anamoniXoiroi = itemView.findViewById(R.id.anamoniXoiroi);
            anamoniIndornithes = itemView.findViewById(R.id.anamoniIndornithes);
            anamoniMelisses = itemView.findViewById(R.id.anamoniMelisses);
            anamoniKreas = itemView.findViewById(R.id.anamoniKreas);
            anamoniGala = itemView.findViewById(R.id.anamoniGala);
            anamoniAuga = itemView.findViewById(R.id.anamoniAuga);
            anamoniMeli = itemView.findViewById(R.id.anamoniMeli);
            medDosologia = itemView.findViewById(R.id.medDosologia);
            medXronosTherapias = itemView.findViewById(R.id.medXronosTherapias);
            //registerMed = itemView.findViewById(R.id.registerMed);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: " + getAdapterPosition());
            onlistItemClickListener.clickPosition(getAdapterPosition(), v.getId());
        }
    }
}