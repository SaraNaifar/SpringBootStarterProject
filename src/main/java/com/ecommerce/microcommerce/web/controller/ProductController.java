package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.net.URI;
import java.util.List;

@RestController
public class ProductController {
    // on utilise @AutoWired pour créer une instance de productDao
    @Autowired
    private  ProductDao productDao ;

    //@TypeDeRequeteMapping permet de lier la requet avec la fonction à exécuter
    @GetMapping(value = "/Produits")
    public MappingJacksonValue listProduits(){
        List<Product>  produits=  productDao.findAll();

        //déclaration des régles de filtrage
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");

        //association des regles de filtrage au bean
        FilterProvider listeDesFiltres = new SimpleFilterProvider().addFilter("MonFiltreDynamique", monFiltre);

        //appliquer les filtres
        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);

        produitsFiltres.setFilters(listeDesFiltres);

        return produitsFiltres;
    }

    @GetMapping(value="Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id) {
        return productDao.findById(id);
    }

    @PostMapping(value= "/Produits")
    public ResponseEntity<Void> ajouterProduit(@RequestBody  Product product){
        Product addedProduct = productDao.save(product);
        if( addedProduct == null){
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addedProduct.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
