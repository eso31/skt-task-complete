package com.skytask.common;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Size(min = 1, max = 30, message = "Product name should be between 1 and 30 characters")
    private String name;
    @NotNull
    @Size(min = 1, max = 30, message = "Product description should be between 1 and 30 characters")
    private String description;
    @NotNull
    @Min(value = 0, message = "Product price can not be negative")
    private double price;
    @NotNull
    @Min(value = 0, message = "Product stock can not be negative")
    private int stock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id:'" + id + "'," +
                "name:'" + name + "'," +
                "description:'" + description + "'," +
                "price:'" + price + "'," +
                "stock:'" + stock + "'" +
                "}";
    }
}
