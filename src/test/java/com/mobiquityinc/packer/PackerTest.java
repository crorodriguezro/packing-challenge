package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PackerTest {

    @Test
    void pack() throws APIException, IOException {
        String inputFilePath = new File("src/test/resources/input.txt").getAbsolutePath();
        String realOutputString = Packer.pack(inputFilePath);
        System.out.println(realOutputString);
        String[] realOutputArray = realOutputString.split("\\r?\\n");
        List<String> realOutput = Arrays.asList(realOutputArray);
        List<String> testOutput = Files.readAllLines(Paths.get("src/test/resources/output.txt"));
        assertEquals(testOutput, realOutput);
    }
}
