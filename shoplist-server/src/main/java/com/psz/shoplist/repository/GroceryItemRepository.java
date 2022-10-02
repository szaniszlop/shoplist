package com.psz.shoplist.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.psz.shoplist.model.document.GroceryItemDocument;

public interface GroceryItemRepository extends MongoRepository<GroceryItemDocument, String>{
    @Query(value="{name: '?0'}")
    GroceryItemDocument findItemByName(String name);

    @Query(value="{category: '?0'}")
    List<GroceryItemDocument> findByCategory(String category);

    public long count();
}
