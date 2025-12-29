package model;

import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.List;

public class Order {
    private int orderId;
    private int customerId;
    private String customerName;
    private LocalDateTime orderDate;  
    private double totalAmount;
    private String status;
    private LocalDateTime createdAt;
    private List<OrderItem> items;
    
    
    public Order() {
        this.orderDate = LocalDateTime.now();
        this.status = "PENDING";
    }
    public Order(int customerId, double totalAmount, String status) {
        this.customerId = customerId;
        this.orderDate = LocalDateTime.now();
        this.totalAmount = totalAmount;
        this.status = status;
    } 
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    
    
    public Timestamp getOrderDateAsTimestamp() {
        return orderDate != null ? Timestamp.valueOf(orderDate) : null;
    }
    
    public void setOrderDateFromTimestamp(Timestamp timestamp) {
        this.orderDate = timestamp != null ? timestamp.toLocalDateTime() : null;
    }
    
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
   
    public void setCreatedAtFromTimestamp(Timestamp timestamp) {
        this.createdAt = timestamp != null ? timestamp.toLocalDateTime() : null;
    } 
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
    
    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", customerId=" + customerId + 
               ", totalAmount=" + totalAmount + ", status=" + status + "]";
    }
}
