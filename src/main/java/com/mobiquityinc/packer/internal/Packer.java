package com.mobiquityinc.packer.internal;

import com.mobiquityinc.domain.Package;
import com.mobiquityinc.domain.PackingRequest;

import java.util.Optional;

public interface Packer {
    /**
     * Returns the best cost/weight package
     * @param request a package request containing all the items and the package weight limit
     * @return a package with the best cost/weight relation
     */
    Optional<Package> pack(PackingRequest request);
}
