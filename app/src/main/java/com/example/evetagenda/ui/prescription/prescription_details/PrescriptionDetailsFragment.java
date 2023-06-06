package com.example.evetagenda.ui.prescription.prescription_details;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
import com.example.evetagenda.Api;
import com.example.evetagenda.R;
import com.example.evetagenda.StartActivity;
import com.example.evetagenda.adapter.MedicineSpinnerAdapter;
import com.example.evetagenda.databinding.FragmentPrescriptionDetailsBinding;
import com.example.evetagenda.model.Error;
import com.example.evetagenda.model.Medicine;
import com.example.evetagenda.ui.login.LoginActivity;
import com.example.evetagenda.ui.prescription.prescription_home.PrescriptionFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PrescriptionDetailsFragment extends Fragment {

    private PrescriptionDetailsViewModel prescriptionDetailsViewModel;
    private FragmentPrescriptionDetailsBinding binding;
    private Spinner mySpinner;
    private MedicineSpinnerAdapter adapter;

    private TextView dose;
    private TextView meat;
    private TextView milk;
    private TextView egg;
    private TextView honey;
    private TextView manual;
    private TextView otherNet;
    private TextView comments;
    private Button submit;
    private Bundle bundle;


    private int medicineId;
    private String doseText;
    private String meatText;
    private String milkText;
    private String eggText;
    private String honeyText;
    private String manualText;
    private String otherNetText;
    private String commentsText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        prescriptionDetailsViewModel =
                new ViewModelProvider(this).get(PrescriptionDetailsViewModel.class);

        binding = FragmentPrescriptionDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        bundle = this.getArguments();

        dose = binding.dose;
        meat = binding.meat;
        milk = binding.milk;
        egg = binding.egg;
        honey = binding.honey;
        manual = binding.manual;
        otherNet = binding.otherNet;
        comments = binding.comments;
        submit = binding.next;


        adapter = new MedicineSpinnerAdapter(getActivity(), R.layout.spinner_item, (ArrayList<Medicine>)bundle.getSerializable("medicines"));
        mySpinner = binding.spinner;
        mySpinner.setAdapter(adapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Medicine medicine = adapter.getItem(position);
                medicineId = medicine.getMedicinesID();
                doseText = medicine.getMedDosologia();
                meatText = medicine.getAnamoniKreas();
                milkText = medicine.getAnamoniGala();
                eggText = medicine.getAnamoniAuga();
                honeyText = medicine.getAnamoniMeli();
                dose.setText(medicine.getMedDosologia()+"");
                meat.setText(medicine.getAnamoniKreas()+"");
                milk.setText(medicine.getAnamoniGala()+"");
                egg.setText(medicine.getAnamoniAuga()+"");
                honey.setText(medicine.getAnamoniMeli()+"");

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });


        submit.setOnClickListener(v -> {


            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle("Submit Prescription");
            builder.setMessage("do you want confirm this action?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    final ProgressDialog progressDoalog;
                    progressDoalog = new ProgressDialog(getActivity());
                    progressDoalog.setMax(100);
                    progressDoalog.setMessage(getResources().getString(R.string.please_wait));
                    progressDoalog.show();


                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Api.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    Api api = retrofit.create(Api.class);
                    Call<Error> call = api.postPrescription(""+bundle.getString("currentDate"),"NOTYET","Not Yet",""+bundle.getString("addressText"),bundle.getInt("producerId"),""+bundle.getString("codeText"),""+bundle.getString("animalTypeText"),bundle.getString("causeText"),medicineId,doseText,manual.getText().toString(),meat.getText().toString(),milk.getText().toString(),egg.getText().toString(),honey.getText().toString(),otherNet.getText().toString(),comments.getText().toString(),bundle.getString("prescriptionNumber"),StartActivity.id);

                    call.enqueue(new Callback<Error>() {
                        @Override
                        public void onResponse(Call<Error> call, Response<Error> response) {
                            progressDoalog.dismiss();
                            Log.d("NoteHomeFragment", ""+response.body().getErrorCode());
                            if(!response.body().getErrorCode().equals("200")) {
                                Toast.makeText(getActivity(),"Αποτυχία",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getActivity(),"Επιτυχία",Toast.LENGTH_SHORT).show();
                                Fragment fragment = new PrescriptionFragment();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.deFragment, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        }

                        @Override
                        public void onFailure(Call<Error> call, Throwable t) {
                            progressDoalog.dismiss();
                            Log.d("NoteHomeFragment", ""+t);
                        }
                    });
                    dialog.dismiss();
                }

            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // I do not need any action here you might
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();

        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}