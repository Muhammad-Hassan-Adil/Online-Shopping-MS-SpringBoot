package com.Hassan.app.SpringBoot;
import com.Hassan.app.SpringBoot.domain.ItemLocation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemLocationTest {

    @Test
    public void testItemLocationConstructor() {
        int expectedId = 1;
        String expectedLocationName = "Electronics";
        ItemLocation itemLocation = new ItemLocation(expectedId, expectedLocationName);
        assertEquals(expectedId, itemLocation.getID());
        assertEquals(expectedLocationName, itemLocation.getLocationName());
    }

    @Test
    public void testSetters() {
        ItemLocation itemLocation = new ItemLocation();
        int expectedId = 1;
        String expectedLocationName = "Electronics";
        itemLocation.setID(expectedId);
        itemLocation.setLocationName(expectedLocationName);
        assertEquals(expectedId, itemLocation.getID());
        assertEquals(expectedLocationName, itemLocation.getLocationName());
    }
}
