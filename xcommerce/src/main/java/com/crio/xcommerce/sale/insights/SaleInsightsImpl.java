package com.crio.xcommerce.sale.insights;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.crio.xcommerce.contract.exceptions.AnalyticsException;
import com.crio.xcommerce.contract.insights.SaleAggregate;
import com.crio.xcommerce.contract.insights.SaleAggregateByMonth;
import com.crio.xcommerce.contract.insights.SaleInsights;
import com.crio.xcommerce.contract.resolver.DataProvider;
import com.crio.xcommerce.entities.AmazonData;
import com.crio.xcommerce.entities.EbayData;
import com.crio.xcommerce.entities.FlipkartData;
import com.crio.xcommerce.enums.AmazonTransactionStatus;
import com.crio.xcommerce.enums.EbayTransactionStatus;
import com.crio.xcommerce.enums.FlipkartTransactionStatus;

public class SaleInsightsImpl implements SaleInsights {

    @Override
    public SaleAggregate getSaleInsights(DataProvider dataProvider, int year)
            throws IOException, AnalyticsException {
        String venderName = dataProvider.getProvider();
        File file = dataProvider.resolveFile();
        switch (venderName) {
            case "flipkart":
                return getSaleInsightsFlipkart(file, year);
            case "amazon":
                return getSaleInsightsAmazon(file, year);
            case "ebay":
                return getSaleInsightsEbay(file, year);
        }
        return null;
    }

