package com.psz.shoplist.model;

import java.util.List;

public interface GroceryItem {
    String getId();
    String getName();
    double getPrice();
    double getWeight();
    String getCategory();
    List<String> getLabels();
}

