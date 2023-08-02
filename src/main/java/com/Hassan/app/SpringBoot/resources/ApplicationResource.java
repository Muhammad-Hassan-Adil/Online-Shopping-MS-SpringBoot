package com.Hassan.app.SpringBoot.resources;

import com.Hassan.app.SpringBoot.domain.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.apache.logging.log4j.*;

@RestController
public class ApplicationResource {
    private static final Logger logger = LogManager.getLogger(ApplicationResource.class.getName());
    @Autowired
    private UserRepoResource userRepoResource;

    @GetMapping("/inventory")
    public List<Inventory> getInventory() {
        ThreadContext.put("httpMethod", "GET");
        try {
            List<Inventory> inv = userRepoResource.findAll();
            logger.info("All Inventory Retrieved");
            return inv;
        } catch (Exception e) {
            return null;
        }
        finally {
            ThreadContext.remove("httpMethod");
        }
    }

    @PostMapping("/add")
    public String addInventory(@RequestBody Inventory inventory) {
        ThreadContext.put("httpMethod", "POST");
        try{
            userRepoResource.save(inventory);
            logger.info("Added to Inventory");
        } catch (Exception e) {
            return "Error Adding";
        }
        finally {
            ThreadContext.remove("httpMethod");
        }
        return "Inventory added with id: " + inventory.getID();
    }

    @PutMapping("/update/{id}")
    public String updateInventory(@PathVariable int id, @RequestBody Inventory inventory) {
        ThreadContext.put("httpMethod", "PUT");
        try {
            Inventory existingInventory = userRepoResource.findById(id).get();
            existingInventory.setItemName(inventory.getItemName());
            existingInventory.setItemCategory(inventory.getItemCategory());
            existingInventory.setItemLocation(inventory.getItemLocation());
            existingInventory.setItemQuantity(inventory.getItemQuantity());
            userRepoResource.save(existingInventory);
            logger.info("Inventory Updated");
            return "Inventory updated with id: " + id + " and name: " + inventory.getItemName() + " and quantity: " + inventory.getItemQuantity();
        }
        catch (Exception e) {
            return "Error Updating";
        }
        finally {
            ThreadContext.remove("httpMethod");
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteInventory(@PathVariable int id) {
        ThreadContext.put("httpMethod", "DELETE");
        try {
            userRepoResource.deleteById(id);
            logger.info("Inventory Deleted");
            return "Inventory deleted with id: " + id;
        }
        catch (Exception e) {
            return "Error Deleting";
        }
        finally {
            ThreadContext.remove("httpMethod");
        }
    }
}
