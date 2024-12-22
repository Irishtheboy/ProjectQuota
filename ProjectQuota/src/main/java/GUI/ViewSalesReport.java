package GUI;

import java.time.LocalDate;
import java.util.List;

public class ViewSalesReport {

    // Properties
    private int reportId;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Sale> sales;
    private double totalRevenue;

    // Constructors
    public ViewSalesReport(int reportId, LocalDate startDate, LocalDate endDate, List<Sale> sales) {
        this.reportId = reportId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sales = sales;
        calculateTotalRevenue();
    }

    // Getters and Setters
    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
        calculateTotalRevenue();
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    // Business Logic
    private void calculateTotalRevenue() {
        totalRevenue = sales.stream()
                .mapToDouble(Sale::getAmount)
                .sum();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sales Report ID: ").append(reportId).append("\n")
                .append("Reporting Period: ").append(startDate).append(" to ").append(endDate).append("\n")
                .append("Total Revenue: $").append(totalRevenue).append("\n")
                .append("Sales Details:\n");

        for (Sale sale : sales) {
            sb.append(sale).append("\n");
        }

        return sb.toString();
    }
}

// Sample Sale Class
class Sale {
    private int saleId;
    private String clientName;
    private LocalDate saleDate;
    private double amount;

    public Sale(int saleId, String clientName, LocalDate saleDate, double amount) {
        this.saleId = saleId;
        this.clientName = clientName;
        this.saleDate = saleDate;
        this.amount = amount;
    }

    public int getSaleId() {
        return saleId;
    }

    public String getClientName() {
        return clientName;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Sale ID: " + saleId + ", Client: " + clientName + ", Date: " + saleDate + ", Amount: $" + amount;
    }
}
