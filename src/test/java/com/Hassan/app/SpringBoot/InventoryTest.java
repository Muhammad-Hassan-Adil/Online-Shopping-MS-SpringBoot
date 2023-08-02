package com.Hassan.app.SpringBoot;
import com.Hassan.app.SpringBoot.domain.Inventory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class InventoryTest {
    @Test
    public void TestSetter() {
        Inventory inventory = new Inventory();
        inventory.setID(1);
        assertEquals(1, inventory.getID());
    }
}
