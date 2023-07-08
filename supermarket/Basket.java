/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supermarket;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author David
 */
public class Basket {

    public class Product {

        String ID;
        String name;
        String type;
        String weight;
        int price;
        int nbItems;
        String addedOn;

        public Product(String ID, String name, String type, int price, int nbItems, String weight, String addedOn) {
            this.ID = ID;
            this.name = name;
            this.type = type;
            this.price = price;
            this.nbItems = nbItems;
            this.weight = weight;
            this.addedOn = addedOn;
        }

        public Product(String ID) {
            this.ID = ID;
        }

        public void setNbItems(int nbItems) {
            this.nbItems = nbItems;
        }

        @Override
        public String toString() {
            return ID + " -- " + name + " -- "
                    + type + " -- " + price + " -- " + nbItems
                    + " -- " + weight + " -- " + addedOn;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            final Product other = (Product) obj;
            if (!Objects.equals(this.ID, other.ID)) {
                return false;
            }
            return true;
        }

        public int getNbItems() {
            return nbItems;
        }

        public String getID() {
            return ID;
        }

        public int getPrice() {
            return price;
        }

    }

    private ArrayList<Basket.Product> products = new ArrayList();
    //private int totalPrice;
    //private int totalQuantity;

    public void addProduct(String ID, String name, String type,
            int price, int nbItems, String weight, String addedOn) {
        if (products.contains(new Product(ID))) {
            getProduct(ID).setNbItems(nbItems + getProduct(ID).getNbItems());
        } else {
            products.add(new Product(ID, name, type, price, nbItems, weight, addedOn));
        }
        //totalPrice += price * nbItems;
        //totalQuantity += nbItems;
    }

    public Basket.Product getProduct(String id) {
        for (Basket.Product p : products) {
            if (new Product(id).equals(p)) {
                return p;
            }
        }
        return null;
    }

    public int getProductNb(String id) {
        int i = 0;
        for (Product p : products) {
            if (p.equals(new Product(id))) {
                return i;
            }
            i++;
        }
        return i;
    }

    public void removeRow(int row) {
        //totalQuantity -= products.get(row).getNbItems();
        //totalPrice -= products.get(row).getPrice() * products.get(row).getNbItems();
        products.remove(row);
    }

    public void editNbItems(String ID, int nb) {
        for (Product p : products) {
            if (p.ID.equals(ID)) {
                p.setNbItems(nb);
            }
        }
    }

    @Override
    public String toString() {
        return "Basket{" + "products=" + products + ", totalPrice=" + this.getTotalPrice() + '}';
    }

    public boolean isEmpty() {
        return products.size() == 0;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for(Product p : products) {
            totalPrice += p.getNbItems() * p.getPrice();
                    
        }
        return totalPrice;
    }

    public int getTotalQuantity() {
        int totalQuantity = 0;
        for(Product p : products) {
            totalQuantity += p.getNbItems();
                    
        }
        return totalQuantity;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

}
