package com.psz.shoplist.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.psz.shoplist.model.GroceryItemModel;
import com.psz.shoplist.model.document.GroceryItemDocument;
import com.psz.shoplist.repository.GroceryItemRepository;
import com.psz.shoplist.web.assembler.GroceryItemModelAssembler;

@RestController
@RequestMapping("/api/v1/groceryItem")
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class GroceryItemController {
    
    @Autowired
    GroceryItemRepository groceryItemRepo;
    
    @Autowired
    GroceryItemModelAssembler groceryItemModelAssembler;

    @GetMapping
    public HttpEntity<PagedModel<EntityModel<GroceryItemModel>>> findAll(
        @PageableDefault(size = 2) Pageable pageable, 
        PagedResourcesAssembler<GroceryItemModel> assembler ) {
            // first get the documents page form repo
            Page<GroceryItemDocument> groceryItems = groceryItemRepo.findAll(pageable);
            // converts documents page into model objects page
            Page<GroceryItemModel> groceryItemsModel = groceryItems.map((GroceryItemDocument doc) -> {return groceryItemModelAssembler.toModel(doc);});
            // then convert the whole Page to an hdml entity
            return new ResponseEntity<>(assembler.toModel(groceryItemsModel), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public HttpEntity<GroceryItemModel> findById(@PathVariable("id") String id) {
        GroceryItemDocument groceryItem =  RestPreconditions.checkFound(groceryItemRepo.findById(id));
        return new ResponseEntity<>(groceryItemModelAssembler.toModel(groceryItem), HttpStatus.OK);
    }

    @PostMapping
    public HttpEntity<GroceryItemModel> create(@RequestBody GroceryItemDocument resource) {
        RestPreconditions.checkNotNull(resource);
        GroceryItemDocument groceryItem = groceryItemRepo.save(resource);
        return new ResponseEntity<>(groceryItemModelAssembler.toModel(groceryItem), HttpStatus.CREATED);
    }    

    @PutMapping(value = "/{id}")
    public HttpEntity<GroceryItemModel> update(@PathVariable( "id" ) Long id, @RequestBody GroceryItemDocument resource) {
        RestPreconditions.checkNotNull(resource);
        RestPreconditions.checkNotNull(groceryItemRepo.findById(resource.getId()));
        GroceryItemDocument groceryItem = groceryItemRepo.save(resource);
        return new ResponseEntity<>(groceryItemModelAssembler.toModel(groceryItem),HttpStatus.OK);        
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") String id) {
        groceryItemRepo.deleteById(id);
    }    

}
