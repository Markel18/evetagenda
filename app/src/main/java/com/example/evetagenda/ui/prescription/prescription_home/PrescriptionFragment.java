package com.example.evetagenda.ui.prescription.prescription_home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.evetagenda.R;
import com.example.evetagenda.adapter.ProducerSpinnerAdapter;
import com.example.evetagenda.databinding.FragmentPrescriptionBinding;
import com.example.evetagenda.model.Medicine;
import com.example.evetagenda.model.Producer;
import com.example.evetagenda.ui.prescription.prescription_details.PrescriptionDetailsFragment;
import com.example.evetagenda.ui.visit.edit_delete.VisitEditDeleteActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PrescriptionFragment extends Fragment {

    private FragmentPrescriptionBinding binding;

    private Spinner mySpinner;
    private ProducerSpinnerAdapter adapter;
    private TextView prescriptionNumber;
    private TextView code;
    private TextView animalType;
    private TextView address;
    private TextView cause;
    private Button next;
    private int producerId;
    private String causeText;
    private String addressText;
    private String prescriptionNumberText;
    private String currentDateAndTime;
    private String codeText;
    private String animalTypeText;
    private ArrayList<Medicine> medicineList;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PrescriptionViewModel prescriptionViewModel = new ViewModelProvider(this).get(PrescriptionViewModel.class);

        binding = FragmentPrescriptionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.currentDate;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        currentDateAndTime = sdf.format(new Date());
        textView.setText(currentDateAndTime);

        prescriptionNumber = binding.prescriptionNumber;
        address = binding.address;
        code = binding.code;
        animalType = binding.animalType;
        cause = binding.cause;
        next = binding.next;


        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMax(100);
        progressDoalog.setMessage(getResources().getString(R.string.please_wait));
        progressDoalog.show();


        cause.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                causeText = cause.getText().toString();
            }
        });


        code.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                codeText = code.getText().toString();
            }
        });


        animalType.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                animalTypeText = animalType.getText().toString();
            }
        });


        address.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                addressText = address.getText().toString();
            }
        });


        prescriptionViewModel.getPrescriptionDetails().observe(getViewLifecycleOwner(), prescriptionDetails -> {
            progressDoalog.dismiss();
            prescriptionNumberText = String.valueOf(prescriptionDetails.getPrescriptionNumber());
            medicineList = (ArrayList<Medicine>) prescriptionDetails.getMedicines();
            prescriptionNumber.setText(prescriptionNumberText);
            adapter = new ProducerSpinnerAdapter(getActivity(), R.layout.spinner_item, prescriptionDetails.getProducer());
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);

            mySpinner = binding.spinner;
            mySpinner.setAdapter(adapter);
            mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view,
                                           int position, long id) {
                    Producer user = adapter.getItem(position);
                    code.setText(user.getProdCodeEktrofis());
                    animalType.setText(user.getProdTypeAnimals());
                    producerId = user.getProdID();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapter) {  }
            });
        });

        next.setOnClickListener(v -> {


            if(TextUtils.isEmpty(code.getText()) || TextUtils.isEmpty(animalType.getText()) || TextUtils.isEmpty(address.getText()) || TextUtils.isEmpty(cause.getText())){
                Toast.makeText(getActivity(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
            }else {
                Fragment fragment = new PrescriptionDetailsFragment();
                Bundle arguments = new Bundle();

                arguments.putInt("producerId", producerId);
                arguments.putString("causeText", causeText);
                arguments.putString("codeText", codeText);
                arguments.putString("animalTypeText", animalTypeText);
                arguments.putString("addressText", addressText);
                arguments.putString("currentDate", currentDateAndTime);
                arguments.putString("prescriptionNumber", prescriptionNumberText);
                arguments.putSerializable("medicines", medicineList);

                fragment.setArguments(arguments);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.deFragment, fragment);
                fragmentTransaction.addToBackStack("tag");
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.commit();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}