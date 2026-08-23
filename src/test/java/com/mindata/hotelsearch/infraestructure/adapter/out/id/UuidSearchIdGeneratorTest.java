package com.mindata.hotelsearch.infraestructure.adapter.out.id;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UuidSearchIdGeneratorTest {
    @Test
    void shouldGenerateUniqueIds() {
        UuidSearchIdGenerator generator = new UuidSearchIdGenerator();
        String first = generator.generate();
        String second = generator.generate();

        assertAll(
                () -> assertNotNull(first),
                () -> assertNotNull(second),
                () -> assertNotEquals(first, second)
        );
    }
}
