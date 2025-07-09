package dev.training.vendingmachine.dto;

public class PurchaseRequest {
    private int money;

    public PurchaseRequest() {
    }

    public PurchaseRequest(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