    private List<String> readContentFromFile(File file) throws IOException {
        List<String> tokens = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = bufferedReader.readLine();
        while (line != null) {
            tokens.add(line);
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        return tokens;
    }

    private List<AmazonData> mapListStringToListOfAmazonData(List<String> tokens)
            throws AnalyticsException {
        List<AmazonData> listOfAmazonData = new LinkedList<>();
        for (int i = 1; i < tokens.size(); i++) {
            String[] tokenSplit = tokens.get(i).split(",");
            if (tokenSplit.length == 5)
                throw new AnalyticsException("Amount is Empty");
            String transactionId = tokenSplit[0];
            String externalTransactionId = tokenSplit[1];
            String userId = tokenSplit[2];
            String transactionStatusStr = tokenSplit[3];
            String transactionDateStr = tokenSplit[4];
            String amountStr = tokenSplit[5];
            if (amountStr.isEmpty())
                throw new AnalyticsException("Amount is Empty");
            if (transactionDateStr.isEmpty())
                throw new AnalyticsException("Transaction Date is Empty");
            double amount = Double.parseDouble(amountStr);
            AmazonTransactionStatus transactionStatus;
            LocalDate transactionDate = LocalDate.parse(transactionDateStr);
            if (transactionStatusStr.isEmpty()) {
                transactionStatus = AmazonTransactionStatus.EMPTY;
            } else {
                transactionStatus =
                        AmazonTransactionStatus.valueOf(transactionStatusStr.toUpperCase());
            }
            AmazonData amazonData = new AmazonData.Builder(transactionDate, amount)
                    .setExternalTransactionId(externalTransactionId).setTransactionId(transactionId)
                    .setTransactionStatus(transactionStatus).setUserId(userId).build();
            listOfAmazonData.add(amazonData);
        }
        return listOfAmazonData;
    }

    private List<EbayData> mapListOfStringToListOfEbayData(List<String> tokens)
            throws AnalyticsException {
        List<EbayData> listEbayData = new LinkedList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");;
        for (int i = 1; i < tokens.size(); i++) {
            String[] tokenSplit = tokens.get(i).split(",");
            if (tokenSplit.length == 4)
                throw new AnalyticsException("Amount is Empty");
            String transactionId = tokenSplit[0];
            String username = tokenSplit[1];
            String transactionStatusStr = tokenSplit[2];
            String transactionDateStr = tokenSplit[3];
            String amountStr = tokenSplit[4];
            if (amountStr.isEmpty())
                throw new AnalyticsException("Amount is Empty");
            if (transactionDateStr.isEmpty())
                throw new AnalyticsException("Transaction Date is Empty");
            double amount = Double.parseDouble(amountStr);
            EbayTransactionStatus transactionStatus;
            LocalDate transactionDate = LocalDate.parse(transactionDateStr, dateFormatter);
            if (transactionStatusStr.isEmpty()) {
                transactionStatus = EbayTransactionStatus.EMPTY;
            } else {
                transactionStatus =
                        EbayTransactionStatus.valueOf(transactionStatusStr.toUpperCase());
            }
            EbayData ebayData =
                    new EbayData.Builder(transactionDate, amount).setTransactionId(transactionId)
                            .setTransactionStatus(transactionStatus).setUserName(username).build();
            listEbayData.add(ebayData);
        }
        return listEbayData;
    }

    private List<FlipkartData> mapListStringToListOfFlipkartData(List<String> tokens)
            throws AnalyticsException {
        List<FlipkartData> listOfFlipkartData = new LinkedList<>();
        for (int i = 1; i < tokens.size(); i++) {
            String[] tokenSplit = tokens.get(i).split(",");
            if (tokenSplit.length == 5)
                throw new AnalyticsException("Amount is Empty");
            String transactionId = tokenSplit[0];
            String externalTransactionId = tokenSplit[1];
            String userId = tokenSplit[2];
            String transactionDateStr = tokenSplit[3];
            String transactionStatusStr = tokenSplit[4];
            String amountStr = tokenSplit[5];
            if (amountStr.isEmpty())
                throw new AnalyticsException("Amount is Empty");
            if (transactionDateStr.isEmpty())
                throw new AnalyticsException("Transaction Date is Empty");
            double amount = Double.parseDouble(amountStr);
            FlipkartTransactionStatus transactionStatus;
            LocalDate transactionDate = LocalDate.parse(transactionDateStr);
            if (transactionStatusStr.isEmpty()) {
                transactionStatus = FlipkartTransactionStatus.EMPTY;
            } else {
                transactionStatus =
                        FlipkartTransactionStatus.valueOf(transactionStatusStr.toUpperCase());
            }
            FlipkartData flipkartData = new FlipkartData.Builder(transactionDate, amount)
                    .setTransactionId(transactionId).setExternalTransactionId(externalTransactionId)
                    .setUserId(userId).setTransactionStatus(transactionStatus).build();
            listOfFlipkartData.add(flipkartData);
        }
        return listOfFlipkartData;
    }

    private SaleAggregate getSaleInsightsFlipkart(File file, int year)
            throws IOException, AnalyticsException {
        List<FlipkartData> lFlipkartData =
                mapListStringToListOfFlipkartData(readContentFromFile(file));
        Map<Object, Double> aggregateByMonthsMap = lFlipkartData.stream()
                .filter(fd -> fd.getTransactionDate().getYear() == year
                        && (fd.getTransactionStatus().equals(FlipkartTransactionStatus.COMPLETE)
                                || fd.getTransactionStatus().equals(FlipkartTransactionStatus.PAID)
                                || fd.getTransactionStatus()
                                        .equals(FlipkartTransactionStatus.SHIPPED)))
                .collect(Collectors.groupingBy(fd -> fd.getTransactionDate().getMonthValue(),
                        Collectors.summingDouble(FlipkartData::getAmount)));
        List<SaleAggregateByMonth> aggregateByMonths = new ArrayList<>();
        double totalSales = aggregateByMonthsMap.values().stream().reduce(0.0, (a, b) -> a + b);
        for (Integer i = 0; i < 12; i++) {
            aggregateByMonths.add(
                    new SaleAggregateByMonth(i + 1, aggregateByMonthsMap.getOrDefault(i + 1, 0.0)));
        }
        return new SaleAggregate(totalSales, aggregateByMonths);
    }

    private SaleAggregate getSaleInsightsAmazon(File file, int year)
            throws IOException, AnalyticsException {
        List<AmazonData> lAmazonData = mapListStringToListOfAmazonData(readContentFromFile(file));
        Map<Object, Double> aggregateByMonthsMap = lAmazonData.stream()
                .filter(amzn -> amzn.getTransactionDate().getYear() == year
                        && (amzn.getTransactionStatus().equals(AmazonTransactionStatus.SHIPPED)))
                .collect(Collectors.groupingBy(amzn -> amzn.getTransactionDate().getMonthValue(),
                        Collectors.summingDouble(AmazonData::getAmount)));
        List<SaleAggregateByMonth> aggregateByMonths = new ArrayList<>();
        double totalSales = aggregateByMonthsMap.values().stream().reduce(0.0, (a, b) -> a + b);
        for (Integer i = 0; i < 12; i++) {
            aggregateByMonths.add(
                    new SaleAggregateByMonth(i + 1, aggregateByMonthsMap.getOrDefault(i + 1, 0.0)));
        }
        return new SaleAggregate(totalSales, aggregateByMonths);
    }

    private SaleAggregate getSaleInsightsEbay(File file, int year)
            throws IOException, AnalyticsException {
        List<EbayData> lEbayData = mapListOfStringToListOfEbayData(readContentFromFile(file));
        Map<Object, Double> aggregateByMonthsMap = lEbayData.stream()
                .filter(ebay -> ebay.getTransactionDate().getYear() == year && (ebay
                        .getTransactionStatus().equals(EbayTransactionStatus.COMPLETE)
                        || ebay.getTransactionStatus().equals(EbayTransactionStatus.DELIVERED)))
                .collect(Collectors.groupingBy(ebay -> ebay.getTransactionDate().getMonthValue(),
                        Collectors.summingDouble(EbayData::getAmount)));
        List<SaleAggregateByMonth> aggregateByMonths = new ArrayList<>();
        double totalSales = aggregateByMonthsMap.values().stream().reduce(0.0, (a, b) -> a + b);
        for (Integer i = 0; i < 12; i++) {
            aggregateByMonths.add(
                    new SaleAggregateByMonth(i + 1, aggregateByMonthsMap.getOrDefault(i + 1, 0.0)));
        }
        return new SaleAggregate(totalSales, aggregateByMonths);
    }

    // //Only For Testing Purpose
    // public static void main(String[] args) {
    //     SaleInsightsImpl saleInsightsImpl = new SaleInsightsImpl();
    //     try {
    //         saleInsightsImpl.getSaleInsightsAmazon(
    //                 new File("xcommerce/src/test/resources/assessments/invalid_data.csv"), 2020);
    //     } catch (IOException | AnalyticsException e) {
    //         // TODO Auto-generated catch block
    //         e.printStackTrace();
    //     }
    // }

}
