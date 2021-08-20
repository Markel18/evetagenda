package com.example.evetagenda.ui.medicine.medicine_list;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evetagenda.R;
import com.example.evetagenda.adapter.MedicineAdapter;
import com.example.evetagenda.databinding.FragmentMedicineBinding;
import com.example.evetagenda.interfaces.OnListItemClickListener;
import com.example.evetagenda.model.Medicine;
import com.example.evetagenda.model.MedicineResponse;
import com.example.evetagenda.ui.medicine.edit_delete.MedicineEditDeleteActivity;

import java.util.ArrayList;
import java.util.List;

public class MedicineListFragment extends Fragment implements OnListItemClickListener {

    private MedicineViewModel medicineViewModel;
    private FragmentMedicineBinding binding;
    private List<Medicine> medicineList = new ArrayList<>();
    MedicineAdapter adapter;
    RecyclerView recyclerView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        medicineViewModel =
                new ViewModelProvider(this).get(MedicineViewModel.class);

        binding = FragmentMedicineBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMax(100);
        progressDoalog.setMessage(getResources().getString(R.string.please_wait));
        progressDoalog.show();


        recyclerView = root.findViewById(R.id.recyclerview);
        medicineViewModel.getNotes().observe(getViewLifecycleOwner(), new Observer<MedicineResponse>() {
            @Override
            public void onChanged(@Nullable MedicineResponse medicineResponse) {
                progressDoalog.dismiss();
                if(medicineResponse.getMedicines() != null) {
                    medicineList = medicineResponse.getMedicines();
                    initializeViewsAdapter();
                }else{
                    Toast.makeText(getActivity(),"No Medicine Entries",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    private void initializeViewsAdapter() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        registerForContextMenu(recyclerView);
        adapter = new MedicineAdapter(getActivity(),medicineList,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("NoteListFragment", "onResume:");
        medicineViewModel.getNotes().observe(getViewLifecycleOwner(), new Observer<MedicineResponse>() {
            @Override
            public void onChanged(@Nullable MedicineResponse medicineResponse) {
                if(medicineResponse.getMedicines() != null) {
                    medicineList = medicineResponse.getMedicines();
                    initializeViewsAdapter();
                }
            }
        });
    }

    @Override
    public void clickPosition(int position, int id) {
        Intent intent = new Intent(getActivity(), MedicineEditDeleteActivity.class);
        intent.putExtra("Medicine", medicineList.get(position));
        startActivity(intent);
    }
}