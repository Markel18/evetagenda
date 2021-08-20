package com.example.evetagenda.ui.todo.todo_home;

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
import com.example.evetagenda.databinding.FragmentNoteHomeBinding;
import com.example.evetagenda.databinding.FragmentTodoHomeBinding;
import com.example.evetagenda.model.Error;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ToDoHomeFragment extends Fragment {

    private ToDoViewModel toDoViewModel;
    private FragmentTodoHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toDoViewModel =
                new ViewModelProvider(this).get(ToDoViewModel.class);

        binding = FragmentTodoHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final EditText editText = binding.textHome;
        final Button submitButton = binding.submitButton;

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
                Call<Error> call = api.postToDo(editText.getText().toString(),StartActivity.id);

                call.enqueue(new Callback<Error>() {
                    @Override
                    public void onResponse(Call<Error> call, Response<Error> response) {
                        progressDoalog.dismiss();
                        Log.d("ToDoHomeFragment", ""+response.body().getErrorCode());
                        if(!response.body().getErrorCode().equals("200")) {
                            Toast.makeText(getActivity(),"Αποτυχία",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(),"Επιτυχία",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Error> call, Throwable t) {
                        progressDoalog.dismiss();
                        Log.d("ToDoHomeFragment", ""+t);
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