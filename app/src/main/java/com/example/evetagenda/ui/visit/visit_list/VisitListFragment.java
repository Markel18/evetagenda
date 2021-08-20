package com.example.evetagenda.ui.visit.visit_list;

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
import com.example.evetagenda.adapter.VisitAdapter;
import com.example.evetagenda.databinding.FragmentGalleryBinding;
import com.example.evetagenda.interfaces.OnListItemClickListener;
import com.example.evetagenda.model.Event;
import com.example.evetagenda.model.EventResponse;
import com.example.evetagenda.model.MedicineResponse;
import com.example.evetagenda.ui.medicine.edit_delete.MedicineEditDeleteActivity;
import com.example.evetagenda.ui.visit.edit_delete.VisitEditDeleteActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VisitListFragment extends Fragment  implements OnListItemClickListener {

    private VisitListViewModel visitListViewModel;
    private FragmentGalleryBinding binding;
    private List<Event> eventList = new ArrayList<>();
    RecyclerView recyclerView;
    VisitAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        visitListViewModel =
                new ViewModelProvider(this).get(VisitListViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMax(100);
        progressDoalog.setMessage(getResources().getString(R.string.please_wait));
        progressDoalog.show();

        visitListViewModel.getEvents().observe(getViewLifecycleOwner(), new Observer<EventResponse>() {
            @Override
            public void onChanged(@Nullable EventResponse eventResponse) {
                progressDoalog.dismiss();
                eventList = eventResponse.getEventsList();
                initializeViewsAdapter();
            }
        });

        return root;
    }

    private void initializeViewsAdapter() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        registerForContextMenu(recyclerView);
        adapter = new VisitAdapter(getActivity(),eventList,this);
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
        visitListViewModel.getEvents().observe(getViewLifecycleOwner(), new Observer<EventResponse>() {
            @Override
            public void onChanged(@Nullable EventResponse eventResponse) {
                if(eventResponse.getEventsList() != null) {
                    eventList = eventResponse.getEventsList();
                    initializeViewsAdapter();
                }
            }
        });
    }

    @Override
    public void clickPosition(int position, int id) {
        Toast.makeText(getActivity(),"Success :"+eventList.get(position).getId(),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), VisitEditDeleteActivity.class);
        intent.putExtra("Event", eventList.get(position));
        startActivity(intent);
    }
}