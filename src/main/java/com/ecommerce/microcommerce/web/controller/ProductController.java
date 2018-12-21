package com.ecommerce.microcommerce.web.controller;
import com.ecommerce.microcommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.microcommerce.dao.ProductDao;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private  ProductDao productDao ;


    @GetMapping(value = "/Produits")
    public List<Product> listProduits(){
        return productDao.findAll();
    }

    @GetMapping(value="Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id) {
        return productDao.findById(id);
    }

    @PostMapping(value= "/Produits")
    public Product ajouterProduit(@RequestBody  Product product){
        return productDao.save(product);
    }
}
