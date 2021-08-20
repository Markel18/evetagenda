package com.example.evetagenda.ui.medicine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.evetagenda.R;
import com.example.evetagenda.databinding.FragmentNoteBinding;
import com.example.evetagenda.ui.medicine.medicine_home.MedicineHomeFragment;
import com.example.evetagenda.ui.medicine.medicine_list.MedicineListFragment;
import com.example.evetagenda.ui.note.note_home.NoteHomeFragment;
import com.example.evetagenda.ui.note.note_list.NoteListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MedicineFragment extends Fragment {

    private FragmentNoteBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNoteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        BottomNavigationView navigation = root.findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, new MedicineListFragment()).commit();



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.home:
                    fragmentTransaction.replace(R.id.flFragment, new MedicineHomeFragment()).commit();
                    return true;
                case R.id.list:
                    fragmentTransaction.replace(R.id.flFragment, new MedicineListFragment()).commit();
                    return true;
            }
            return false;
        }

    };

}