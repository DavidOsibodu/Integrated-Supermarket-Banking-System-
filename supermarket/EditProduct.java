/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supermarket;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.regex.Pattern;
import javax.lang.model.SourceVersion;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author David
 */
public class EditProduct extends javax.swing.JFrame {

    /**
     * Creates new form EditProduct
     */
    Product P;
    ProductList products;
    File image;

    public EditProduct(String ID, ProductList products) {
        initComponents();
        this.P = products.getProduct(ID);
        this.products = products;

        name.setText(P.getName());
        price.setText(String.valueOf(P.getPrice()));
        weight.setText(P.getWeight());
        expDate.setText(P.getExpireDate());
        estOrderDate.setText(P.getOrderDate());

        supplier.setText(P.getSupplier());
        noItems.setText(String.valueOf(P.getNbItems()));
        min.setText(String.valueOf(P.getMinStock()));
        max.setText(String.valueOf(P.getMaxStock()));
        soldbydate.setText(P.getSoldByDate());

        usebydate.setText(P.getUseByDate());
        expDate.setText(P.getExpireDate());
        image = new File(P.getImgPath());

        switch (P.getType()) {
            case "other":
                foodExtra.setVisible(false);
                elecExtra.setVisible(false);
                kitchenExtra.setVisible(false);
                break;
            case "electrical":
                guaranteeDuration.setText(P.getGuatantee());
                kitchenExtra.setVisible(false);
                foodExtra.setVisible(false);
                break;
            case "food":
                elecExtra.setVisible(false);
                kitchenExtra.setVisible(false);
                String w = "";
                if (P.getWeight().contains("lb")) {
                    w = P.getWeight().substring(0, P.getWeight().indexOf("l"));
                    weightCateg.setSelectedIndex(1);
                } else if (P.getWeight().contains("g") && !P.getWeight().contains("Kg")) {
                    w = P.getWeight().substring(0, P.getWeight().indexOf("g"));
                    weightCateg.setSelectedIndex(0);
                } else if (P.getWeight().contains("Kg")) {
                    w = P.getWeight().substring(0, P.getWeight().indexOf("K"));
                    weightCateg.setSelectedIndex(2);
                } else if (P.getWeight().contains("L")) {
                    w = P.getWeight().substring(0, P.getWeight().indexOf("L"));
                    weightCateg.setSelectedIndex(3);
                } else if (P.getWeight().contains("cL")) {
                    w = P.getWeight().substring(0, P.getWeight().indexOf("c"));
                    weightCateg.setSelectedIndex(4);
                }
                weight.setText(w);
                break;
            case "kitchen":
                if (P.getKitchenType().equals("Electrical")) {
                    prodType.setSelectedIndex(0);
                } else {
                    prodType.setSelectedIndex(1);
                }
                foodExtra.setVisible(false);
                elecExtra.setVisible(false);
                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        weight = new javax.swing.JTextField();
        price = new javax.swing.JTextField();
        expDate = new javax.swing.JTextField();
        estOrderDate = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        supplier = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        max = new javax.swing.JTextField();
        min = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        editProductBtn = new javax.swing.JButton();
        errMsg = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        noItems = new javax.swing.JTextField();
        foodExtra = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        soldbydate = new javax.swing.JTextField();
        usebydate = new javax.swing.JTextField();
        weightCateg = new javax.swing.JComboBox<>();
        elecExtra = new javax.swing.JPanel();
        guarantDays = new javax.swing.JComboBox<>();
        guaranteeDuration = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        kitchenExtra = new javax.swing.JPanel();
        prodType = new javax.swing.JComboBox<>();
        jLabel61 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Editing Product:     ");

        jLabel3.setText("Name");

        jLabel4.setText("Price");

        jLabel5.setText("Expiry Date");

        jLabel6.setText("Weight");

        jLabel7.setText("Estimated Date to order");

        jLabel8.setText("Supplier(s)");

        jLabel9.setText("Quantity in stock");

        jLabel10.setText("MIN");

        jLabel11.setText("MAX");

        editProductBtn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        editProductBtn.setText("EDIT PRODUCT");
        editProductBtn.setToolTipText("");
        editProductBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProductBtnActionPerformed(evt);
            }
        });

        errMsg.setForeground(new java.awt.Color(255, 0, 0));
        errMsg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel12.setText("N° of items");

        jLabel45.setText("Weight Category");

        jLabel46.setText("Use by Date");

        jLabel47.setText("Sold by Date");

        weightCateg.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "g", "lb", "kg", "L", "cL" }));

        javax.swing.GroupLayout foodExtraLayout = new javax.swing.GroupLayout(foodExtra);
        foodExtra.setLayout(foodExtraLayout);
        foodExtraLayout.setHorizontalGroup(
            foodExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(foodExtraLayout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addComponent(weightCateg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(164, Short.MAX_VALUE))
            .addGroup(foodExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(foodExtraLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(foodExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel45)
                        .addComponent(jLabel46)
                        .addComponent(jLabel47))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(foodExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(soldbydate, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                        .addComponent(usebydate))
                    .addContainerGap()))
        );
        foodExtraLayout.setVerticalGroup(
            foodExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(foodExtraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(weightCateg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(86, Short.MAX_VALUE))
            .addGroup(foodExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, foodExtraLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(foodExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(foodExtraLayout.createSequentialGroup()
                            .addGroup(foodExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(foodExtraLayout.createSequentialGroup()
                                    .addComponent(jLabel45)
                                    .addGap(18, 18, 18)
                                    .addComponent(usebydate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel46))
                            .addGap(40, 40, 40))
                        .addComponent(soldbydate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel47))
                    .addContainerGap()))
        );

        guarantDays.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Days", "Months", "Years" }));

        guaranteeDuration.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel60.setText("Guarantee Duration");

        javax.swing.GroupLayout elecExtraLayout = new javax.swing.GroupLayout(elecExtra);
        elecExtra.setLayout(elecExtraLayout);
        elecExtraLayout.setHorizontalGroup(
            elecExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(elecExtraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel60)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(guaranteeDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(guarantDays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );
        elecExtraLayout.setVerticalGroup(
            elecExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(elecExtraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(elecExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(guaranteeDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel60)
                    .addComponent(guarantDays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        prodType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Electrical", "Crockery" }));

        jLabel61.setText("Type ");

        javax.swing.GroupLayout kitchenExtraLayout = new javax.swing.GroupLayout(kitchenExtra);
        kitchenExtra.setLayout(kitchenExtraLayout);
        kitchenExtraLayout.setHorizontalGroup(
            kitchenExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kitchenExtraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel61)
                .addGap(18, 18, 18)
                .addComponent(prodType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
        );
        kitchenExtraLayout.setVerticalGroup(
            kitchenExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kitchenExtraLayout.createSequentialGroup()
                .addGroup(kitchenExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(prodType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(min, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel11)
                        .addGap(3, 3, 3)
                        .addComponent(max, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(117, 117, 117)
                        .addComponent(editProductBtn))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(noItems, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(estOrderDate, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(name, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(weight, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(price, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(expDate, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(supplier, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(373, 373, 373)
                                .addComponent(errMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(kitchenExtra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(foodExtra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(elecExtra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(481, 481, 481))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(kitchenExtra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(elecExtra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(weight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(expDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(estOrderDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)))
                            .addComponent(foodExtra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(supplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(max, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(min, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(editProductBtn))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel12)
                                .addComponent(noItems, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(errMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(117, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void editProductBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProductBtnActionPerformed
        // TODO add your handling code here:
        errMsg.setText("");
        if (name.getText().trim().length() == 0) {
            errMsg.setText("Enter Product Name");
        } else if (price.getText().trim().length() == 0) {
            errMsg.setText("Enter Product Price");
        } else if (!isStringInt(price.getText().trim())) {
            errMsg.setText("Price Must be a number");
        } else if (weight.getText().trim().length() == 0) {
            errMsg.setText("Enter Product Weight");
        } else if (!isStringInt(weight.getText().trim())) {
            errMsg.setText("Weight Must be a number");
        } else if (estOrderDate.getText().trim().length() == 0) {
            errMsg.setText("Enter Estimated Order Date");
        } else if (!isValidDateStr(estOrderDate.getText().trim())) {
            errMsg.setText("Order Date format is invalid");
        } else if (supplier.getText().trim().length() == 0) {
            errMsg.setText("Enter Supplier(s)");
        } else if (min.getText().trim().length() == 0) {
            errMsg.setText("Enter Min Quantity");
        } else if (!isStringInt(min.getText().trim())) {
            errMsg.setText("Min Quantity must be a number");
        } else if (max.getText().trim().length() == 0) {
            errMsg.setText("Enter Max Quantity");
        } else if (!isStringInt(max.getText().trim())) {
            errMsg.setText("Max Quantity must be a number");
        } else if (Integer.parseInt(min.getText().trim()) > Integer.parseInt(min.getText().trim())) {
            errMsg.setText("Max Quantity can't be lower than Min");
        } else if (noItems.getText().trim().length() == 0) {
            errMsg.setText("Enter Number of Items");
        } else if (!isStringInt(noItems.getText().trim())) {
            errMsg.setText("Number of items must be a number");
        } else if (this.image == null) {
            errMsg.setText("Select an image");
        } else {
            boolean pass = false;
            if (P.getType().equals("food")) {
                if (expDate.getText().trim().length() == 0) {
                    errMsg.setText("Enter Expiry Date");
                } else if (!isValidDateStr(expDate.getText().trim())) {
                    errMsg.setText("Expiry Date format is invalid");
                } else if (usebydate.getText().trim().length() == 0) {
                    errMsg.setText("Enter Use by Date");
                } else if (soldbydate.getText().trim().length() == 0) {
                    errMsg.setText("Enter Sold by Date");
                } else {
                    pass = true;
                }
            } else if (P.getType().equals("electrical") && guaranteeDuration.getText().length() == 0) {
                errMsg.setText("Enter guarantee Duration");
            } else {
                pass = true;
            }

            if (pass == true) {
                /* String imgPath = "images/" + this.P.getID() + image.getName().substring(image.getName().lastIndexOf("."), image.getName().length());
                try {
                    File source = new File(image.getPath());
                    File dest = new File(imgPath);
                    copyFileUsingStream(source, dest);
                } catch (Exception e) {

                } */

                System.out.println(P.toString());

                P.setName(name.getText());
                P.setPrice(price.getText());
                if (P.getType().equals("food")) {
                    P.setWeight(weight.getText() + (String) weightCateg.getSelectedItem());
                } else {
                    P.setWeight(weight.getText());
                }
                P.setExpireDate(expDate.getText());
                P.setOrderDate(estOrderDate.getText());

                P.setSupplier(supplier.getText());
                P.setNbItems(Integer.parseInt(noItems.getText()));
                P.setMinStock(min.getText());
                P.setMaxStock(max.getText());
                P.setSoldByDate(soldbydate.getText());

                P.setGuatantee(guaranteeDuration.getText() + " " + guarantDays.getSelectedItem());
                P.setKitchenType((String) prodType.getSelectedItem());
                P.setUseByDate(usebydate.getText());
                P.setExpireDate(expDate.getText());
                //P.setImgPath(imgPath);

                System.out.println(P.toString());

                products.saveToFile();
                JOptionPane.showMessageDialog(this, "Product Edited Successfully");
                this.dispose();

            }
        }
    }//GEN-LAST:event_editProductBtnActionPerformed

    public boolean isStringInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public boolean isValidDateStr(String d) {
        return Pattern.compile("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)\\d\\d$", Pattern.CASE_INSENSITIVE).matcher(d).matches();
    }

    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    public void browseForImage() {
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String fext = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
            if (fext.equals("png") || fext.equals("jpg") || fext.equals("jpeg") || fext.equals("gif")) {
                try {
                    this.image = file;

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "File not found", "ERROR", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Select a valid image file. \nValid extensions are: (png / jpg/ jpeg/ gif)", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No file was selected.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton editProductBtn;
    private javax.swing.JPanel elecExtra;
    private javax.swing.JLabel errMsg;
    private javax.swing.JTextField estOrderDate;
    private javax.swing.JTextField expDate;
    private javax.swing.JPanel foodExtra;
    private javax.swing.JComboBox<String> guarantDays;
    private javax.swing.JTextField guaranteeDuration;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel kitchenExtra;
    private javax.swing.JTextField max;
    private javax.swing.JTextField min;
    private javax.swing.JTextField name;
    private javax.swing.JTextField noItems;
    private javax.swing.JTextField price;
    private javax.swing.JComboBox<String> prodType;
    private javax.swing.JTextField soldbydate;
    private javax.swing.JTextField supplier;
    private javax.swing.JTextField usebydate;
    private javax.swing.JTextField weight;
    private javax.swing.JComboBox<String> weightCateg;
    // End of variables declaration//GEN-END:variables

}
