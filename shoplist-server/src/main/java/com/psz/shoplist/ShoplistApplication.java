package com.psz.shoplist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.psz.shoplist.model.document.GroceryItemDocument;
import com.psz.shoplist.repository.GroceryItemRepository;

@SpringBootApplication
@EnableMongoRepositories
public class ShoplistApplication implements CommandLineRunner  {

	@Autowired
    GroceryItemRepository groceryItemRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(ShoplistApplication.class, args);
	}

	//CREATE
    void createGroceryItems() {
        System.out.println("Data creation started...");
        groceryItemRepo.save(new GroceryItemDocument("Whole Wheat Biscuit", "Whole Wheat Biscuit", 5, "snacks"));
        groceryItemRepo.save(new GroceryItemDocument("Kodo Millet", "XYZ Kodo Millet healthy", 2, "millets"));
        groceryItemRepo.save(new GroceryItemDocument("Dried Red Chilli", "Dried Whole Red Chilli", 2, "spices"));
        groceryItemRepo.save(new GroceryItemDocument("Pearl Millet", "Healthy Pearl Millet", 1, "millets"));
        groceryItemRepo.save(new GroceryItemDocument("Cheese Crackers", "Bonny Cheese Crackers Plain", 6, "snacks"));
        System.out.println("Data creation complete...");
    }

	@Override
	public void run(String... args) throws Exception {
        System.out.println("-------------CREATE GROCERY ITEMS-------------------------------\n");
        
        createGroceryItems();
        
        System.out.println("\n----------------SHOW ALL GROCERY ITEMS---------------------------\n");
        
        showAllGroceryItems();
        
        System.out.println("\n--------------GET ITEM BY NAME-----------------------------------\n");
        
        getGroceryItemByName("Whole Wheat Biscuit");
        
        System.out.println("\n-----------GET ITEMS BY CATEGORY---------------------------------\n");
        
        getItemsByCategory("millets");
    
          System.out.println("\n-----------UPDATE CATEGORY NAME OF SNACKS CATEGORY----------------\n");
        
        updateCategoryName("snacks");    
                
        System.out.println("\n----------DELETE A GROCERY ITEM----------------------------------\n");
        
        deleteGroceryItem("Kodo Millet");
        
        System.out.println("\n------------FINAL COUNT OF GROCERY ITEMS-------------------------\n");
        
        findCountOfGroceryItems();

        System.out.println("\n------------Delete all items-------------------------\n");		

		// deleteAllGroceryItems();
		
        System.out.println("\n------------FINAL COUNT OF GROCERY ITEMS-------------------------\n");
        
        findCountOfGroceryItems();

        System.out.println("\n-------------------THANK YOU---------------------------");
		
	}

private void deleteAllGroceryItems() {
		groceryItemRepo.findAll().forEach(item -> deleteGroceryItem(item.getId()));
	}

	// READ
    // 1. Show all the data
	public void showAllGroceryItems() {
         
		groceryItemRepo.findAll().forEach(item -> System.out.println(getItemDetails(item)));
	}
	
	// 2. Get item by name
	public void getGroceryItemByName(String name) {
		System.out.println("Getting item by name: " + name);
		GroceryItemDocument item = groceryItemRepo.findItemByName(name);
		System.out.println(getItemDetails(item));
	}
	
	// 3. Get name and quantity of a all items of a particular category
	public void getItemsByCategory(String category) {
		System.out.println("Getting items for the category " + category);
		List<GroceryItemDocument> list = groceryItemRepo.findByCategory(category);
		
		list.forEach(item -> System.out.println("Name: " + item.getName() + ", Quantity: " + item.getQuantity()));
	}
	
	// 4. Get count of documents in the collection
	public void findCountOfGroceryItems() {
		long count = groceryItemRepo.count();
		System.out.println("Number of documents in the collection: " + count);
	}

	 // Print details in readable form
     
     public String getItemDetails(GroceryItemDocument item) {

		System.out.println(
		"Item Name: " + item.getName() + 
		", \nQuantity: " + item.getQuantity() +
		", \nItem Category: " + item.getCategory()
		);
		
		return "";
	}

	public void updateCategoryName(String category) {
         
		// Change to this new value
		String newCategory = "munchies";
		
		// Find all the items with the category snacks
		List<GroceryItemDocument> list = groceryItemRepo.findByCategory(category);
		
		list.forEach(item -> {
			// Update the category in each document
			item.setCategory(newCategory);
		});
		
		// Save all the items in database
		List<GroceryItemDocument> itemsUpdated = groceryItemRepo.saveAll(list);
		
		if(itemsUpdated != null)
			System.out.println("Successfully updated " + itemsUpdated.size() + " items.");         
	}

	// DELETE
	public void deleteGroceryItem(String id) {
		groceryItemRepo.deleteById(id);
		System.out.println("Item with id " + id + " deleted...");
	}



}
