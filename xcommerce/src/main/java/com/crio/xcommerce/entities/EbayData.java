package com.crio.xcommerce.entities;

import java.time.LocalDate;
import com.crio.xcommerce.enums.EbayTransactionStatus;

public class EbayData {

    private final String transactionId;
    private final String username;
    private final EbayTransactionStatus transactionStatus;
    private final LocalDate transactionDate;    
    private final double amount;

    private EbayData(Builder builder){
        this.transactionId=builder.transactionId;
        this.username=builder.username;
        this.transactionDate=builder.transactionDate;
        this.transactionStatus=builder.transactionStatus;
        this.amount=builder.amount;
    }

    public static class Builder{
        private String transactionId;
        private String username;
        private LocalDate transactionDate;
        private EbayTransactionStatus transactionStatus;
        private double amount;

        public Builder(LocalDate transactionDate,double amount){
            this.transactionDate=transactionDate;
            this.amount=amount;
        }

        public Builder setTransactionStatus(EbayTransactionStatus transactionStatus){
            this.transactionStatus=transactionStatus;
            return this;
        }

        public Builder setTransactionId(String transactionId){
            this.transactionId=transactionId;
            return this;
        }

        public Builder setUserName(String username){
            this.username=username;
            return this;
        }

        public EbayData build(){
            return new EbayData(this);
        }          
    }
    public String getTransactionId() {
        return transactionId;
    }

    public String getUsername() {
        return username;
    }

    public EbayTransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "EbayData [amount=" + amount + ", transactionDate=" + transactionDate
                + ", transactionId=" + transactionId + ", transactionStatus=" + transactionStatus
                + ", username=" + username + "]";
    }
}
