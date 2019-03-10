package com.mobiquityinc.io;

import com.mobiquityinc.domain.PackingRequest;
import com.mobiquityinc.domain.Thing;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.exception.UncheckedAPIException;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileParser {
    public List<PackingRequest> parseFile(String filePath) throws APIException, IOException {
        if (filePath == null || filePath.isEmpty()) {
            throw new APIException("Invalid file path: " + filePath);
        }
        try {
            Path path = Paths.get(filePath);
            if (!path.toFile().exists()) {
                throw new APIException("File does not exist: " + filePath);
            }
            List<String> fileLines = Files.readAllLines(path);
            return fileLines
                    .stream()
                    .map(this::getPackageFromLine)
                    .collect(Collectors.toList());
        } catch (InvalidPathException | NumberFormatException | UncheckedAPIException e) {
            e.printStackTrace();
            throw new APIException("Invalid file path: " + filePath);
        }
    }

    private PackingRequest getPackageFromLine(String line) {
        String[] splitByColon = line.split(":");
        int packageCapacity = Integer.parseInt(splitByColon[0].trim());
        String[] splitByParentheses = splitByColon[1].split("[(]");
        List<Thing> things = Arrays.stream(splitByParentheses)
                .map(s -> s.replaceAll("€|[)]", ""))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    String[] unparsedThing = s.split(",");
                    return new Thing(
                            Integer.parseInt(unparsedThing[0]),
                            Double.parseDouble(unparsedThing[1]),
                            new BigDecimal(unparsedThing[2])
                    );
                })
                .collect(Collectors.toList());
        return new PackingRequest(
                packageCapacity,
                things
        );
    }
}
