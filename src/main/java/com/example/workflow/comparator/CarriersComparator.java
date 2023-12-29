package com.example.workflow.comparator;

import java.util.Comparator;
import java.util.List;

import com.example.workflow.model.Carriers;

public class CarriersComparator implements Comparator<Carriers> {

    private List<String> sortingOrder;

    public CarriersComparator(List<String> sortingOrder) {
        this.sortingOrder = sortingOrder;
    }

    @Override
    public int compare(Carriers carrier1, Carriers carrier2) {
        for (String field : sortingOrder) {
            int result = compareField(carrier1, carrier2, field);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    private int compareField(Carriers carrier1, Carriers carrier2, String field) {
        switch (field) {
            case "cost":
                return Integer.compare(carrier1.getCost(), carrier2.getCost());
            case "time":
                return Integer.compare(carrier1.getTime(), carrier2.getTime());
            case "capacity":
                return Integer.compare(carrier1.getCapacity(), carrier2.getCapacity());
            default:
                throw new IllegalArgumentException("Invalid sorting field: " + field);
        }
    }
}