package com.psz.shoplist.model.document;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.psz.shoplist.model.GroceryItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@Document
public class GroceryItemDocument implements GroceryItem{
    @Id
    private String id;

    private String name;
    private double price;
    private double weight;
    private String description;
    private String category;   
    private List<String> labels; 
    
    @Override
    public String toString() {
        return "GroceryItem [id=" + id + ", name=" + name + ", price=" + price + ", category=" + category + 
        ", labels[" + labels.stream().reduce("", (a, b) -> { return a + ", " + b;}) + "], description=" + description + "]";
    }    
}
