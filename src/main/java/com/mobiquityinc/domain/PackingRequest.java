package com.mobiquityinc.domain;

import java.util.Collections;
import java.util.List;

public class PackingRequest {
    private double packageCapacity;
    private List<Thing> things;

    public PackingRequest(double packageCapacity, List<Thing> things) {
        this.packageCapacity = packageCapacity;
        this.things = Collections.unmodifiableList(things);
    }

    public double getPackageCapacity() {
        return packageCapacity;
    }

    public List<Thing> getThings() {
        return things;
    }

    @Override
    public String toString() {
        return "PackingRequest{" +
                "packageCapacity=" + packageCapacity +
                ", things=" + things +
                '}';
    }
}
