package supermarket;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author David
 */
public class ProductList {

    private ArrayList<Product> productsList;

    public ProductList() {
        this.productsList = new ArrayList();
    }
    
    public void setProductList(ArrayList<Product> product){
        this.productsList = product;
    }

    public void addProduct(Product product) {
        productsList.add(product);
    }

    public Product getProduct(String ID) {
        for (Product product : productsList) {
            if (product.getID().equals(ID)) {
                return product;
            }
        }
        return null;
    }

    public void setNbItems(String ID, int nb) {
        for (Product product : productsList) {
            if (product.getID().equals(ID)) {
                product.setNbItems(nb);
            }
        }
    }

    public void saveToFile() {
        try {
            FileWriter fw = new FileWriter("data/test.txt");
            for (Product product : productsList) {               
                fw.write(product.toFileString());
            }
            fw.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public boolean contains(String id) {
        for (Product product : productsList) {
            if (product.getID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "ProductList{" + "productsList=" + productsList + '}';
    }

}
