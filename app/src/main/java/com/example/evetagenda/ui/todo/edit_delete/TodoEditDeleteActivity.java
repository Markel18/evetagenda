package com.example.evetagenda.ui.todo.edit_delete;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.evetagenda.Api;
import com.example.evetagenda.R;
import com.example.evetagenda.StartActivity;
import com.example.evetagenda.databinding.ActivityTodoDeleteEditBinding;
import com.example.evetagenda.model.Error;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TodoEditDeleteActivity extends AppCompatActivity {

    String text;
    String isDone;
    String id;
    private ActivityTodoDeleteEditBinding binding;

    EditText todoText;
    CheckBox isDoneCheckBox;
    Button submitButton;
    Button deleteButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_delete_edit);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_200)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Edit ToDo</font>"));

        todoText = findViewById(R.id.text_home);
        isDoneCheckBox = findViewById(R.id.done);
        submitButton = findViewById(R.id.submit_button);
        deleteButton = findViewById(R.id.delete_button);

        Intent intent = getIntent();
        text = intent.getStringExtra("text");
        isDone = intent.getStringExtra("isDone");
        id = intent.getStringExtra("id");

        todoText.setText(text);

        if(String.valueOf(isDone).equals("1")) {
            isDoneCheckBox.setChecked(true);
        }else{
            isDoneCheckBox.setChecked(false);
        }

        todoText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                text = todoText.getText().toString();
            }
        });

        isDoneCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                   if(isChecked == true) {
                       isDone = "1";
                   }else{
                       isDone = "2";
                   }
               }
           }
        );

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDoalog;
                progressDoalog = new ProgressDialog(TodoEditDeleteActivity.this);
                progressDoalog.setMax(100);
                progressDoalog.setMessage(getResources().getString(R.string.please_wait));
                progressDoalog.show();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api api = retrofit.create(Api.class);
                Call<Error> call = api.deleteToDo(StartActivity.id,id);
                call.enqueue(new Callback<Error>() {
                    @Override
                    public void onResponse(Call<com.example.evetagenda.model.Error> call, Response<Error> response) {
                        progressDoalog.dismiss();
                        Log.d("ToDoEditActivity", ""+response.body().getErrorCode());
                        if(!response.body().getErrorCode().equals("200")) {
                            Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<com.example.evetagenda.model.Error> call, Throwable t) {
                        Log.d("NoteHomeFragment", ""+t);
                    }
                });
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDoalog;
                progressDoalog = new ProgressDialog(TodoEditDeleteActivity.this);
                progressDoalog.setMax(100);
                progressDoalog.setMessage(getResources().getString(R.string.please_wait));
                progressDoalog.show();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api api = retrofit.create(Api.class);
                Call<Error> call = api.updateToDo(StartActivity.id,id,isDone,text);
                call.enqueue(new Callback<Error>() {
                    @Override
                    public void onResponse(Call<com.example.evetagenda.model.Error> call, Response<Error> response) {
                        progressDoalog.dismiss();
                        Log.d("ToDoEditActivity", ""+response.body().getErrorCode());
                        if(!response.body().getErrorCode().equals("200")) {
                            Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<com.example.evetagenda.model.Error> call, Throwable t) {
                        progressDoalog.dismiss();
                        Log.d("NoteHomeFragment", ""+t);
                    }
                });
            }
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}