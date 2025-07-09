package dev.training.vendingmachine.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VendingMachineTest {

    private VendingMachine vendingMachine;

    @BeforeEach
    void setUp() {
        vendingMachine = new VendingMachine();
    }

    @Test
    void _100円を入れるとコーラが1本出てくる() {
        // When
        String result = vendingMachine.purchase(100);
        
        // Then
        assertEquals("コーラ", result);
    }
}
