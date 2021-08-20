package com.example.evetagenda.ui.visit.visit_home;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.evetagenda.Api;
import com.example.evetagenda.R;
import com.example.evetagenda.StartActivity;
import com.example.evetagenda.adapter.ProducerSpinnerAdapter;
import com.example.evetagenda.databinding.FragmentVisitHomeBinding;
import com.example.evetagenda.model.Error;
import com.example.evetagenda.model.Event;
import com.example.evetagenda.model.Medicine;
import com.example.evetagenda.model.Producer;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VisitHomeFragment extends Fragment {

    private VisitHomeViewModel visitHomeViewModel;
    private FragmentVisitHomeBinding binding;
    private EditText dateFrom;
    private EditText dateTo;
    private EditText timeFrom;
    private EditText timeTo;
    private EditText title;
    private Spinner supplier;
    private EditText phone;
    private EditText area;
    private EditText visitReason;
    private Calendar myCalendar;
    private ProducerSpinnerAdapter producerAdapter;
    private int producerId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        visitHomeViewModel =
                new ViewModelProvider(this).get(VisitHomeViewModel.class);

        binding = FragmentVisitHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button submitButton = binding.submitButton;

        myCalendar = Calendar.getInstance();
        dateFrom = binding.dateFrom;
        dateTo = binding.dateTo;
        timeFrom = binding.timeFrom;
        timeTo = binding.timeTo;
        title = binding.title;
        supplier = binding.producerSpinner;
        area = binding.perioxi;
        visitReason = binding.aitiologiaEpiskepsis;
        phone = binding.prodPhone;
        Spinner spinner = (Spinner) binding.spinner;

        String [] values =
                {"Μπλε","Κόκκινο","Πράσινο","Κίτρινο","Μαύρο"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }

        };


        dateFrom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
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
                new DatePickerDialog(getActivity(), date, myCalendar
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
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
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
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeTo.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        visitHomeViewModel.getPrescriptionDetails().observe(getViewLifecycleOwner(), prescriptionDetails -> {
            producerAdapter = new ProducerSpinnerAdapter(this.getActivity(), R.layout.spinner_item, prescriptionDetails.getProducer());
            producerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            supplier.setAdapter(producerAdapter);
            supplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view,
                                           int position, long id) {
                    Producer user = producerAdapter.getItem(position);

                    producerId = user.getProdID();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapter) {  }
            });
        });



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDoalog;
                progressDoalog = new ProgressDialog(getActivity());
                progressDoalog.setMax(100);
                progressDoalog.setMessage(getResources().getString(R.string.please_wait));
                progressDoalog.show();

                Event event = new Event();
                event.setTitle(title.getText().toString());
                event.setProd_name(producerId);
                event.setPerioxi(area.getText().toString());
                event.setAitiologia_episkepsis(visitReason.getText().toString());
                event.setProd_phone(phone.getText().toString());
                event.setStart_event(dateFrom.getText().toString()+" "+timeFrom.getText().toString());
                event.setEnd_event(dateTo.getText().toString()+" "+timeTo.getText().toString());
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
                Call<Error> call = api.postEvent(event);

                call.enqueue(new Callback<Error>() {
                    @Override
                    public void onResponse(Call<Error> call, Response<Error> response) {
                        progressDoalog.dismiss();
                        Log.d("NoteHomeFragment", ""+response.body().getErrorCode());
                        if(!response.body().getErrorCode().equals("200")) {
                            Toast.makeText(getActivity(),"Αποτυχία",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(),"Επιτυχία",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Error> call, Throwable t) {
                        progressDoalog.dismiss();
                        Log.d("NoteHomeFragment", ""+t);
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