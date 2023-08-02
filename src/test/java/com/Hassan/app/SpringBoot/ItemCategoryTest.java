package com.Hassan.app.SpringBoot;
import com.Hassan.app.SpringBoot.domain.ItemCategory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemCategoryTest {

    @Test
    public void testItemCategoryConstructor() {
        int expectedId = 1;
        String expectedCategoryName = "Electronics";

        ItemCategory itemCategory = new ItemCategory(expectedId, expectedCategoryName);

        assertEquals(expectedId, itemCategory.getID());
        assertEquals(expectedCategoryName, itemCategory.getCategoryName());
    }

    @Test
    public void testSetters() {
        ItemCategory itemCategory = new ItemCategory();

        int expectedId = 1;
        String expectedCategoryName = "Electronics";

        itemCategory.setID(expectedId);
        itemCategory.setCategoryName(expectedCategoryName);

        assertEquals(expectedId, itemCategory.getID());
        assertEquals(expectedCategoryName, itemCategory.getCategoryName());
    }
}
