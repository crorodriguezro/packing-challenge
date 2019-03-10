package com.mobiquityinc.packer.internal;

import com.mobiquityinc.domain.Package;
import com.mobiquityinc.domain.PackingRequest;
import com.mobiquityinc.domain.Thing;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

/**
 * This implementation finds ALL the combinations between items and then sort them based on cost and weight.
 * Warning: Should not be used for packing request containing many items.
 */
public class SlowPacker implements Packer {

    /**
     * @param request a package request containing all the items and the package weight limit
     * @return The best solution otherwise an empty optional
     */
    public Optional<Package> pack(PackingRequest request) {
        Comparator<Package> byCost = comparing(Package::getTotalCost);
        Comparator<Package> byWeight = comparing(Package::getTotalWeight, reverseOrder());
        return getAllViablePackages(request)
                .max(byCost.thenComparing(byWeight));
    }

    /**
     * Builds the packages from the package request and then discards non-viable solutions
     * @param request a package request containing all the items and the package weight limit
     * @return Stream containing all the viable solutions
     */
    private Stream<Package> getAllViablePackages(PackingRequest request) {
        List<Thing> things = request.getThings();
        return getAllCombinations(things)
                .stream()
                .map(combination -> {
                    double totalWeight = combination
                            .stream()
                            .mapToDouble(Thing::getWeight)
                            .sum();
                    BigDecimal totalCost = combination
                            .stream()
                            .map(Thing::getCost)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    return new Package(totalWeight, totalCost, combination);
                })
                .filter(packageD -> packageD.getTotalWeight() <= request.getPackageCapacity());
    }

    private List<List<Thing>> getAllCombinations(List<Thing> things) {
        int arrayListInitialCapacity = 2 ^ things.size();
        List<List<Thing>> combinations = new ArrayList<>(arrayListInitialCapacity);
        combineThings(0, things, new ArrayList<>(), combinations);
        return combinations;
    }

    private void combineThings(int start, final List<Thing> things, List<Thing> combination, List<List<Thing>> combinations) {
        for (int i = start; i < things.size(); ++i) {
            combination = new ArrayList<>(combination);
            combination.add(things.get(i));
            combinations.add(combination);
            if (i < things.size()) {
                combineThings(i + 1, things, combination, combinations);
            }
            combination = combination.subList(0, combination.size() - 1);
        }
    }
}
