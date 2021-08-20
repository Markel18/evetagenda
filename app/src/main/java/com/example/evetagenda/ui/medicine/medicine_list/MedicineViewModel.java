package com.example.evetagenda.ui.medicine.medicine_list;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.evetagenda.Api;
import com.example.evetagenda.StartActivity;
import com.example.evetagenda.model.MedicineResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MedicineViewModel extends ViewModel {

    private MutableLiveData<MedicineResponse> medicineResponse;

    //we will call this method to get the data
    public LiveData<MedicineResponse> getNotes() {
        //if the list is null
        if (medicineResponse == null) {
            medicineResponse = new MutableLiveData<MedicineResponse>();
            //we will load it asynchronously from server in this method
            loadMedicines();
        }else{
            loadMedicines();
        }

        //finally we will return the list
        return medicineResponse;
    }

    //This method is using Retrofit to get the JSON data from URL
    private void loadMedicines() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<MedicineResponse> call = api.getMedicines(StartActivity.id);

        call.enqueue(new Callback<MedicineResponse>() {
            @Override
            public void onResponse(Call<MedicineResponse> call, Response<MedicineResponse> response) {
                //finally we are setting the list to our MutableLiveData
                medicineResponse.setValue(response.body());
                Log.d("MedicineViewModel", "Bika 1"+response.body().getError().getErrorCode());

            }

            @Override
            public void onFailure(Call<MedicineResponse> call, Throwable t) {
                Log.d("MedicineViewModel", "Bika 2"+t);

            }
        });
    }
}