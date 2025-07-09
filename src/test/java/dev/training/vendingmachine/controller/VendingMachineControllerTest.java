package dev.training.vendingmachine.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VendingMachineController.class)
class VendingMachineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void _100円でコーラを購入できる() throws Exception {
        mockMvc.perform(post("/api/purchase")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"money\": 100}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").value("コーラ"))
                .andExpect(jsonPath("$.quantity").value(1));
    }
}
