package com.example.evetagenda.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.evetagenda.Api;
import com.example.evetagenda.R;
import com.example.evetagenda.StartActivity;
import com.example.evetagenda.UserActivity;
import com.example.evetagenda.databinding.ActivityLoginBinding;
import com.example.evetagenda.data.DatabaseHelper;
import com.example.evetagenda.model.UserInfoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#389099"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setBackgroundDrawable(colorDrawable);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;


        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);


        loginViewModel.getUserInfoResponse().observe(this, new Observer<UserInfoResponse>() {
            @Override
            public void onChanged(@Nullable UserInfoResponse producerResponse) {

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDoalog;
                progressDoalog = new ProgressDialog(LoginActivity.this);
                progressDoalog.setMax(100);
                progressDoalog.setMessage(getResources().getString(R.string.please_wait));
                progressDoalog.show();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api api = retrofit.create(Api.class);
                Call<UserInfoResponse> call = api.getUser(usernameEditText.getText().toString(),passwordEditText.getText().toString());

                call.enqueue(new Callback<UserInfoResponse>() {
                    @Override
                    public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                        Log.d("LoginActivity", ""+response.body().getError().getErrorCode());
                        progressDoalog.dismiss();

                        if(response.body().getError().getErrorCode().equals("200")) {
                            DatabaseHelper myDb = new DatabaseHelper(LoginActivity.this);
                            Boolean database = myDb.insertData(response.body().getUserInfo().getId()+"", response.body().getUserInfo().emailUsers,response.body().getUserInfo().pwdUsers,response.body().getUserInfo().getFnameUsers(),response.body().getUserInfo().getLnameUsers());
                            Log.d("LoginActivity", database.toString());
                            StartActivity.id = response.body().getUserInfo().getId()+"";
                            Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                            intent.putExtra("fullName", response.body().getUserInfo().getFnameUsers()+" "+response.body().getUserInfo().getLnameUsers());
                            intent.putExtra("email",response.body().getUserInfo().emailUsers);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),"Incorrect Info",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                        Log.d("LoginActivity", ""+t);
                        progressDoalog.dismiss();

                    }
                });
            }
        });
    }
}