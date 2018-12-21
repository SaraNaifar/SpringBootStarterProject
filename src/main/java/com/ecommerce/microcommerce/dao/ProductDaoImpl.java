package com.ecommerce.microcommerce.dao;
import com.ecommerce.microcommerce.model.Product;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {

    public static List<Product> products = new ArrayList<>();
    static{
        products.add(new Product(1, new String("livre"), 320));
        products.add(new Product( 2, new String("ordinateur"), 600));
        products.add(new Product(3, new String("clavier"), 190));
    }

    @Override
    // type de retour : List<Product> -- retourne la list construite
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Product findById(int id) {
        for(Product product : products ){
            if (product.getId() == id ){
                return product ;
            }
        }
        return null;
    }

    @Override
    public Product save(Product product) {
        products.add(product);
        return product;
    }
}
