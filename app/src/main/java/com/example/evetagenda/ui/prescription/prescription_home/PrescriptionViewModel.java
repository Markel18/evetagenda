package com.example.evetagenda.ui.prescription.prescription_home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.evetagenda.Api;
import com.example.evetagenda.StartActivity;
import com.example.evetagenda.model.PrescriptionDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PrescriptionViewModel extends ViewModel {

    private MutableLiveData<PrescriptionDetails> prescriptionDetails;

    //we will call this method to get the data
    public LiveData<PrescriptionDetails> getPrescriptionDetails() {
        //if the list is null
        if (prescriptionDetails == null) {
            prescriptionDetails = new MutableLiveData<PrescriptionDetails>();
            //we will load it asynchronously from server in this method
            loadPrescriptionDetails();
        }

        //finally we will return the list
        return prescriptionDetails;
    }

    //This method is using Retrofit to get the JSON data from URL
    private void loadPrescriptionDetails() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<PrescriptionDetails> call = api.getPrescriptionDetails(StartActivity.id);

        call.enqueue(new Callback<PrescriptionDetails>() {
            @Override
            public void onResponse(Call<PrescriptionDetails> call, Response<PrescriptionDetails> response) {
                Log.d("PrescriptionDetails", "success :"+response.body());
                prescriptionDetails.setValue(response.body());
            }

            @Override
            public void onFailure(Call<PrescriptionDetails> call, Throwable t) {
                Log.d("PrescriptionDetails", "fail :"+t);
            }
        });
    }
}