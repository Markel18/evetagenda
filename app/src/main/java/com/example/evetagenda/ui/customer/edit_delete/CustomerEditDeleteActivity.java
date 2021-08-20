package com.example.evetagenda.ui.customer.edit_delete;

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

public class CustomerEditDeleteActivity extends AppCompatActivity {


    String id;
    Button submitButton;
    Button deleteButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_delete_edit);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_200)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Edit Customer</font>"));

        submitButton = findViewById(R.id.submit_button);
        deleteButton = findViewById(R.id.delete_button);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        final EditText nameText = findViewById(R.id.name);
        final EditText phoneText = findViewById(R.id.phone);
        final EditText emailText = findViewById(R.id.email);
        final EditText codeText = findViewById(R.id.codee);
        final EditText afmText = findViewById(R.id.afm);
        final EditText doyText = findViewById(R.id.doy);
        final EditText areaText = findViewById(R.id.area);
        final EditText numberOfAnimalsText = findViewById(R.id.number_of_animals);
        final EditText animalTypeText = findViewById(R.id.animal_typee);
        final EditText animalTribeText = findViewById(R.id.animal_tribe);


        nameText.setText(intent.getStringExtra("name"));
        phoneText.setText(intent.getStringExtra("phone"));
        emailText.setText(intent.getStringExtra("email"));
        codeText.setText(intent.getStringExtra("code"));
        afmText.setText(intent.getStringExtra("afm"));
        doyText.setText(intent.getStringExtra("doy"));
        areaText.setText(intent.getStringExtra("area"));
        numberOfAnimalsText.setText(intent.getStringExtra("num_of_animals"));
        animalTypeText.setText(intent.getStringExtra("animal_type")+"");
        animalTribeText.setText(intent.getStringExtra("animal_tribe")+"");


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDoalog;
                progressDoalog = new ProgressDialog(CustomerEditDeleteActivity.this);
                progressDoalog.setMax(100);
                progressDoalog.setMessage(getResources().getString(R.string.please_wait));
                progressDoalog.show();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api api = retrofit.create(Api.class);
                Call<Error> call = api.deleteCustomer(StartActivity.id,id);
                call.enqueue(new Callback<Error>() {
                    @Override
                    public void onResponse(Call<Error> call, Response<Error> response) {
                        Log.d("CustomerActivity", ""+response.body().getErrorCode());
                        if(!response.body().getErrorCode().equals("200")) {
                            Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        progressDoalog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Error> call, Throwable t) {
                        Log.d("CustomerActivity", ""+t);
                        progressDoalog.dismiss();
                    }
                });
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDoalog;
                progressDoalog = new ProgressDialog(CustomerEditDeleteActivity.this);
                progressDoalog.setMax(100);
                progressDoalog.setMessage(getResources().getString(R.string.please_wait));
                progressDoalog.show();
                
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api api = retrofit.create(Api.class);
                Call<Error> call = api.updateCustomer(StartActivity.id,id,nameText.getText().toString(),phoneText.getText().toString(),emailText.getText().toString(),codeText.getText().toString(),afmText.getText().toString(),doyText.getText().toString(),areaText.getText().toString(),numberOfAnimalsText.getText().toString(),animalTypeText.getText().toString(),animalTribeText.getText().toString());
                call.enqueue(new Callback<Error>() {
                    @Override
                    public void onResponse(Call<Error> call, Response<Error> response) {
                        Log.d("CustomerActivity", ""+response.body().getErrorCode());
                        progressDoalog.dismiss();
                        if(!response.body().getErrorCode().equals("200")) {
                            Toast.makeText(getApplicationContext(),"Αποτυχία",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Επιτυχία",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Error> call, Throwable t) {
                        Log.d("NoteHomeFragment", ""+t);
                        progressDoalog.dismiss();
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