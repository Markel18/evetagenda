package com.example.evetagenda.ui.customer.customer_home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.evetagenda.databinding.FragmentCustomerHomeBinding;
import com.example.evetagenda.model.Error;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomerHomeFragment extends Fragment {

    private CustomerViewModel customerViewModel;
    private FragmentCustomerHomeBinding binding;

    private String name;
    private String phone;
    private String email;
    private String code;
    private String afm;
    private String doy;
    private String area;
    private String numberOfAnimals;
    private String animalType;
    private String animalTribe;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        customerViewModel =
                new ViewModelProvider(this).get(CustomerViewModel.class);

        binding = FragmentCustomerHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button submitButton = binding.submitButton;
        final EditText nameText = binding.name;
        final EditText phoneText = binding.phone;
        final EditText emailText = binding.email;
        final EditText codeText = binding.codee;
        final EditText afmText = binding.afm;
        final EditText doyText = binding.doy;
        final EditText areaText = binding.area;
        final EditText numberOfAnimalsText = binding.numberOfAnimals;
        final EditText animalTypeText = binding.animalTypee;
        final EditText animalTribeText = binding.animalTribe;


        nameText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                name = nameText.getText().toString();
            }
        });

        phoneText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                phone = phoneText.getText().toString();
            }
        });

        emailText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                email = emailText.getText().toString();
            }
        });


        codeText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                code = codeText.getText().toString();
            }
        });


        afmText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                afm = afmText.getText().toString();
            }
        });

        doyText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                doy = doyText.getText().toString();
            }
        });

        areaText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                area = areaText.getText().toString();
            }
        });


        numberOfAnimalsText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                numberOfAnimals = numberOfAnimalsText.getText().toString();
            }
        });

        animalTypeText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                animalType = animalTypeText.getText().toString();
            }
        });

        animalTribeText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                animalTribe = animalTribeText.getText().toString();
            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                Call<Error> call = api.postCustomer(name,phone,email,code,afm,doy,area,numberOfAnimals,animalType,animalTribe,StartActivity.id);

                call.enqueue(new Callback<Error>() {
                    @Override
                    public void onResponse(Call<Error> call, Response<Error> response) {
                        progressDoalog.dismiss();
                        Log.d("CustomerHomeFragment", ""+response.body().getErrorCode());
                        if(!response.body().getErrorCode().equals("200")) {
                            Toast.makeText(getActivity(),"Αποτυχία",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(),"Επιτυχία",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Error> call, Throwable t) {
                                                                progressDoalog.dismiss();
                        progressDoalog.dismiss();
                        Log.d("CustomerHomeFragment", ""+t);
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