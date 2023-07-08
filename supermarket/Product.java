package supermarket;

import java.util.Date;
import java.util.Objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author David
 */
public class Product {

    private String ID;
    private String name;
    private int price;
    private String type;
    private String weight;
    private String expireDate;
    private String orderDate;
    private String supplier;
    private int nbItems;
    private int minStock;
    private int maxStock;

    String useByDate;
    String soldByDate;
    String kitchenType;
    String guatantee;

    private String imgPath;
    private String addedOn;

    public static final int NB_ATTRS = 13;
    private Date lastOrderDate = null;

    public Product(String ID, int nbItems) {
        this.ID = ID;
        this.nbItems = nbItems;
    }

    public Product(String ID, String type, String name, int nbItems, String min, String max) {
        this.ID = ID;
        this.nbItems = nbItems;
        minStock = Integer.parseInt(min);
        maxStock = Integer.parseInt(max);
        this.type = type;
        this.name = name;
    }

    public Product(String ID, String type, String name, String price,
            String weight, String expireDate, String orderDate,
            String supplier, int nbItems, String minStock,
            String maxStock, String useByDate, String soldByDate, String imgPath,
            String kitchenType, String guatantee, String addedOn) {
        this.ID = ID;
        this.name = name;
        this.price = Integer.parseInt(price);
        this.type = type;
        this.weight = weight;

        this.expireDate = expireDate;
        this.orderDate = orderDate;
        this.supplier = supplier;
        this.nbItems = nbItems;
        this.minStock = Integer.parseInt(minStock);

        this.maxStock = Integer.parseInt(maxStock);
        this.useByDate = useByDate;
        this.soldByDate = soldByDate;
        this.imgPath = imgPath;

        this.kitchenType = kitchenType;
        this.guatantee = guatantee;

        this.addedOn = addedOn;
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = Integer.parseInt(price);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getNbItems() {
        return nbItems;
    }

    public void setNbItems(int nbItems) {
        this.nbItems = nbItems;
    }

    public int getMinStock() {
        return minStock;
    }

    public void setMinStock(String minStock) {
        this.minStock = Integer.parseInt(minStock);
    }

    public int getMaxStock() {
        return maxStock;
    }

    public void setMaxStock(String maxStock) {
        this.maxStock = Integer.parseInt(maxStock);
    }

    public String getUseByDate() {
        return useByDate;
    }

    public void setUseByDate(String useByDate) {
        this.useByDate = useByDate;
    }

    public String getSoldByDate() {
        return soldByDate;
    }

    public void setSoldByDate(String soldByDate) {
        this.soldByDate = soldByDate;
    }

    public String getKitchenType() {
        return kitchenType;
    }

    public void setKitchenType(String kitchenType) {
        this.kitchenType = kitchenType;
    }

    public String getGuatantee() {
        return guatantee;
    }

    public void setGuatantee(String guatantee) {
        this.guatantee = guatantee;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public Date getLastOrderDate() {
        return lastOrderDate;
    }

    public void setLastOrderDate(Date lastOrderDate) {
        this.lastOrderDate = lastOrderDate;
    }

    @Override
    public String toString() {
        return "Product{" + "ID=" + ID + ", name=" + name + ", price=" + price
                + ", type=" + type + ", weight=" + weight + ", expireDate="
                + expireDate + ", orderDate=" + orderDate + ", supplier="
                + supplier + ", nbItems=" + nbItems + ", minStock=" + minStock
                + ", maxStock=" + maxStock + ", useByDate=" + useByDate
                + ", soldByDate=" + soldByDate + ", kitchenType=" + kitchenType
                + ", guatantee=" + guatantee + ", imgPath=" + imgPath
                + ", addedOn=" + addedOn + ", lastOrderDate=" + lastOrderDate
                + "}\n";
    }

    public String toFileString() {
        if (type.equals("food")) {
            return ID + " -- " + type + " -- " + name + " -- " + price + " -- "
                    + Math.round(price * 1.2 * 100.0) / 100.0
                    + " -- " + weight + " -- " + expireDate + " -- " + orderDate + " -- "
                    + supplier + " -- " + nbItems
                    + " -- " + minStock + " -- " + maxStock + " -- " + useByDate
                    + " -- " + soldByDate + " -- " + imgPath
                    + " -- " + addedOn + "\n";
        } else if (type.equals("electrical")) {
            return ID + " -- " + type + " -- " + name + " -- " + price + " -- "
                    + Math.round(price * 1.2 * 100.0) / 100.0
                    + " -- " + weight + " -- " + expireDate + " -- " + orderDate + " -- "
                    + supplier + " -- " + nbItems
                    + " -- " + minStock + " -- " + maxStock + " -- " + guatantee + " -- " + imgPath
                    + " -- " + addedOn + "\n";
        } else if (type.equals("kitchen")) {
            return ID + " -- " + type + " -- " + name + " -- " + price + " -- "
                    + Math.round(price * 1.2 * 100.0) / 100.0
                    + " -- " + weight + " -- " + expireDate + " -- " + orderDate + " -- "
                    + supplier + " -- " + nbItems
                    + " -- " + minStock + " -- " + maxStock + " -- " + kitchenType
                    + " -- " + imgPath + " -- " + addedOn + "\n";
        } else {
            return ID + " -- " + type + " -- " + name + " -- " + price + " -- "
                    + Math.round(price * 1.2 * 100.0) / 100.0
                    + " -- " + weight + " -- " + expireDate + " -- " + orderDate + " -- "
                    + supplier + " -- " + nbItems
                    + " -- " + minStock + " -- " + maxStock + " -- " + imgPath
                    + " -- " + addedOn + "\n";
        }

    }

}
