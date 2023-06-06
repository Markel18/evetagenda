package com.example.evetagenda.ui.medicine.medicine_home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.evetagenda.Api;
import com.example.evetagenda.R;
import com.example.evetagenda.StartActivity;
import com.example.evetagenda.databinding.FragmentMedicineHomeBinding;
import com.example.evetagenda.databinding.FragmentNoteHomeBinding;
import com.example.evetagenda.model.Error;
import com.example.evetagenda.model.Medicine;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MedicineHomeFragment extends Fragment {

    private MedicineHomeViewModel medicineHomeViewModel;
    private FragmentMedicineHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        medicineHomeViewModel =
                new ViewModelProvider(this).get(MedicineHomeViewModel.class);

        binding = FragmentMedicineHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button submitButton = binding.submitButton;
        final EditText nameTex = binding.medName;
        final EditText effectiveIngredientText = binding.drastikiOusia;
        final EditText company = binding.medCompany;
        final EditText beef = binding.anamoniBooeidi;
        final EditText goat = binding.anamoniAiges;
        final EditText sheep = binding.anamoniProbata;
        final EditText pig = binding.anamoniXoiroi;
        final EditText indianChicken = binding.anamoniIndornithes;
        final EditText bee = binding.anamoniMelisses;
        final EditText meat = binding.anamoniKreas;
        final EditText milk = binding.anamoniGala;
        final EditText egg = binding.anamoniAuga;
        final EditText honey = binding.anamoniMeli;
        final EditText dose = binding.medDosologia;
        final EditText recoverType = binding.medXronosTherapias;



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDoalog;
                progressDoalog = new ProgressDialog(getActivity());
                progressDoalog.setMax(100);
                progressDoalog.setMessage(getResources().getString(R.string.please_wait));
                progressDoalog.show();

                Medicine med = new Medicine();
                med.medName = nameTex.getText().toString();
                med.drastikiOusia = effectiveIngredientText.getText().toString();
                med.medCompany = company.getText().toString();
                med.anamoniBooeidi = beef.getText().toString();
                med.anamoniAiges = goat.getText().toString();
                med.anamoniProbata = sheep.getText().toString();
                med.anamoniXoiroi = pig.getText().toString();
                med.anamoniIndornithes = indianChicken.getText().toString();
                med.anamoniMelisses = bee.getText().toString();
                med.anamoniKreas = meat.getText().toString();
                med.anamoniGala = milk.getText().toString();
                med.anamoniAuga = egg.getText().toString();
                med.anamoniMeli = honey.getText().toString();
                med.medDosologia = dose.getText().toString();
                med.medXronosTherapias = recoverType.getText().toString();
                med.uid =  Integer.parseInt(StartActivity.id);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api api = retrofit.create(Api.class);
                Call<Error> call = api.postMedicine(med);

                call.enqueue(new Callback<Error>() {
                    @Override
                    public void onResponse(Call<Error> call, Response<Error> response) {
                        progressDoalog.dismiss();
                        Log.d("NoteHomeFragment", ""+response.body().getErrorCode());
                        if(!response.body().getErrorCode().equals("200")) {
                            Toast.makeText(getActivity(),"Αποτυχία",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(),"Επιτυχία",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Error> call, Throwable t) {
                        progressDoalog.dismiss();
                        Log.d("NoteHomeFragment", ""+t);
                    }
                });
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