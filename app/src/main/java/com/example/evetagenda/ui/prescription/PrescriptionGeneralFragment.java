package com.example.evetagenda.ui.prescription;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.evetagenda.R;
import com.example.evetagenda.databinding.PrescriptionGeneralFragmentBinding;
import com.example.evetagenda.ui.prescription.prescription_home.PrescriptionFragment;

public class PrescriptionGeneralFragment extends Fragment{


    private PrescriptionGeneralFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = PrescriptionGeneralFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Fragment fragment = new PrescriptionFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.deFragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


        return root;
    }


}