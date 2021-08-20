package com.example.evetagenda.ui.customer.customer_list;


import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.evetagenda.Api;
import com.example.evetagenda.StartActivity;
import com.example.evetagenda.model.Producer;
import com.example.evetagenda.model.ProducerResponse;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CustomerListViewModel extends ViewModel {

    private MutableLiveData<ProducerResponse> producerResponse;

    //we will call this method to get the data
    public LiveData<ProducerResponse> getProducers() {
        //if the list is null
        if (producerResponse == null) {
            producerResponse = new MutableLiveData<ProducerResponse>();
            //we will load it asynchronously from server in this method
            loadProducers();
        }else{
            loadProducers();
        }

        //finally we will return the list
        return producerResponse;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadProducers() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ProducerResponse> call = api.getProducers(StartActivity.id);

        call.enqueue(new Callback<ProducerResponse>() {
            @Override
            public void onResponse(Call<ProducerResponse> call, Response<ProducerResponse> response) {
                //finally we are setting the list to our MutableLiveData
                producerResponse.setValue(response.body());
                Log.d("CustomerFragment", "Bika 1"+response.body());
            }

            @Override
            public void onFailure(Call<ProducerResponse> call, Throwable t) {
                Log.d("CustomerFragment", "Bika 2"+t);
            }
        });
    }
}

