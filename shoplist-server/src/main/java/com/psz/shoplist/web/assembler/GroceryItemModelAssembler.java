package com.psz.shoplist.web.assembler;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.psz.shoplist.model.GroceryItemModel;
import com.psz.shoplist.model.document.GroceryItemDocument;
import com.psz.shoplist.web.GroceryItemController;

/** This class cis converting the back end mobgo DB documente representation into the frontend REST model representation */
@Component
public class GroceryItemModelAssembler extends RepresentationModelAssemblerSupport<GroceryItemDocument, GroceryItemModel>{

    public GroceryItemModelAssembler(){
        super(GroceryItemController.class, GroceryItemModel.class);
    }

    @Override
    public GroceryItemModel toModel(GroceryItemDocument entity) {
        GroceryItemModel model = instantiateModel(entity);
        model.add(linkTo(
            methodOn(GroceryItemController.class)
            .findById(entity.getId()))
            .withSelfRel());
        model.setCategory(entity.getCategory());
        model.setId(entity.getId());;    
        model.setName(entity.getName());
        model.setQuantity(entity.getQuantity());
        return model;
    }
    
}
