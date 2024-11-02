package com.ruayshop.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Motorcycle")
public class Motorcycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mt_id")
    private Integer id;

    @Column(name = "mt_model", length = 100)
    private String model;

    @Column(name = "mt_desc", length = 1000)
    private String description;

    @Column(name = "mt_stock")
    private Integer stock;

    @Column(name = "mt_price")
    private Double price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void decreaseStock(int amount) {
        if (this.stock >= amount) {
            this.stock -= amount;
        } else {
            throw new IllegalArgumentException("Not enough stock available.");
        }
    }
}
