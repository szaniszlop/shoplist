package com.psz.shoplist.model.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@Document
public class GroceryItemDocument {
    @Id
    private String id;

    private String name;
    private int quantity;
    private String category;    
    
    @Override
    public String toString() {
        return "GroceryItem [id=" + id + ", name=" + name + ", quantity=" + quantity + ", category=" + category + "]";
    }    
}
