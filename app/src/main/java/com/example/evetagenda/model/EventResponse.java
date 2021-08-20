package com.example.evetagenda.model;

import java.util.List;

public class EventResponse {
    private List<Event> events= null;
    private Error error;

    public List<Event> getEventsList() {
        return events;
    }

    public Error getError() {
        return error;
    }

    public EventResponse(List<Event> events, Error error) {
        this.events = events;
        this.error = error;
    }

}
