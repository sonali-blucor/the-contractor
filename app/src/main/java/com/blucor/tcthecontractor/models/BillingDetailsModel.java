package com.blucor.tcthecontractor.models;

public class BillingDetailsModel {

    public BillingDetailsModel(String percentage, String paid, String amount, String balance, String remarks) {
        this.percentage = percentage;
        this.paid = paid;
        this.amount = amount;
        this.balance = balance;
        this.remarks = remarks;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    private String percentage;
    private  String paid;
    private String amount;
    private  String balance;
    private  String remarks;
}
