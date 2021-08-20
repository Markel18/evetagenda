package com.example.evetagenda.ui.agenda;

import android.app.ProgressDialog;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.evetagenda.Api;
import com.example.evetagenda.R;
import com.example.evetagenda.StartActivity;
import com.example.evetagenda.model.EventResponse;
import com.example.evetagenda.ui.login.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AgendaViewModel extends ViewModel {

    private MutableLiveData<EventResponse> eventResponse;

    //we will call this method to get the data
    public LiveData<EventResponse> getProducers() {
        //if the list is null
        if (eventResponse == null) {
            eventResponse = new MutableLiveData<EventResponse>();
            //we will load it asynchronously from server in this method
            loadEvents();
        }

        //finally we will return the list
        return eventResponse;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadEvents() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<EventResponse> call = api.getEvents(StartActivity.id);


        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                //finally we are setting the list to our MutableLiveData
                eventResponse.setValue(response.body());
                Log.d("CustomerFragment", "Bika 1"+response.body());
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Log.d("CustomerFragment", "Bika 2"+t);
            }
        });
    }
}