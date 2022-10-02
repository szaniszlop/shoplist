package com.psz.shoplist.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.psz.shoplist.model.GroceryItem;
import com.psz.shoplist.repository.ItemRepository;

@RestController
@RequestMapping("/api/groceryItem")
public class GroceryItemController {
    
    @Autowired
    ItemRepository groceryItemRepo;
    
    @GetMapping
    public PaginatedResponse<GroceryItem> findAll(@RequestParam(defaultValue = "5") Optional<String> limit, @RequestParam(defaultValue = "0") Optional<String> offset ) {
        List<GroceryItem> response = groceryItemRepo.findAll();
        return new PaginatedResponse<>(response, offset.orElse("0"), limit.orElse("5"));
    }

    @GetMapping(value = "/{id}")
    public GroceryItem findById(@PathVariable("id") String id) {
        return RestPreconditions.checkFound(groceryItemRepo.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody GroceryItem resource) {
        RestPreconditions.checkNotNull(resource);
        return groceryItemRepo.save(resource).getId();
    }    

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) Long id, @RequestBody GroceryItem resource) {
        RestPreconditions.checkNotNull(resource);
        RestPreconditions.checkNotNull(groceryItemRepo.findById(resource.getId()));
        groceryItemRepo.save(resource);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") String id) {
        groceryItemRepo.deleteById(id);
    }    
}
