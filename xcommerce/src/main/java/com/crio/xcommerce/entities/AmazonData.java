package com.crio.xcommerce.entities;

import java.time.LocalDate;
import com.crio.xcommerce.enums.AmazonTransactionStatus;

public class AmazonData {
    private final String transactionId;
    private final String extTxnId;
    private final String userId;
    private final AmazonTransactionStatus status;
    private final LocalDate date;
    private final double amount;

    private AmazonData(Builder builder){
        this.transactionId=builder.transactionId;
        this.extTxnId=builder.extTxnId;
        this.userId=builder.userId;
        this.date=builder.date;
        this.status=builder.status;
        this.amount=builder.amount;
    }

    public static class Builder{
        private String transactionId;
        private String extTxnId;
        private String userId;
        private AmazonTransactionStatus status;
        private LocalDate date;
        private double amount;

        public Builder(LocalDate transactionDate,double amount){
            this.date=transactionDate;
            this.amount=amount;
        }

        public Builder setTransactionStatus(AmazonTransactionStatus transactionStatus){
            this.status=transactionStatus;
            return this;
        }

        public Builder setTransactionId(String transactionId){
            this.transactionId=transactionId;
            return this;
        }

        public Builder setExternalTransactionId(String externalTransactionId){
            this.extTxnId=externalTransactionId;
            return this;
        }

        public Builder setUserId(String userId){
            this.userId=userId;
            return this;
        }

        public AmazonData build(){
            return new AmazonData(this);
        }  
    }
    public String getTransactionId() {
        return transactionId;
    }
    public String getExternalTransactionId() {
        return extTxnId;
    }
    public String getUserId() {
        return userId;
    }
    public LocalDate getTransactionDate() {
        return date;
    }
    public AmazonTransactionStatus getTransactionStatus() {
        return status;
    }
    public double getAmount() {
        return amount;
    }
    @Override
    public String toString() {
        return "AmazonData [amount=" + amount + ", date=" + date + ", extTxnId=" + extTxnId
                + ", status=" + status + ", transactionId=" + transactionId + ", userId=" + userId
                + "]";
    }
}
