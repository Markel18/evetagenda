package com.example.evetagenda.ui.medicine.edit_delete;

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
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.evetagenda.Api;
import com.example.evetagenda.R;
import com.example.evetagenda.StartActivity;
import com.example.evetagenda.databinding.ActivityTodoDeleteEditBinding;
import com.example.evetagenda.model.Error;
import com.example.evetagenda.model.Medicine;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MedicineEditDeleteActivity extends AppCompatActivity {

    String text;
    String id;

    Button submitButton;
    Button deleteButton;
    EditText medName;
    EditText effectiveIngredientText;
    EditText company ;
    EditText beef;
    EditText goat;
    EditText sheep;
    EditText pig;
    EditText indianChicken;
    EditText bee;
    EditText meat;
    EditText milk;
    EditText egg;
    EditText honey;
    EditText dose;
    EditText recoverType;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_delete_edit);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_200)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Edit Medicine</font>"));

        submitButton = findViewById(R.id.submit_button);
        deleteButton = findViewById(R.id.delete_button);

        medName = findViewById(R.id.medName);
        effectiveIngredientText = findViewById(R.id.drastikiOusia);
        company = findViewById(R.id.medCompany);
        beef = findViewById(R.id.anamoniBooeidi);
        goat = findViewById(R.id.anamoniAiges);
        sheep = findViewById(R.id.anamoniProbata);
        pig = findViewById(R.id.anamoniXoiroi);
        indianChicken = findViewById(R.id.anamoniIndornithes);
        bee = findViewById(R.id.anamoniMelisses);
        meat = findViewById(R.id.anamoniKreas);
        milk = findViewById(R.id.anamoniGala);
        egg = findViewById(R.id.anamoniAuga);
        honey = findViewById(R.id.anamoniMeli);
        dose = findViewById(R.id.medDosologia);
        recoverType = findViewById(R.id.medXronosTherapias);

        Intent intent = getIntent();
        Medicine intentMed = (Medicine)intent.getSerializableExtra("Medicine");

        medName.setText(intentMed.getMedName());
        effectiveIngredientText.setText(intentMed.getDrastikiOusia());
        company.setText(intentMed.getMedCompany());
        beef.setText(String.valueOf(intentMed.getAnamoniBooeidi()));
        goat.setText(String.valueOf(intentMed.getAnamoniAiges()));
        sheep.setText(String.valueOf(intentMed.getAnamoniProbata()));
        pig.setText(String.valueOf(intentMed.getAnamoniXoiroi()));
        indianChicken.setText(String.valueOf(intentMed.getAnamoniIndornithes()));
        bee.setText(String.valueOf(intentMed.getAnamoniMelisses()));
        meat.setText(String.valueOf(intentMed.getAnamoniKreas()));
        milk.setText(String.valueOf(intentMed.getAnamoniGala()));
        egg.setText(String.valueOf(intentMed.getAnamoniAuga()));
        honey.setText(String.valueOf(intentMed.getAnamoniMeli()));
        dose.setText(String.valueOf(intentMed.getMedDosologia()));
        recoverType.setText(String.valueOf(intentMed.getMedXronosTherapias()));

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDoalog;
                progressDoalog = new ProgressDialog(MedicineEditDeleteActivity.this);
                progressDoalog.setMax(100);
                progressDoalog.setMessage(getResources().getString(R.string.please_wait));
                progressDoalog.show();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api api = retrofit.create(Api.class);
                Call<Error> call = api.deleteMedicine(StartActivity.id,intentMed.getMedicinesID()+"");
                call.enqueue(new Callback<Error>() {
                    @Override
                    public void onResponse(Call<Error> call, Response<Error> response) {
                        progressDoalog.dismiss();
                        Log.d("MedicineActivity", ""+response.body().getErrorCode());
                        if(!response.body().getErrorCode().equals("200")) {
                            Toast.makeText(getApplicationContext(),"Αποτυχία",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Επιτυχία",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Error> call, Throwable t) {
                        progressDoalog.dismiss();
                        Log.d("MedicineActivity", ""+t);
                    }
                });
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                final ProgressDialog progressDoalog;
                progressDoalog = new ProgressDialog(MedicineEditDeleteActivity.this);
                progressDoalog.setMax(100);
                progressDoalog.setMessage(getResources().getString(R.string.please_wait));
                progressDoalog.show();

                Medicine med = new Medicine();
                med.medName = medName.getText().toString();
                med.drastikiOusia = effectiveIngredientText.getText().toString();
                med.medCompany = company.getText().toString();
                med.anamoniBooeidi = beef.getText().toString();
                med.anamoniAiges = goat.getText().toString();
                med.anamoniProbata = sheep.getText().toString();
                med.anamoniXoiroi = pig.getText().toString();
                med.anamoniIndornithes = indianChicken.getText().toString();
                med.anamoniMelisses = bee.getText().toString();
                med.anamoniKreas = meat.getText().toString();
                med.anamoniGala = milk.getText().toString();
                med.anamoniAuga = egg.getText().toString();
                med.anamoniMeli = honey.getText().toString();
                med.medDosologia = dose.getText().toString();
                med.medXronosTherapias = recoverType.getText().toString();
                med.medicinesID = intentMed.getMedicinesID();
                med.uid =  Integer.parseInt(StartActivity.id);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api api = retrofit.create(Api.class);
                Call<Error> call = api.updateMedicine(med);
                call.enqueue(new Callback<Error>() {
                    @Override
                    public void onResponse(Call<Error> call, Response<Error> response) {
                        progressDoalog.dismiss();
                        Log.d("MedicineActivity", ""+response.body().getErrorCode());
                        if(!response.body().getErrorCode().equals("200")) {
                            Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Error> call, Throwable t) {
                        progressDoalog.dismiss();
                        Log.d("MedicineActivity", ""+t);
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