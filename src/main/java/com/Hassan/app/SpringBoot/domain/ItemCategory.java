package com.Hassan.app.SpringBoot.domain;

import jakarta.persistence.*;

@Entity
public class ItemCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    @Column
    private String categoryName;

    public ItemCategory() {
    }

    public ItemCategory(int ID, String categoryName) {
        this.ID = ID;
        this.categoryName = categoryName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
