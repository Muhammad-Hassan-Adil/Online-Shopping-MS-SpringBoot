package com.Hassan.app.SpringBoot;

import com.Hassan.app.SpringBoot.domain.*;
import com.Hassan.app.SpringBoot.resources.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ApplicationTest {

    private MockMvc mockMvc;

    @Mock
    private UserRepoResource userRepoResource;

    @InjectMocks
    private ApplicationResource applicationResource;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(applicationResource).build();
    }

    @Test
    public void testGetInventory() throws Exception {
        Inventory inventory1 = new Inventory(1, "Widget", 50, new ItemCategory(1, "Electronics"), new ItemLocation(101, "Warehouse A"));
        Inventory inventory2 = new Inventory(2, "Gadget", 30, new ItemCategory(2, "Accessories"), new ItemLocation(102, "Warehouse B"));
        List<Inventory> inventoryList = Arrays.asList(inventory1, inventory2);

        when(userRepoResource.findAll()).thenReturn(inventoryList);

        mockMvc.perform(get("/inventory"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].itemName").value("Widget"))
                .andExpect(jsonPath("$[0].itemQuantity").value(50))
                .andExpect(jsonPath("$[1].itemName").value("Gadget"))
                .andExpect(jsonPath("$[1].itemQuantity").value(30));

        verify(userRepoResource, times(1)).findAll();
        verifyNoMoreInteractions(userRepoResource);
    }

    @Test
    public void testGetInventoryError() {
		doThrow(new RuntimeException("Database connection error")).when(userRepoResource).findAll();
		List<Inventory> result = applicationResource.getInventory();
		assertNull(result);
		verify(userRepoResource, times(1)).findAll();
		verifyNoMoreInteractions(userRepoResource);
	}

    @Test
    public void testAddInventory() throws Exception {
        Inventory inventory = new Inventory(1, "Widget", 50, new ItemCategory(1, "Electronics"), new ItemLocation(101, "Warehouse A"));
        when(userRepoResource.save(any(Inventory.class))).thenReturn(inventory);
        mockMvc.perform(post("/add")
                        .content("{ \"itemName\": \"Widget\", \"itemQuantity\": 50, \"itemCategory\": { \"ID\": 1, \"categoryName\": \"Electronics\" }, \"itemLocation\": { \"ID\": 101, \"locationName\": \"Warehouse A\" } }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Inventory added with id: 0"));
        verify(userRepoResource, times(1)).save(any(Inventory.class));
        verifyNoMoreInteractions(userRepoResource);
    }

	@Test
	public void testAddInventoryError() {
        Inventory inventoryToAdd = new Inventory(1, "Widget", 50, new ItemCategory(1, "Electronics"), new ItemLocation(101, "Warehouse A"));
        doThrow(new RuntimeException("Error adding inventory")).when(userRepoResource).save(any());
        String result = applicationResource.addInventory(inventoryToAdd);
        assertEquals("Error Adding", result);
        verify(userRepoResource, times(1)).save(any());
        verifyNoMoreInteractions(userRepoResource);
	}

    @Test
    public void testUpdateInventory() throws Exception {
        Inventory inventory = new Inventory(1, "Widget", 50, new ItemCategory(1, "Electronics"), new ItemLocation(101, "Warehouse A"));
        when(userRepoResource.findById(1)).thenReturn(Optional.of(inventory));
        when(userRepoResource.save(any(Inventory.class))).thenReturn(inventory);
        mockMvc.perform(put("/update/1")
                        .content("{ \"itemName\": \"Widget\", \"itemQuantity\": 50, \"itemCategory\": { \"ID\": 1, \"categoryName\": \"Electronics\" }, \"itemLocation\": { \"ID\": 101, \"locationName\": \"Warehouse A\" } }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Inventory updated with id: 1 and name: Widget and quantity: 50"));
        verify(userRepoResource, times(1)).findById(1);
        verify(userRepoResource, times(1)).save(any(Inventory.class));
        verifyNoMoreInteractions(userRepoResource);
    }

    @Test
    public void testUpdateInventoryError(){
        Inventory existingInventory = new Inventory(1, "Widget", 50, new ItemCategory(1, "Electronics"), new ItemLocation(101, "Warehouse A"));
        Inventory updatedInventory = new Inventory(1, "New Widget", 100, new ItemCategory(2, "Gadgets"), new ItemLocation(102, "Warehouse B"));
        when(userRepoResource.findById(1)).thenReturn(Optional.of(existingInventory));
        doThrow(new RuntimeException("Error updating inventory")).when(userRepoResource).save(any());
        String result = applicationResource.updateInventory(1, updatedInventory);
        assertEquals("Error Updating", result);
        verify(userRepoResource, times(1)).findById(anyInt());
        verify(userRepoResource, times(1)).save(any());
        verifyNoMoreInteractions(userRepoResource);
    }

    @Test
    public void testDeleteInventory() throws Exception {
        Inventory inventory = new Inventory(1, "Widget", 50, new ItemCategory(1, "Electronics"), new ItemLocation(101, "Warehouse A"));
        when(userRepoResource.findById(1)).thenReturn(Optional.of(inventory));
        mockMvc.perform(delete("/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Inventory deleted with id: 1"));
        verify(userRepoResource, times(1)).deleteById(1);
        verifyNoMoreInteractions(userRepoResource);
    }

    @Test
    public void testDeleteInventoryError(){
        doThrow(new RuntimeException("Error deleting inventory")).when(userRepoResource).deleteById(anyInt());
        String result = applicationResource.deleteInventory(1);
        assertEquals("Error Deleting", result);
        verify(userRepoResource, times(1)).deleteById(anyInt());
        verifyNoMoreInteractions(userRepoResource);
    }
}
