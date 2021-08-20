package com.example.evetagenda.model;

import java.util.List;

public class MedicineResponse {
    private List<Medicine> medicines = null;
    private Error error;

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public Error getError() {
        return error;
    }
}
