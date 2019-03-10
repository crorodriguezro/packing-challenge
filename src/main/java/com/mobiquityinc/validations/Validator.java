package com.mobiquityinc.validations;

import com.mobiquityinc.domain.PackingRequest;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.exception.UncheckedAPIException;

import java.math.BigDecimal;
import java.util.List;

public class Validator {
    private static int MAX_THING_WEIGHT = 100;
    private static BigDecimal MAX_THING_COST = BigDecimal.valueOf(100);
    private static int MAX_PACKAGE_WEIGHT = 100;
    private static int MAX_PACKAGE_ITEMS = 15;

    public static void validate(List<PackingRequest> packingRequests) throws APIException {
        try {
            packingRequests.forEach(packingRequest -> {
                validatePackage(packingRequest);
                validateThings(packingRequest);
            });
        } catch (UncheckedAPIException e) {
            e.printStackTrace();
            throw new APIException("Invalid parameters");
        }
    }

    private static void validatePackage(PackingRequest packingRequest) {
        if(packingRequest.getPackageCapacity() > MAX_PACKAGE_WEIGHT) {
            throw new UncheckedAPIException("Incorrect parameter");
        }
        if(packingRequest.getThings().size() > MAX_PACKAGE_ITEMS) {
            throw new UncheckedAPIException("Incorrect parameter");
        }
    }

    private static void validateThings(PackingRequest packingRequest) {
        packingRequest
                .getThings()
                .forEach(thing -> {
                    boolean costBiggerThan100 = thing.getCost().compareTo(MAX_THING_COST) > 0;
                    boolean weightBiggerThan100 = thing.getWeight() > MAX_THING_WEIGHT;
                    if(costBiggerThan100 || weightBiggerThan100) {
                        throw new UncheckedAPIException("Incorrect parameter");
                    }
                });
    }
}
