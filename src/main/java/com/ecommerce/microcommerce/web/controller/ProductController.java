package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.converter.json.MappingJacksonValue;
import com.ecommerce.microcommerce.exceptions.ProduitIntrouvableException;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@Api(description="Api permet d'avoir les opérations CRUD de produit")
public class ProductController {
    // on utilise @AutoWired pour créer une instance de productDao
    @Autowired
    private  ProductDao productDao ;

    //@TypeDeRequeteMapping permet de lier la requet avec la fonction à exécuter
    @ApiOperation(value = "récupérer la list des produits")
    @GetMapping(value = "/Produits")
    public MappingJacksonValue listProduits(){
        Iterable<Product>  produits=  productDao.findAll();

        //déclaration des régles de filtrage
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");

        //association des regles de filtrage au bean
        FilterProvider listeDesFiltres = new SimpleFilterProvider().addFilter("MonFiltreDynamique", monFiltre);

        //appliquer les filtres
        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);

        produitsFiltres.setFilters(listeDesFiltres);

        return produitsFiltres;
    }
    @ApiOperation(value="recupérer les produits par son id")
    @GetMapping(value="Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id) {
        Product produit =  productDao.findById(id);
        if(produit==null ) throw new ProduitIntrouvableException("le produit avec l'id"+id+"n'existe pas");
        return  produit;
    }

    @ApiOperation(value="récupérer les produits ayont le prix plus cher supérieur a 400")
    @GetMapping(value="test/Produits/{prixLimit}")
    public List<Product> afficherProduitsPlusCher(@PathVariable int prixLimit){
        return productDao.findByPrixGreaterThan(400);
    }

    @ApiOperation(value="récupérer les produits par le nom recherché")
    @GetMapping(value="ProduitsParNom/{nomatrouve}")
    public List<Product> afficherProduitParNom(@PathVariable String nomatrouve){
        return productDao.findByNomLike("%"+nomatrouve+"%");
    }

   @PostMapping(value= "/Produits")
    public ResponseEntity<Void> ajouterProduit(@Valid  @RequestBody  Product product){
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
    @DeleteMapping(value="/Produits/{id}")
    public void supprimerProduit(@PathVariable int id ){
         productDao.deleteById(id);
    }
    @PutMapping(value="/Produits")
    public void updateProduit(@RequestBody Product produit){
        productDao.save(produit);
    }
}
