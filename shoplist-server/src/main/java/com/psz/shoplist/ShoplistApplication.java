package com.psz.shoplist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.psz.shoplist.model.GroceryList;
import com.psz.shoplist.model.document.GroceryItemDocument;
import com.psz.shoplist.model.document.GroceryListDocument;
import com.psz.shoplist.repository.GroceryItemRepository;
import com.psz.shoplist.repository.GroceryListRepository;

@SpringBootApplication
@EnableMongoRepositories
public class ShoplistApplication implements CommandLineRunner  {

	@Autowired
    GroceryItemRepository groceryItemRepo;

	@Autowired
    GroceryListRepository groceryListRepo;

	public static void main(String[] args) {
		SpringApplication.run(ShoplistApplication.class, args);
	}

	//CREATE
    void createGroceryItems() {
        System.out.println("Data creation started...");
		List<GroceryItemDocument> groceryItems = new ArrayList<>();
		groceryItems.add(new GroceryItemDocument(UUID.randomUUID().toString(), "Whole Wheat Biscuit", 5.0, 0.50, "Whole Wheat Biscuit", "snacks",  Arrays.asList(new String[]{"snacks", "biskits", "bio"})));
        groceryItems.add(new GroceryItemDocument(UUID.randomUUID().toString(), "XYZ Kodo Millet healthy", 2.0, 0.250, "XYZ Kodo Millet healthy", "millets",  Arrays.asList(new String[]{"snacks", "biskits"})));
        groceryItems.add(new GroceryItemDocument(UUID.randomUUID().toString(), "Dried Whole Red Chilli", 3.0, 0.250, "XYZ Kodo Millet healthy", "millets",  Arrays.asList(new String[]{"snacks", "biskits"})));
        groceryItems.add(new GroceryItemDocument(UUID.randomUUID().toString(), "Healthy Pearl Millet", 3.0, 0.250, "XYZ Kodo Millet healthy", "snacks",  Arrays.asList(new String[]{"snacks", "biskits"})));
        groceryItems.add(new GroceryItemDocument(UUID.randomUUID().toString(), "Bonny Cheese Crackers Plain", 1.0, 0.250, "XYZ Kodo Millet healthy", "snacks",  Arrays.asList(new String[]{"snacks", "biskits"})));
		groceryItems.stream().forEach((item) -> {groceryItemRepo.save(item);});

		GroceryListDocument list = new GroceryListDocument(UUID.randomUUID().toString(), "szaniszlop", new Date(), new Date(), new ArrayList<>());
		list.getItems().add(groceryItems.get(0));
		list.getItems().add(groceryItems.get(2));
		groceryListRepo.save(list);

		list = new GroceryListDocument(UUID.randomUUID().toString(), "someoneElse", new Date(), new Date(), new ArrayList<>());
		list.getItems().add(groceryItems.get(3));
		list.getItems().add(groceryItems.get(4));
		groceryListRepo.save(list);

		list = new GroceryListDocument(UUID.randomUUID().toString(), "szaniszlop", new Date(), new Date(), new ArrayList<>());
		list.getItems().add(groceryItems.get(3));
		list.getItems().add(groceryItems.get(4));
		groceryListRepo.save(list);

        System.out.println("Data creation complete...");
    }

	@PreDestroy
	public void onExit() {
		System.out.println("###STOPing###");
		deleteAllGroceryItems();
		deleteAllGroceryLists();
		System.out.println("###STOP FROM THE LIFECYCLE###");
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

        System.out.println("\n------------FINAL COUNT OF GROCERY ITEMS-------------------------\n");
        
        findCountOfGroceryItems();

        System.out.println("\n------------Find list by client ID-------------------------\n");
        
        findListByClientId("szaniszlop");

        System.out.println("\n------------GetListCount-------------------------\n");
        
        findCountOfLists();		

		System.out.println("\n-------------------THANK YOU---------------------------");
		
	}

	private void deleteAllGroceryItems() {
		groceryItemRepo.findAll().forEach(item -> deleteGroceryItem(item.getId()));
	}

	private void deleteAllGroceryLists() {
		groceryListRepo.findAll().forEach(item -> deleteGroceryList(item.getId()));
	}

	// READ
    // 1. Show all the data
	public void showAllGroceryItems() {
         
		groceryItemRepo.findAll().forEach(item -> System.out.println(getItemDetails(item)));
	}
	
	// 2. Get item by name
	public void getGroceryItemByName(String name) {
		System.out.println("Getting item by name: " + name);
		List<GroceryItemDocument> items = groceryItemRepo.findItemByName(name);
		items.stream().forEach((item) -> {System.out.println(getItemDetails(item));});
	}
	
	// 3. Get name and quantity of a all items of a particular category
	public void getItemsByCategory(String category) {
		System.out.println("Getting items for the category " + category);
		List<GroceryItemDocument> list = groceryItemRepo.findByCategory(category);
		
		list.forEach(item -> System.out.println("Name: " + item.getName() + ", Price: " + item.getPrice()));
	}
	
	// 4. Get count of documents in the collection
	public void findCountOfGroceryItems() {
		long count = groceryItemRepo.count();
		System.out.println("Number of documents in the collection: " + count);
	}

	 // Print details in readable form
     
     public String getItemDetails(GroceryItemDocument item) {

		System.out.println(item);
		
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

	public void findListByClientId(String clientId){
		System.out.println("Getting lists for client: " + clientId);
		List<GroceryListDocument> documents = groceryListRepo.findByClientId(clientId);
		documents.forEach((doc) -> {System.out.println(doc);});
	}

	public void findCountOfLists(){
		long count = groceryListRepo.count();
		System.out.println("Number of documents in the lists collection: " + count);
	}		

	// DELETE
	public void deleteGroceryItem(String id) {
		groceryItemRepo.deleteById(id);
		System.out.println("Item with id " + id + " deleted...");
	}

	public void deleteGroceryList(String id) {
		groceryListRepo.deleteById(id);
		System.out.println("List with id " + id + " deleted...");
	}


}
