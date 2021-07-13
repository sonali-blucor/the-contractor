package com.blucor.thecontractor.models;

public class WorkOrderModel {

    public WorkOrderModel(String work_description, String unit, String quantity, String amount, String rate) {
        this.work_description = work_description;
        this.unit = unit;
        this.quantity = quantity;
        this.amount = amount;
        this.rate = rate;
    }

    public String getWork_description() {
        return work_description;
    }

    public void setWork_description(String work_description) {
        this.work_description = work_description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    private String amount;
    private  String rate;
    private String work_description;
    private  String unit;
    private  String quantity;
}
