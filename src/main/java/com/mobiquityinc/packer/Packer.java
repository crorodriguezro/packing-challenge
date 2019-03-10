package com.mobiquityinc.packer;

import com.mobiquityinc.domain.Package;
import com.mobiquityinc.domain.PackingRequest;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.io.FileParser;
import com.mobiquityinc.packer.internal.SlowPacker;
import com.mobiquityinc.validations.Validator;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Packer {

    private static com.mobiquityinc.packer.internal.Packer packer = new SlowPacker();
    private static FileParser fileParser = new FileParser();

    public static String pack(String filePath) throws APIException, IOException {
        List<PackingRequest> packingRequests = fileParser.parseFile(filePath);
        Validator.validate(packingRequests);
        return packingRequests
                .stream()
                .map(packingRequest -> packer.pack(packingRequest))
                .map(aPackage -> aPackage
                        .map(Package::getThingsIndices)
                        .orElse("-"))
                .collect(Collectors.joining("\n"));
    }
}
