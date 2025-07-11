package dev.training.vendingmachine.dto;

public class PurchaseResponse {
    private String product;
    private int quantity;

    public PurchaseResponse() {
    }

    public PurchaseResponse(String product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
