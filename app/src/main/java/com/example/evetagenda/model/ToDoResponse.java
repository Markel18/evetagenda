package com.example.evetagenda.model;

import java.util.List;

public class ToDoResponse {
    private List<ToDo> todos= null;
    private Error error;

    public List<ToDo> getTodos() {
        return todos;
    }

    public Error getError() {
        return error;
    }
}
