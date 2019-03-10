package com.mobiquityinc.packer.internal;

import com.mobiquityinc.domain.Package;
import com.mobiquityinc.domain.PackingRequest;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.io.FileParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SlowPackerTest {

    private static SlowPacker packer;
    private static List<PackingRequest> packingRequests;

    @BeforeAll
    static void setUpAll() throws APIException, IOException {
        packer = new SlowPacker();
        String inputFilePath = new File("src/test/resources/input.txt").getAbsolutePath();
        packingRequests = new FileParser().parseFile(inputFilePath);
    }

    @Test
    void failToPackIfEmptyRequestList() {
        Optional<Package> packedThings = packer.pack(new PackingRequest(0, new ArrayList<>()));
        assertFalse(packedThings.isPresent());
    }

    @Test
    void failToPackIfOverweight() {
        PackingRequest overweightRequest = packingRequests.get(1);
        Optional<Package> packedThings = packer.pack(overweightRequest);
        assertFalse(packedThings.isPresent());
    }

    @Test
    void successToPackIfViable() {
        PackingRequest viableRequest = packingRequests.get(0);
        Optional<Package> packedThings = packer.pack(viableRequest);
        assertTrue(packedThings.isPresent());
        assertAll("Should return the best pack",
                () -> assertEquals(1, packedThings.get().getThings().size()),
                () -> assertEquals(4, packedThings.get().getThings().get(0).getIndexNumber())
        );

        PackingRequest viableRequest2 = packingRequests.get(2);
        Optional<Package> packedThings2 = packer.pack(viableRequest2);
        assertTrue(packedThings2.isPresent());
        assertAll("Should return the best pack",
                () -> assertEquals(2, packedThings2.get().getThings().size()),
                () -> assertEquals(2, packedThings2.get().getThings().get(0).getIndexNumber()),
                () -> assertEquals(7, packedThings2.get().getThings().get(1).getIndexNumber())
        );
    }

    @Test
    void successToPackBigFile() throws APIException, IOException {
        String inputFilePath = new File("src/test/resources/input-15-things.txt").getAbsolutePath();
        PackingRequest bigRequest = new FileParser()
                .parseFile(inputFilePath)
                .get(0);
        Optional<Package> packedThings = packer.pack(bigRequest);
        assertTrue(packedThings.isPresent());
    }

    @Test
    void successToPackIfViableWhenManyPackagesWithSamePrice() {
        PackingRequest viableRequest = packingRequests.get(3);
        Optional<Package> packedThings = packer.pack(viableRequest);
        assertTrue(packedThings.isPresent());
        assertAll("Should return the best pack",
                () -> assertEquals(2, packedThings.get().getThings().size()),
                () -> assertEquals(8, packedThings.get().getThings().get(0).getIndexNumber()),
                () -> assertEquals(9, packedThings.get().getThings().get(1).getIndexNumber())
        );
    }
}
