package com.example.evetagenda.ui.note.note_list;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.evetagenda.Api;
import com.example.evetagenda.StartActivity;
import com.example.evetagenda.model.NoteResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NoteListViewModel extends ViewModel {

    private MutableLiveData<NoteResponse> noteResponse;

    //we will call this method to get the data
    public LiveData<NoteResponse> getNotes() {
        //if the list is null
        if (noteResponse == null) {
            noteResponse = new MutableLiveData<NoteResponse>();
            //we will load it asynchronously from server in this method
            loadNotes();
        }else{
            loadNotes();
        }
        //finally we will return the list
        return noteResponse;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadNotes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<NoteResponse> call = api.getNotes(StartActivity.id);

        call.enqueue(new Callback<NoteResponse>() {
            @Override
            public void onResponse(Call<NoteResponse> call, Response<NoteResponse> response) {
                //finally we are setting the list to our MutableLiveData
                noteResponse.setValue(response.body());
                Log.d("NoteListFragment", "Bika 1"+response.body().getError().getErrorCode());

            }

            @Override
            public void onFailure(Call<NoteResponse> call, Throwable t) {
                Log.d("NoteListFragment", "Bika 2"+t);

            }
        });
    }
}