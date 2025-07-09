package dev.training.vendingmachine.controller;

import dev.training.vendingmachine.domain.VendingMachine;
import dev.training.vendingmachine.dto.PurchaseRequest;
import dev.training.vendingmachine.dto.PurchaseResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class VendingMachineController {

    private final VendingMachine vendingMachine;

    public VendingMachineController() {
        this.vendingMachine = new VendingMachine();
    }

    @PostMapping("/purchase")
    public PurchaseResponse purchase(@RequestBody PurchaseRequest request) {
        String product = vendingMachine.purchase(request.getMoney());
        return new PurchaseResponse(product, 1);
    }
}
