package com.example.evetagenda.ui.login;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.evetagenda.Api;
import com.example.evetagenda.model.UserInfoResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginViewModel extends ViewModel {

    private MutableLiveData<UserInfoResponse> userInfo;

    //we will call this method to get the data
    public LiveData<UserInfoResponse> getUserInfoResponse() {
        //if the list is null
        if (userInfo == null) {
            userInfo = new MutableLiveData<UserInfoResponse>();
            //we will load it asynchronously from server in this method
            loadUser();
        }

        //finally we will return the list
        return userInfo;
    }

    //This method is using Retrofit to get the JSON data from URL
    private void loadUser() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<UserInfoResponse> call = api.getUser("evetagenda@gmail.com","$2y$10$fB4sbrzi.JZ7U4o0vyHruOGyHg5GXT.pRdWpYCvr9fgrOQGHANVDW");

        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                //finally we are setting the list to our MutableLiveData
                userInfo.setValue(response.body());
                Log.d("LoginVieweModel", "Bika 1"+response.body());
            }
            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                Log.d("LoginVieweModel", "Bika 2"+t);
            }
        });
    }
}

