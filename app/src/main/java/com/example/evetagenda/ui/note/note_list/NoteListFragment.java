package com.example.evetagenda.ui.note.note_list;

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
import com.example.evetagenda.adapter.NoteAdapter;
import com.example.evetagenda.databinding.FragmentNoteListBinding;
import com.example.evetagenda.interfaces.OnListItemClickListener;
import com.example.evetagenda.model.Note;
import com.example.evetagenda.model.NoteResponse;
import com.example.evetagenda.model.Producer;
import com.example.evetagenda.model.ToDoResponse;
import com.example.evetagenda.ui.note.edit_delete.NoteEditDeleteActivity;
import com.example.evetagenda.ui.todo.edit_delete.TodoEditDeleteActivity;

import java.util.ArrayList;
import java.util.List;

public class NoteListFragment extends Fragment implements OnListItemClickListener {

    private NoteListViewModel noteListViewModel;
    private FragmentNoteListBinding binding;
    private List<Note> noteList = new ArrayList<>();
    NoteAdapter adapter;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        noteListViewModel =
                new ViewModelProvider(this).get(NoteListViewModel.class);

        binding = FragmentNoteListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.recyclerview);

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMax(100);
        progressDoalog.setMessage(getResources().getString(R.string.please_wait));
        progressDoalog.show();

        noteListViewModel.getNotes().observe(getViewLifecycleOwner(), new Observer<NoteResponse>() {
            @Override
            public void onChanged(@Nullable NoteResponse noteResponse) {
                progressDoalog.dismiss();
                noteList = noteResponse.getNotes();
                initializeViewsAdapter();
            }
        });

        return root;
    }

    private void initializeViewsAdapter() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        registerForContextMenu(recyclerView);
        adapter = new NoteAdapter(getActivity(),noteList,this);
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
        noteListViewModel.getNotes().observe(getViewLifecycleOwner(), new Observer<NoteResponse>() {
            @Override
            public void onChanged(@Nullable NoteResponse noteResponse) {
                if(noteResponse.getNotes() != null) {
                    noteList = noteResponse.getNotes();
                    initializeViewsAdapter();
                }else{
                    Toast.makeText(getActivity(),"No Note Entries",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void clickPosition(int position, int id) {
        Intent intent = new Intent(getActivity(), NoteEditDeleteActivity.class);
        intent.putExtra("text", noteList.get(position).getMessage());
        intent.putExtra("id",  String.valueOf(noteList.get(position).getCommentID()));
        startActivity(intent);
    }
}