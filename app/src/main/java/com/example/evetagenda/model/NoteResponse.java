package com.example.evetagenda.model;

import java.util.List;

public class NoteResponse {
    private List<Note> notes;
    private Error error;


    public List<Note> getNotes() {
        return notes;
    }

    public Error getError() {
        return error;
    }
}
