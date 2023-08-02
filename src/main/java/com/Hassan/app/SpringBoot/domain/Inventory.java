package com.Hassan.app.SpringBoot.domain;
import jakarta.persistence.*;

@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    @Column
    private int itemQuantity;
    @Column
    private String itemName;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private ItemCategory itemCategory;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private ItemLocation itemLocation;

    public Inventory() {
    }

    public Inventory(int ID, String itemName, int itemQuantity, ItemCategory itemCategory, ItemLocation itemLocation) {
        this.ID = ID;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemCategory = itemCategory;
        this.itemLocation = itemLocation;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
    }

    public ItemLocation getItemLocation() {
        return itemLocation;
    }

    public void setItemLocation(ItemLocation itemLocation) {
        this.itemLocation = itemLocation;
    }
}
