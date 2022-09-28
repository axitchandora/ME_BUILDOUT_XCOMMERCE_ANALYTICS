package com.crio.xcommerce.entities;

import java.time.LocalDate;
import com.crio.xcommerce.enums.FlipkartTransactionStatus;

public class FlipkartData {
    private final String transactionId;
    private final String externalTransactionId;
    private final String userId;
    private final LocalDate transactionDate;
    private final FlipkartTransactionStatus transactionStatus;
    private final double amount;

    private FlipkartData(Builder builder){
        this.transactionId=builder.transactionId;
        this.externalTransactionId=builder.externalTransactionId;
        this.userId=builder.userId;
        this.transactionDate=builder.transactionDate;
        this.transactionStatus=builder.transactionStatus;
        this.amount=builder.amount;
    }

    public static class Builder{
        private String transactionId;
        private String externalTransactionId;
        private String userId;
        private LocalDate transactionDate;
        private FlipkartTransactionStatus transactionStatus;
        private double amount;

        public Builder(LocalDate transactionDate,double amount){
            this.transactionDate=transactionDate;
            this.amount=amount;
        }

        public Builder setTransactionStatus(FlipkartTransactionStatus transactionStatus){
            this.transactionStatus=transactionStatus;
            return this;
        }

        public Builder setTransactionId(String transactionId){
            this.transactionId=transactionId;
            return this;
        }

        public Builder setExternalTransactionId(String externalTransactionId){
            this.externalTransactionId=externalTransactionId;
            return this;
        }

        public Builder setUserId(String userId){
            this.userId=userId;
            return this;
        }

        public FlipkartData build(){
            return new FlipkartData(this);
        }  
    }
    public String getTransactionId() {
        return transactionId;
    }
    public String getExternalTransactionId() {
        return externalTransactionId;
    }
    public String getUserId() {
        return userId;
    }
    public LocalDate getTransactionDate() {
        return transactionDate;
    }
    public FlipkartTransactionStatus getTransactionStatus() {
        return transactionStatus;
    }
    public double getAmount() {
        return amount;
    }
    @Override
    public String toString() {
        return "FlipkartData [amount=" + amount + ", externalTransactionId=" + externalTransactionId
                + ", transactionDate=" + transactionDate + ", transactionId=" + transactionId
                + ", transactionStatus=" + transactionStatus + ", userId=" + userId + "]";
    }

}
