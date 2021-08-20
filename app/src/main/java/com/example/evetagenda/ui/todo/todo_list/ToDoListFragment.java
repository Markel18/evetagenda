package com.example.evetagenda.ui.todo.todo_list;

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
import com.example.evetagenda.adapter.ToDoAdapter;
import com.example.evetagenda.databinding.FragmentTodoBinding;
import com.example.evetagenda.interfaces.OnListItemClickListener;
import com.example.evetagenda.model.ToDo;
import com.example.evetagenda.model.ToDoResponse;
import com.example.evetagenda.ui.todo.edit_delete.TodoEditDeleteActivity;

import java.util.ArrayList;
import java.util.List;

public class ToDoListFragment extends Fragment implements OnListItemClickListener {

    private ToDoListViewModel toDoListViewModel;
    private FragmentTodoBinding binding;
    private List<ToDo> toDoList = new ArrayList<>();
    ToDoAdapter adapter;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toDoListViewModel =
                new ViewModelProvider(this).get(ToDoListViewModel.class);

        binding = FragmentTodoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = root.findViewById(R.id.recyclerview);
        initializeViewsAdapter();

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMax(100);
        progressDoalog.setMessage(getResources().getString(R.string.please_wait));
        progressDoalog.show();

        toDoListViewModel.getNotes().observe(getViewLifecycleOwner(), new Observer<ToDoResponse>() {
            @Override
            public void onChanged(@Nullable ToDoResponse toDoResponse) {
                progressDoalog.dismiss();
                toDoList = toDoResponse.getTodos();
                initializeViewsAdapter();
            }
        });

        return root;
    }

    private void initializeViewsAdapter() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        registerForContextMenu(recyclerView);
        adapter = new ToDoAdapter(getActivity(),toDoList,this);
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
        Log.d("ToDoListFragment", "onResume:");
        toDoListViewModel.getNotes().observe(getViewLifecycleOwner(), new Observer<ToDoResponse>() {
            @Override
            public void onChanged(@Nullable ToDoResponse toDoResponse) {
                if(toDoResponse.getTodos() != null) {
                    toDoList = toDoResponse.getTodos();
                    initializeViewsAdapter();
                }else{
                    Toast.makeText(getActivity(),"No ToDo Entries",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void clickPosition(int position, int id) {
        Intent intent = new Intent(getActivity(), TodoEditDeleteActivity.class);
        intent.putExtra("text", toDoList.get(position).getItem());
        intent.putExtra("isDone", String.valueOf(toDoList.get(position).getIsDone()));
        intent.putExtra("id",  String.valueOf(toDoList.get(position).getiDItem()));
        startActivity(intent);
    }
}