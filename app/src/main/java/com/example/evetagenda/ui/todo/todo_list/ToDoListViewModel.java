package com.example.evetagenda.ui.todo.todo_list;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.evetagenda.Api;
import com.example.evetagenda.StartActivity;
import com.example.evetagenda.model.ToDoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ToDoListViewModel extends ViewModel {

    private MutableLiveData<ToDoResponse> todoResponse;

    //we will call this method to get the data
    public LiveData<ToDoResponse> getNotes() {
        //if the list is null
        if (todoResponse == null) {
            todoResponse = new MutableLiveData<ToDoResponse>();
            //we will load it asynchronously from server in this method
            loadToDos();
        }else{
            loadToDos();
        }

        //finally we will return the list
        return todoResponse;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadToDos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ToDoResponse> call = api.getToDo(StartActivity.id);

        call.enqueue(new Callback<ToDoResponse>() {
            @Override
            public void onResponse(Call<ToDoResponse> call, Response<ToDoResponse> response) {
                //finally we are setting the list to our MutableLiveData
                todoResponse.setValue(response.body());
                Log.d("NoteListFragment", "Bika 1"+response.body().getError().getErrorCode());

            }

            @Override
            public void onFailure(Call<ToDoResponse> call, Throwable t) {
                Log.d("NoteListFragment", "Bika 2"+t);
            }
        });
    }
}