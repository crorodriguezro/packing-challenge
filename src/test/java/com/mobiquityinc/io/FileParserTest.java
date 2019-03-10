package com.mobiquityinc.io;

import com.mobiquityinc.domain.PackingRequest;
import com.mobiquityinc.exception.APIException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileParserTest {

    private static FileParser fileParser;

    @BeforeAll
    static void initAll() {
        fileParser = new FileParser();
    }

    @Test
    void failToParseIfInvalidPath() {
        APIException thrown =
                assertThrows(APIException.class,
                        () -> fileParser.parseFile(null));
        APIException thrown2 =
                assertThrows(APIException.class,
                        () -> fileParser.parseFile(""));
        APIException thrown3 =
                assertThrows(APIException.class,
                        () -> fileParser.parseFile("?"));
        assertTrue(thrown.getMessage().contains("Invalid file path"));
        assertTrue(thrown2.getMessage().contains("Invalid file path"));
        assertTrue(thrown3.getMessage().contains("Invalid file path"));
    }

    @Test
    void failToParseFileDoesNotExist() {
        APIException thrown =
                assertThrows(APIException.class,
                        () -> fileParser.parseFile("src/test/resources/does-not-exist.txt"));
        assertTrue(thrown.getMessage().contains("File does not exist"));
    }

    @Test
    void successToParseEmptyFile() throws APIException, IOException {
        String inputFilePath = new File("src/test/resources/empty-input.txt").getAbsolutePath();
        List<PackingRequest> packingRequests = fileParser.parseFile(inputFilePath);
        assertEquals(0, packingRequests.size());
    }

    @Test
    void successToParseFile() throws APIException, IOException {
        String inputFilePath = new File("src/test/resources/input.txt").getAbsolutePath();
        List<PackingRequest> packingRequests = fileParser.parseFile(inputFilePath);
        assertEquals(4, packingRequests.size());

        PackingRequest packingRequest3 = packingRequests.get(2);
        assertEquals(75, packingRequest3.getPackageCapacity());
        assertEquals(2, packingRequest3.getThings().get(1).getIndexNumber());
        assertEquals(14.55, packingRequest3.getThings().get(1).getWeight());
        assertEquals(BigDecimal.valueOf(74), packingRequest3.getThings().get(1).getCost());
    }
}
