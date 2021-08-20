package com.example.evetagenda.model;

import java.util.List;

public class PrescriptionDetails {
    private List<Medicine> medicines= null;
    private List<Producer> producers = null;
    private int prescriptionNumber;

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public List<Producer> getProducer() {
        return producers;
    }

    public int getPrescriptionNumber() {
        return prescriptionNumber;
    }
}
