package com.example.evetagenda.model;

import java.util.List;

public class ProducerResponse {
    private List<Producer> producers= null;
    private Error error;

    public List<Producer> getProducerList() {
        return producers;
    }

    public Error getError() {
        return error;
    }

    public ProducerResponse(List<Producer> producerList, Error error) {
        this.producers = producerList;
        this.error = error;
    }
}
