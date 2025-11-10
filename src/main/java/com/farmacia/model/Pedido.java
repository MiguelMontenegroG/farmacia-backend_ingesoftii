package com.farmacia.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "pedidos")
public class Pedido {
    @Id
    private String id;

    @Indexed
    private String userId;

    private List<OrderItem> items;
    private BigDecimal subtotal;
    private BigDecimal tax; // impuestos
    private BigDecimal shipping; // envío
    private BigDecimal total;
    private String status; // "pending", "processing", "shipped", "delivered", "cancelled"
    private String paymentMethod;
    private com.farmacia.dto.DireccionDTO shippingAddress;
    private com.farmacia.dto.DireccionDTO billingAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime estimatedDelivery;
    private String trackingNumber;

    // Constructores
    public Pedido() {}

    public Pedido(String userId) {
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = "pending";
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getTax() { return tax; }
    public void setTax(BigDecimal tax) { this.tax = tax; }

    public BigDecimal getShipping() { return shipping; }
    public void setShipping(BigDecimal shipping) { this.shipping = shipping; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    // Método adicional para compatibilidad con AdminController
    public String getEstado() { return status; }
    public void setEstado(String estado) { this.status = estado; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public com.farmacia.dto.DireccionDTO getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(com.farmacia.dto.DireccionDTO shippingAddress) { this.shippingAddress = shippingAddress; }

    public com.farmacia.dto.DireccionDTO getBillingAddress() { return billingAddress; }
    public void setBillingAddress(com.farmacia.dto.DireccionDTO billingAddress) { this.billingAddress = billingAddress; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    // Método adicional para compatibilidad con AdminController
    public LocalDateTime getFechaCreacion() { return createdAt; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.createdAt = fechaCreacion; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getEstimatedDelivery() { return estimatedDelivery; }
    public void setEstimatedDelivery(LocalDateTime estimatedDelivery) { this.estimatedDelivery = estimatedDelivery; }

    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
}