package com.mobiquityinc.domain;

import java.math.BigDecimal;

public class Thing {
    private int indexNumber;
    private double weight;
    private BigDecimal cost;

    public Thing(int indexNumber, double weight, BigDecimal cost) {
        this.indexNumber = indexNumber;
        this.weight = weight;
        this.cost = cost;
    }

    public int getIndexNumber() {
        return indexNumber;
    }

    public double getWeight() {
        return weight;
    }

    public BigDecimal getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Thing{" +
                "indexNumber=" + indexNumber +
                ", weight=" + weight +
                ", cost=" + cost +
                '}';
    }
}
