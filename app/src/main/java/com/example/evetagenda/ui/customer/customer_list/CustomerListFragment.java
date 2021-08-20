package com.example.evetagenda.ui.customer.customer_list;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.evetagenda.adapter.CustomerAdapter;
import com.example.evetagenda.databinding.FragmentGalleryBinding;
import com.example.evetagenda.interfaces.OnListItemClickListener;
import com.example.evetagenda.model.NoteResponse;
import com.example.evetagenda.model.Producer;
import com.example.evetagenda.model.ProducerResponse;
import com.example.evetagenda.ui.customer.edit_delete.CustomerEditDeleteActivity;
import com.example.evetagenda.ui.note.edit_delete.NoteEditDeleteActivity;

import java.util.ArrayList;
import java.util.List;


public class CustomerListFragment extends Fragment implements OnListItemClickListener {

    private CustomerListViewModel customerListViewModel;
    private FragmentGalleryBinding binding;
    private List<Producer> producerList = new ArrayList<>();
    RecyclerView recyclerView;
    CustomerAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        customerListViewModel =
                new ViewModelProvider(this).get(CustomerListViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = root.findViewById(R.id.recyclerview);

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMax(100);
        progressDoalog.setMessage(getResources().getString(R.string.please_wait));
        progressDoalog.show();

        customerListViewModel.getProducers().observe(getViewLifecycleOwner(), new Observer<ProducerResponse>() {
            @Override
            public void onChanged(@Nullable ProducerResponse producerResponse) {
                progressDoalog.dismiss();
                if(producerResponse.getProducerList() != null) {
                    producerList = producerResponse.getProducerList();
                    initializeViewsAdapter();
                }else{
                    Toast.makeText(getActivity(),"No Customer Entries",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    private void initializeViewsAdapter() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        registerForContextMenu(recyclerView);
        adapter = new CustomerAdapter(getActivity(),producerList,this);
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
        Log.d("ProducerListFragment", "onResume:");
        customerListViewModel.getProducers().observe(getViewLifecycleOwner(), new Observer<ProducerResponse>() {
            @Override
            public void onChanged(@Nullable ProducerResponse noteResponse) {
                producerList = noteResponse.getProducerList();
                initializeViewsAdapter();
            }
        });
    }

    @Override
    public void clickPosition(int position, int id) {
        Toast.makeText(getActivity(),"Success :"+producerList.get(position).getProdID(),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), CustomerEditDeleteActivity.class);
        intent.putExtra("afm", producerList.get(position).getProdAFM()+"");
        intent.putExtra("area", producerList.get(position).getProdArea()+"");
        intent.putExtra("code", producerList.get(position).getProdCodeEktrofis()+"");
        intent.putExtra("doy", producerList.get(position).getProdDOY()+"");
        intent.putExtra("email", producerList.get(position).getProdEmail()+"");
        intent.putExtra("animal_tribe", producerList.get(position).getProdFiliAnimals()+"");
        intent.putExtra("name", producerList.get(position).getProdFLname()+"");
        intent.putExtra("num_of_animals", producerList.get(position).getProdNumAnimals()+"");
        intent.putExtra("phone", producerList.get(position).getProdPhone()+"");
        intent.putExtra("animal_type", producerList.get(position).getProdTypeAnimals()+"");
        intent.putExtra("id",  String.valueOf(producerList.get(position).getProdID())+"");
        startActivity(intent);
    }


}