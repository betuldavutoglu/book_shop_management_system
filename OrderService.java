package service;

import dao.OrderDAO;
import model.Order;
import model.OrderItem;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private OrderDAO orderDAO;
    
    public OrderService() {
        this.orderDAO = new OrderDAO();
    }
    
    public boolean createOrder(Order order) {
        System.out.println("=== ORDER SERVICE: createOrder çağrıldı ===");
        
        if (order == null) {
            System.out.println("ERROR: Order null!");
            return false;
        }
        
        System.out.println("Müşteri ID: " + order.getCustomerId());
        System.out.println("Toplam Tutar: " + order.getTotalAmount());
        System.out.println("Durum: " + order.getStatus());
        
        if (order.getItems() == null || order.getItems().isEmpty()) {
            System.out.println("ERROR: Order items null veya boş!");
            return false;
        }
        
        System.out.println("Sipariş öğe sayısı: " + order.getItems().size());
        
        // Debug: Her öğeyi göster
        double calculatedTotal = 0;
        for (OrderItem item : order.getItems()) {
            double itemTotal = item.getQuantity() * item.getUnitPrice();
            calculatedTotal += itemTotal;
            System.out.println("Öğe - Kitap ID: " + item.getBookId() + 
                             ", Miktar: " + item.getQuantity() + 
                             ", Fiyat: " + item.getUnitPrice() +
                             ", Toplam: " + itemTotal);
        }
        
        // Total amount kontrolü
        System.out.println("Hesaplanan toplam: " + calculatedTotal);
        System.out.println("Order'daki toplam: " + order.getTotalAmount());
        
        // Eğer totalAmount 0 ise hesapla
        if (order.getTotalAmount() == 0) {
            order.setTotalAmount(calculatedTotal);
            System.out.println("TotalAmount güncellendi: " + order.getTotalAmount());
        }
        
        try {
            boolean result = orderDAO.create(order);
            System.out.println("Sipariş kayıt sonucu: " + result);
            
            if (result) {
                System.out.println("✓ Sipariş başarıyla oluşturuldu, ID: " + order.getOrderId());
                // Hemen yükleyelim
                loadOrdersDebug(); // Debug için
            }
            
            return result;
            
        } catch (Exception e) {
            System.err.println("✗ Sipariş oluşturma hatası: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Debug için: Siparişleri kontrol et
    private void loadOrdersDebug() {
        try {
            List<Order> orders = getAllOrders();
            System.out.println("=== DEBUG: Güncel sipariş sayısı: " + orders.size() + " ===");
            for (Order o : orders) {
                System.out.println("Sipariş ID: " + o.getOrderId() + 
                                 ", Müşteri: " + o.getCustomerName() + 
                                 ", Tutar: " + o.getTotalAmount());
            }
        } catch (Exception e) {
            System.err.println("Debug yükleme hatası: " + e.getMessage());
        }
    }
    
    public Order getOrder(int id) {
        try {
            System.out.println("Sipariş getiriliyor ID: " + id);
            Order order = orderDAO.read(id);
            if (order != null) {
                System.out.println("✓ Sipariş bulundu: ID=" + id);
            } else {
                System.out.println("⚠️ Sipariş bulunamadı: ID=" + id);
            }
            return order;
        } catch (Exception e) {
            System.err.println("Sipariş getirme hatası: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Order> getAllOrders() {
        try {
            System.out.println("=== Tüm siparişler getiriliyor ===");
            List<Order> orders = orderDAO.readAll();
            
            if (orders == null) {
                System.out.println("⚠️ Orders listesi null!");
                return new ArrayList<>();
            }
            
            System.out.println("✓ " + orders.size() + " sipariş getirildi");
            
            // Debug: Her siparişi göster
            for (Order order : orders) {
                System.out.println("  - ID: " + order.getOrderId() + 
                                 ", Müşteri: " + (order.getCustomerName() != null ? order.getCustomerName() : "Müşteri " + order.getCustomerId()) +
                                 ", Tutar: " + order.getTotalAmount() +
                                 ", Durum: " + order.getStatus());
            }
            
            return orders;
            
        } catch (Exception e) {
            System.err.println("✗ Siparişleri getirme hatası: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean updateOrderStatus(int orderId, String status) {
        try {
            System.out.println("Sipariş durumu güncelleniyor: " + orderId + " -> " + status);
            return orderDAO.updateOrderStatus(orderId, status);
        } catch (Exception e) {
            System.err.println("Durum güncelleme hatası: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteOrder(int id) {
        System.out.println("=== OrderService.deleteOrder() çağrıldı ===");
        System.out.println("Silinecek Sipariş ID: " + id);
        
        try {
            return orderDAO.delete(id);
        } catch (Exception e) {
            System.err.println("Sipariş silme hatası: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
