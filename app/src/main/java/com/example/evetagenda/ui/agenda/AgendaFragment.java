package com.example.evetagenda.ui.agenda;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.evetagenda.R;
import com.example.evetagenda.databinding.FragmentAgendaBinding;
import com.example.evetagenda.model.EventResponse;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.MINUTE;

public class AgendaFragment extends Fragment {

    public AgendaViewModel agendaViewModel;
    private FragmentAgendaBinding binding;
    private List<com.example.evetagenda.model.Event> eventList;
    private static final String TAG = "Calendar";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        agendaViewModel =
                new ViewModelProvider(this).get(AgendaViewModel.class);

        binding = FragmentAgendaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView selectedDate = binding.selectedDate;
        final TextView eventText = binding.eventText;
        final CompactCalendarView compactCalendar =  root.findViewById(R.id.compactcalendar_view);
        // Set first day of week to Monday, defaults to Monday so calling setFirstDayOfWeek is not necessary
        // Use constants provided by Java Calendar class
        compactCalendar.setFirstDayOfWeek(Calendar.MONDAY);

        Date d = new Date();
        selectedDate.setText(sdf.format(d));
        eventText.setText("");

        agendaViewModel.getProducers().observe(getViewLifecycleOwner(), new Observer<EventResponse>() {
            @Override
            public void onChanged(@Nullable EventResponse eventResponse) {

                if(eventResponse.getEventsList() != null) {
                    eventList =eventResponse.getEventsList();
                    for(com.example.evetagenda.model.Event ev : eventList){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = null;
                        try {
                            date = sdf.parse(ev.getStart_event());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        long millis = date.getTime();
                        Event ev1 = new Event(Color.parseColor(ev.getColor()), millis, ev.getTitle());
                        compactCalendar.addEvent(ev1);
                    }
                }else{
                    Toast.makeText(getActivity(),"No Event Entries",Toast.LENGTH_SHORT).show();
                }
            }
        });

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendar.getEvents(dateClicked);
                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
                selectedDate.setText(sdf.format(dateClicked));
                String allEvents = "";

                if(!events.isEmpty()){
                    for(Event ev : events){
                        if(ev.getData() != null) {
                            Calendar time = Calendar.getInstance();
                            time.setTimeInMillis(ev.getTimeInMillis());
                            time.get(Calendar.HOUR_OF_DAY);
                            time.getTime();
                            allEvents = allEvents +time.get(Calendar.HOUR_OF_DAY)+":"+time.get(MINUTE)+" | "+ev.getData().toString() + "\n";
                        }
                    }
                }

                eventText.setText(allEvents);

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
                selectedDate.setText(sdf.format(firstDayOfNewMonth));
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