package com.psz.shoplist.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GroceryItemModel extends RepresentationModel<GroceryItemModel> implements GroceryItem{
    private String id;

    private String name;
    private int quantity;
    private String category;

    @Override
    public String toString() {
        return "GroceryItem [id=" + id + ", name=" + name + ", quantity=" + quantity + ", category=" + category + "]";
    }
}
