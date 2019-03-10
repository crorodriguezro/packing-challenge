package com.mobiquityinc.domain;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Package {
    private Double totalWeight;
    private BigDecimal totalCost;
    private List<Thing> things;

    public Package(double totalWeight, BigDecimal totalCost, List<Thing> things) {
        this.totalWeight = totalWeight;
        this.totalCost = totalCost;
        this.things = Collections.unmodifiableList(things);
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public List<Thing> getThings() {
        return things;
    }

    public String getThingsIndices() {
        return things.stream()
                .map(thing -> String.valueOf(thing.getIndexNumber()))
                .collect(Collectors.joining(","));
    }
}
