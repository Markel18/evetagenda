package com.example.evetagenda.ui.visit.edit_delete;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.evetagenda.Api;
import com.example.evetagenda.R;
import com.example.evetagenda.StartActivity;
import com.example.evetagenda.adapter.ProducerSpinnerAdapter;
import com.example.evetagenda.model.Error;
import com.example.evetagenda.model.Event;
import com.example.evetagenda.model.Medicine;
import com.example.evetagenda.model.PrescriptionDetails;
import com.example.evetagenda.model.Producer;
import com.example.evetagenda.ui.login.LoginActivity;
import com.example.evetagenda.ui.visit.visit_home.VisitHomeViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VisitEditDeleteActivity extends AppCompatActivity {

    String text;
    String id;

    private Button submitButton;
    private Button deleteButton;
    private EditText dateFrom;
    private EditText dateTo;
    private EditText timeFrom;
    private EditText timeTo;
    private EditText title;
    private Spinner supplier;
    private EditText phone;
    private EditText area;
    private EditText visitReason;
    private Calendar myCalendar = Calendar.getInstance();
    private ProducerSpinnerAdapter producerAdapter;
    private int producerId;
    int selectedIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_delete_edit);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_200)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Edit Event</font>"));

        submitButton = findViewById(R.id.submit_button);
        deleteButton = findViewById(R.id.delete_button);

        dateFrom = findViewById(R.id.date_from);
        dateTo = findViewById(R.id.date_to);
        timeFrom = findViewById(R.id.time_from);
        timeTo = findViewById(R.id.time_to);
        title = findViewById(R.id.title);
        supplier = findViewById(R.id.producer_spinner);
        phone = findViewById(R.id.prod_phone);
        area = findViewById(R.id.perioxi);
        visitReason = findViewById(R.id.aitiologia_episkepsis);

        Intent intent = getIntent();
        Event intentEvent = (Event)intent.getSerializableExtra("Event");
        dateFrom.setText(intentEvent.getStart_event().split(" ")[0]+"");
        dateTo.setText(intentEvent.getEnd_event().split(" ")[0]+"");
        timeFrom.setText(intentEvent.getStart_event().split(" ")[1]+"");
        timeTo.setText(String.valueOf(intentEvent.getEnd_event().split(" ")[1]+""));
        title.setText(String.valueOf(intentEvent.getTitle()));
        phone.setText(String.valueOf(intentEvent.getProd_phone()));
        area.setText(String.valueOf(intentEvent.getPerioxi()));
        visitReason.setText(String.valueOf(intentEvent.getAitiologia_episkepsis()));


        Spinner spinner = findViewById(R.id.spinner);

        String [] values =
                {"Μπλε","Κόκκινο","Πράσινο","Κίτρινο","Μαύρο"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(VisitEditDeleteActivity.this, R.layout.spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);



        if(intentEvent.getColor().equals("#0000FF")){
            spinner.setSelection(adapter.getPosition("Μπλε"));
        }else if(intentEvent.getColor().equals("#FF0000")){
            spinner.setSelection(adapter.getPosition("Κόκκινο"));
        }else if(intentEvent.getColor().equals("#00ff00")) {
            spinner.setSelection(adapter.getPosition("Πράσινο"));
        }else if(intentEvent.getColor().equals("#ffff00")) {
            spinner.setSelection(adapter.getPosition("Κίτρινο"));
        }else if(intentEvent.getColor().equals("#000000")) {
            spinner.setSelection(adapter.getPosition("Μαύρο"));
        }


        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(VisitEditDeleteActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage(getResources().getString(R.string.please_wait));
        progressDoalog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<PrescriptionDetails> call = api.getPrescriptionDetails(StartActivity.id);

        call.enqueue(new Callback<PrescriptionDetails>() {
            @Override
            public void onResponse(Call<PrescriptionDetails> call, Response<PrescriptionDetails> response) {
                progressDoalog.dismiss();
                producerAdapter = new ProducerSpinnerAdapter(VisitEditDeleteActivity.this, R.layout.spinner_item, response.body().getProducer());
                producerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                supplier.setAdapter(producerAdapter);
                supplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view,
                                               int position, long id) {
                        Producer user = producerAdapter.getItem(position);
                        producerId = user.getProdID();

                        Producer selectedProd = new Producer();
                        for(Producer pr : response.body().getProducer()){
                            if(pr.getProdID() == intentEvent.getProd_name()){
                                selectedProd = pr;
                            }
                        }
                        selectedIndex = producerAdapter.getPosition(selectedProd);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapter) {  }
                });
                supplier.setSelection(selectedIndex);
            }

            @Override
            public void onFailure(Call<PrescriptionDetails> call, Throwable t) {
                progressDoalog.dismiss();
                Log.d("PrescriptionDetails", "fail :"+t);
            }
        });


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                // updateLabel();
            }

        };


        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(VisitEditDeleteActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dateFrom.setText(sdf.format(myCalendar.getTime()));
            }
        });

        dateTo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(VisitEditDeleteActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dateTo.setText(sdf.format(myCalendar.getTime()));
            }
        });

        timeFrom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(VisitEditDeleteActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeFrom.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        timeTo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(VisitEditDeleteActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeTo.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDoalog;
                progressDoalog = new ProgressDialog(VisitEditDeleteActivity.this);
                progressDoalog.setMax(100);
                progressDoalog.setMessage(getResources().getString(R.string.please_wait));
                progressDoalog.show();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api api = retrofit.create(Api.class);
                Call<Error> call = api.deleteEvent(StartActivity.id,intentEvent.getId()+"");
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

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDoalog;
                progressDoalog = new ProgressDialog(VisitEditDeleteActivity.this);
                progressDoalog.setMax(100);
                progressDoalog.setMessage(getResources().getString(R.string.please_wait));
                progressDoalog.show();

                Event event = new Event();
                event.setStart_event(dateFrom.getText().toString()+" "+timeFrom.getText().toString());
                event.setEnd_event(dateTo.getText().toString()+" "+timeTo.getText().toString());
                event.setProd_phone(phone.getText().toString());
                event.setAitiologia_episkepsis(visitReason.getText().toString());
                event.setPerioxi(area.getText().toString());
                event.setProd_name(producerId);
                event.setTitle(title.getText().toString());
                event.setId(intentEvent.getId());
                event.setUid(Integer.parseInt(StartActivity.id));

                if(spinner.getSelectedItem().toString().equals("Μπλε")){
                    event.setColor("#0000FF");
                }else if(spinner.getSelectedItem().toString().equals("Κόκκινο")){
                    event.setColor("#FF0000");
                }else if(spinner.getSelectedItem().toString().equals("Πράσινο")) {
                    event.setColor("#00ff00");
                }else if(spinner.getSelectedItem().toString().equals("Κίτρινο")) {
                    event.setColor("#ffff00");
                }else if(spinner.getSelectedItem().toString().equals("Μαύρο")) {
                    event.setColor("#000000");
                }


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api api = retrofit.create(Api.class);
                Call<Error> call = api.updateEvent(event);
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