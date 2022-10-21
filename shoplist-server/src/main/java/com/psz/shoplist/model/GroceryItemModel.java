package com.psz.shoplist.model;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class GroceryItemModel extends RepresentationModel<GroceryItemModel> {
    private String id;

    private String name;
    private double price;
    private double weight;
    private String category;
    private String description;
    private List<String> labels;

}
