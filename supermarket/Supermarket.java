package supermarket;

import java.awt.Component;
import java.awt.Image;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author David
 */
public class Supermarket extends javax.swing.JFrame {

    Customer customer;
    Basket basket = new Basket();
    Wishlist wishlist = new Wishlist();
    ProductList products = new ProductList();
    String uName;

    public Supermarket(String uName) {
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);

        loadDataFromFile(uName);

        productsTabs.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                foodTab.clearSelection();
                kitchenTab.clearSelection();
                elecTab.clearSelection();
                otherTab.clearSelection();
            }
        });
    }

    public void loadDataFromFile(String uName) {
        // load customer information
        try {
            this.uName = uName;
            File inputFile = new File("data/users.txt");
            Scanner inputReader = new Scanner(inputFile);
            while (inputReader.hasNextLine()) {
                String data = inputReader.nextLine();
                String[] user = data.split(" -- ");
                if (user[0].equals(uName)) {
                    username.setText(user[0]);
                    fname.setText(user[2]);
                    lname.setText(user[3]);
                    dob.setText(user[4]);
                    gender.setText(user[5]);
                    address.setText(user[6]);
                    customerSince.setText(user[7]);
                    break;
                }
            }
            inputReader.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Couldn't find users file.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        //Load customer stock information
        DefaultTableModel model = (DefaultTableModel) stockTab.getModel();

        try {
            File inputFile = new File("data/stocks/" + uName + ".txt");
            Scanner inputReader = new Scanner(inputFile);
            while (inputReader.hasNextLine()) {
                String[] s = inputReader.nextLine().split(" -- ");
                model.addRow(new Object[]{s[0], s[1], s[2], s[3], s[4], s[5]});
            }
            inputReader.close();
        } catch (Exception e) {
        }
        
        // Load customer basket information
        DefaultTableModel basketModel = (DefaultTableModel) basketTab.getModel();
        
        try {
            File inputFile = new File("data/baskets/" + uName + ".txt");
            Scanner inputReader = new Scanner(inputFile);
            while (inputReader.hasNextLine()) {
                String[] s = inputReader.nextLine().split(" -- ");
                basketModel.addRow(new Object[]{s[0], s[1], s[2], s[3], s[4], s[5]});
            }
            inputReader.close();
        } catch (Exception e) {
            
        }

        // load products from file
        try {
            File inputFile = new File("data/products.txt");
            Scanner inputReader = new Scanner(inputFile);
            int i = 0;
            while (inputReader.hasNextLine()) {
                String[] p = inputReader.nextLine().split(" -- ");
                appendProductRow(p);
                products.addProduct(new Product(p[0], Integer.parseInt(p[9])));
            }
            inputReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
        }

    }

    public void appendProductRow(String[] p) {
        JLabel imgLabel = new JLabel();

        String type = p[1];

        if (type.equals("food")) {
            ImageIcon icon = new ImageIcon(p[14]);
            Image ic = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(ic));

            foodTab.getColumn("Image").setCellRenderer(new myTableCellRenderer());
            DefaultTableModel model = (DefaultTableModel) foodTab.getModel();
            model.addRow(new Object[]{p[0], p[2], p[3], p[4], p[5], p[6], p[7], p[8], p[9], p[10], p[11], p[12], p[13], imgLabel
            });
        } else if (type.equals("kitchen")) {
            ImageIcon icon = new ImageIcon(p[13]);
            Image ic = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(ic));

            kitchenTab.getColumn("Image").setCellRenderer(new myTableCellRenderer());
            DefaultTableModel model = (DefaultTableModel) kitchenTab.getModel();
            model.addRow(new Object[]{p[0], p[2], p[3], p[4], p[5], p[6], p[7], p[8], p[9], p[10], p[11], p[12], imgLabel
            });
        } else if (type.equals("electrical")) {
            ImageIcon icon = new ImageIcon(p[13]);
            Image ic = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(ic));

            elecTab.getColumn("Image").setCellRenderer(new myTableCellRenderer());
            DefaultTableModel model = (DefaultTableModel) elecTab.getModel();
            model.addRow(new Object[]{p[0], p[2], p[3], p[4], p[5], p[6], p[7], p[8], p[9], p[10], p[11], p[12], imgLabel
            });
        } else {
            ImageIcon icon = new ImageIcon(p[12]);
            Image ic = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(ic));

            otherTab.getColumn("Image").setCellRenderer(new myTableCellRenderer());
            DefaultTableModel model = (DefaultTableModel) otherTab.getModel();
            model.addRow(new Object[]{p[0], p[2], p[3], p[4], p[5], p[6], p[7], p[8], p[9], p[10], p[11], imgLabel});
        }
    }

    class myTableCellRenderer implements TableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            foodTab.setRowHeight(100);
            kitchenTab.setRowHeight(100);
            elecTab.setRowHeight(100);
            otherTab.setRowHeight(100);
            return (Component) value;
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
        jTabbedPane4 = new javax.swing.JTabbedPane();
        productsTabs = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        kitchenTab = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        elecTab = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        otherTab = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        foodTab = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        username = new javax.swing.JLabel();
        fname = new javax.swing.JLabel();
        lname = new javax.swing.JLabel();
        dob = new javax.swing.JLabel();
        gender = new javax.swing.JLabel();
        address = new javax.swing.JLabel();
        customerSince = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        stockTab = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        basketTab = new javax.swing.JTable();
        removeFromBasketBTN = new javax.swing.JButton();
        editBasketNbItemsBTN = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        newNbItems = new javax.swing.JTextField();
        checkOutBTN = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        quantity = new javax.swing.JTextField();
        addToBasket = new javax.swing.JButton();
        addToWishlist = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI Historic", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 0, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("WELCOME TO THE SUPERMARKET!");

        jTabbedPane4.setToolTipText("Shop for products");
        jTabbedPane4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTabbedPane4.setName("Shop"); // NOI18N

        productsTabs.setBackground(new java.awt.Color(153, 255, 204));
        productsTabs.setForeground(new java.awt.Color(51, 51, 51));
        productsTabs.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        kitchenTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Price £", "Price VAT (20%)", "Weight (g)", "Exp Date", "Est Order Date", "Supplier(s)", "Quantity", "Min  Quantity", "Max  Quantity", "Type", "Image"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        kitchenTab.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        kitchenTab.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(kitchenTab);

        productsTabs.addTab("            Kitchen            ", jScrollPane2);

        elecTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Price £", "Price VAT (20%)", "Weight (g)", "Exp Date", "Est Order Date", "Supplier(s)", "Quantity", "Min  Quantity", "Max  Quantity", "Guarantee Duration", "Image"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        elecTab.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        elecTab.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(elecTab);

        productsTabs.addTab("            Electrical            ", jScrollPane3);

        otherTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Price £", "Price VAT (20%)", "Weight (g)", "Exp Date", "Est Order Date", "Supplier(s)", "Quantity", "Min  Quantity", "Max  Quantity", "Image"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        otherTab.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        otherTab.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(otherTab);

        productsTabs.addTab("            Other            ", jScrollPane4);

        foodTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Price £", "Price VAT (20%)", "Weight ", "Exp Date", "Est Order Date", "Supplier(s)", "Quantity", "Min  Quantity", "Max  Quantity", "Use by Date", "Sold By Date", "Image"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        foodTab.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        foodTab.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(foodTab);

        productsTabs.addTab("            Food            ", jScrollPane1);

        jTabbedPane4.addTab("            Shopping            ", productsTabs);

        jLabel3.setText("Username:");

        jLabel4.setText("First Name:");

        jLabel5.setText("Last Name:");

        jLabel6.setText("Date of birth:");

        jLabel7.setText("Gender:");

        jLabel8.setText("Address:");

        jLabel14.setText("Customer Since:");

        customerSince.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customerSince, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(717, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(106, 106, 106)
                                .addComponent(username, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(121, 121, 121)
                                .addComponent(gender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(117, 117, 117)
                                .addComponent(address, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(102, 102, 102)
                                .addComponent(fname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(103, 103, 103)
                                .addComponent(lname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(93, 93, 93)
                                .addComponent(dob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(37, 37, 37))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(gender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(customerSince, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(265, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("          View Account Info          ", jPanel1);

        stockTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Type", "Price per Item", "Number of items", "AddedOn"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        stockTab.setColumnSelectionAllowed(true);
        stockTab.setRowHeight(50);
        stockTab.setRowMargin(5);
        stockTab.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        stockTab.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(stockTab);
        stockTab.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane6)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(83, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("            Stock         ", jPanel2);

        basketTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Type", "Price per Item", "Number of items", "Subtotal", "AddedOn"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        basketTab.setRowHeight(50);
        basketTab.setRowMargin(5);
        basketTab.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        basketTab.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(basketTab);
        basketTab.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (basketTab.getColumnModel().getColumnCount() > 0) {
            basketTab.getColumnModel().getColumn(5).setHeaderValue("Weight");
        }

        removeFromBasketBTN.setBackground(new java.awt.Color(255, 0, 51));
        removeFromBasketBTN.setForeground(new java.awt.Color(255, 255, 255));
        removeFromBasketBTN.setText("Remove");
        removeFromBasketBTN.setEnabled(false);
        removeFromBasketBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeFromBasketBTNActionPerformed(evt);
            }
        });

        editBasketNbItemsBTN.setBackground(new java.awt.Color(0, 51, 204));
        editBasketNbItemsBTN.setForeground(new java.awt.Color(255, 255, 255));
        editBasketNbItemsBTN.setText("Edit");
        editBasketNbItemsBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBasketNbItemsBTNActionPerformed(evt);
            }
        });

        jLabel9.setText("Number of Items:");

        checkOutBTN.setBackground(new java.awt.Color(0, 204, 0));
        checkOutBTN.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        checkOutBTN.setForeground(new java.awt.Color(255, 255, 255));
        checkOutBTN.setText("Proceed to Checkout");
        checkOutBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkOutBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5)
                .addGap(25, 25, 25))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(removeFromBasketBTN)
                        .addGap(282, 282, 282)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(newNbItems, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(editBasketNbItemsBTN))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(417, 417, 417)
                        .addComponent(checkOutBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(426, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(removeFromBasketBTN)
                    .addComponent(editBasketNbItemsBTN)
                    .addComponent(jLabel9)
                    .addComponent(newNbItems, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(checkOutBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        removeFromBasketBTN.setVisible(false);

        jTabbedPane4.addTab("          Basket        ", jPanel3);

        jLabel2.setText("Quantity");

        addToBasket.setText("ADD TO BASKET");
        addToBasket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToBasketActionPerformed(evt);
            }
        });

        addToWishlist.setText("ADD TO WISHLIST");
        addToWishlist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToWishlistActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jTabbedPane4)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addComponent(addToWishlist)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(addToBasket)
                .addGap(128, 128, 128))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addToBasket)
                    .addComponent(addToWishlist))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        jTabbedPane4.getAccessibleContext().setAccessibleName("");
        jTabbedPane4.getAccessibleContext().setAccessibleDescription("");
        addToWishlist.setVisible(false);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addToBasketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addToBasketActionPerformed
        // TODO add your handling code here:
        String Q = quantity.getText().trim();
        if (Q.length() == 0 || !isStringInt(Q) || Integer.parseInt(Q) < 1) {
            JOptionPane.showMessageDialog(this, "Enter a valid Quantity.", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            int rowF = foodTab.getSelectedRow();
            int rowK = kitchenTab.getSelectedRow();
            int rowE = elecTab.getSelectedRow();
            int rowO = otherTab.getSelectedRow();
            if (rowF == -1 && rowK == -1 && rowE == -1 && rowO == -1) {
                JOptionPane.showMessageDialog(this, "Select a product to add.", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                int nbItems = Integer.parseInt(Q);
                if (rowF > -1) {
                    addToBasket(rowF, nbItems, "food", foodTab);
                } else if (rowK > -1) {
                    addToBasket(rowK, nbItems, "kitchen", kitchenTab);
                } else if (rowE > -1) {
                    addToBasket(rowE, nbItems, "electrical", elecTab);
                } else if (rowO > -1) {
                    addToBasket(rowO, nbItems, "other", otherTab);
                }
            }
        }
    }//GEN-LAST:event_addToBasketActionPerformed

    public void addToBasket(int row, int nbItems, String type, JTable tab) {
        String ID = String.valueOf(tab.getValueAt(row, 0));
        int q = products.getProduct(ID).getNbItems();
        if (q == 0) {
            JOptionPane.showMessageDialog(this, "Selected product is out of stock.", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else if (nbItems > q) {
            JOptionPane.showMessageDialog(this, "Selected product have only " + q + " items left in stock.", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            String name = String.valueOf(tab.getValueAt(row, 1));
            int price = Integer.parseInt(String.valueOf(tab.getValueAt(row, 2)));
            String weight = String.valueOf(tab.getValueAt(row, 4));
            String now = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date(System.currentTimeMillis()));
            
            // product exists in the basket
            boolean productExistedInBasket = false;
            if (basket.getProduct(ID) == null) {
                addToBasketTab(ID, name, type, price, nbItems, now);
            } else {
                basketTab.setValueAt(basket.getProduct(ID).getNbItems() + nbItems, basket.getProductNb(ID), 4);
                productExistedInBasket = true;
            }
            basket.addProduct(ID, name, type, price, nbItems, weight, now);
            products.setNbItems(ID, q - nbItems);
            tab.setValueAt(q - nbItems, row, 8);
            
            // Write to the basket storage file of the login user
            saveBasketToFile(basket, productExistedInBasket);
            
            // Save the updated products information
            
            
            JOptionPane.showMessageDialog(this, "Added " + nbItems + " items of " + name + " to basket");
        }
    }
    
    /**
     * Implementation - 
     * Saves the basket information to the corresponding file. 
     */
    private void saveBasketToFile(Basket basket, boolean productExistedInBasket) {
        try {
            FileWriter fw = new FileWriter("data/baskets/" + uName + ".txt", productExistedInBasket ? true : false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter writer = new PrintWriter(bw);
            String stock = "";
            for (Basket.Product p : basket.getProducts()) {
                stock += p.toString() + "\n";
            }
            writer.print(stock);
            writer.close();
        } catch (IOException ioe) {
            
        }
    }

    public void addToWishlist(int row, JTable tab, String type) {
        String ID = String.valueOf(tab.getValueAt(row, 0));

        if (wishlist.getProduct(ID) == null) {
            String name = String.valueOf(tab.getValueAt(row, 1));
            int price = Integer.parseInt(String.valueOf(tab.getValueAt(row, 2)));
            String weight = String.valueOf(tab.getValueAt(row, 4));
            String now = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date(System.currentTimeMillis()));
            wishlist.addProduct(ID, name, type, price, 0, weight, now);
        } else {
            JOptionPane.showMessageDialog(this, "Product already in the wishlist.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeFromBasketBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeFromBasketBTNActionPerformed
        // TODO add your handling code here:
        int row = basketTab.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select product to remove.", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            DefaultTableModel model = (DefaultTableModel) basketTab.getModel();
            basket.removeRow(row);
            model.removeRow(row);
            String id = String.valueOf(model.getValueAt(row, 0));
            products.setNbItems(id, basket.getProduct(id).getNbItems()
                    - Integer.parseInt(String.valueOf(model.getValueAt(row, 4))));
        }
    }//GEN-LAST:event_removeFromBasketBTNActionPerformed

    private void editBasketNbItemsBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBasketNbItemsBTNActionPerformed
        // TODO add your handling code here:
        int row = basketTab.getSelectedRow();
        String nbItems = newNbItems.getText().trim();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a product to edit.", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else if (nbItems.length() == 0 || !isStringInt(nbItems)) {
            JOptionPane.showMessageDialog(this, "Enter a valid number of items.", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            String ID = String.valueOf(basketTab.getValueAt(row, 0));
            basketTab.setValueAt(nbItems, row, 4);
            basket.editNbItems(ID, Integer.parseInt(nbItems));
            basketTab.validate();
        }
    }//GEN-LAST:event_editBasketNbItemsBTNActionPerformed

    private void checkOutBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkOutBTNActionPerformed
        // TODO add your handling code here:
        if (basket.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Basket Is Empty!", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            this.dispose();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {                    
                    new Checkout(basket, products, uName).setVisible(true);
                }
            });
        }
    }//GEN-LAST:event_checkOutBTNActionPerformed

    private void addToWishlistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addToWishlistActionPerformed
        // TODO add your handling code here:
        int rowF = foodTab.getSelectedRow();
        int rowK = kitchenTab.getSelectedRow();
        int rowE = elecTab.getSelectedRow();
        int rowO = otherTab.getSelectedRow();
        if (rowF > -1) {
            addToWishlist(rowF, foodTab, "food");
        } else if (rowK > -1) {
            addToWishlist(rowK, kitchenTab, "kitchen");
        } else if (rowE > -1) {
            addToWishlist(rowE, elecTab, "electrical");
        } else if (rowO > -1) {
            addToWishlist(rowO, otherTab, "other");
        } else {
            JOptionPane.showMessageDialog(this, "Select a product to add to WISHLIST.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_addToWishlistActionPerformed

    public void addToBasketTab(String ID, String name, String type, int price, int nbItems, String date) {
        DefaultTableModel model = (DefaultTableModel) basketTab.getModel();
        model.addRow(new Object[]{ID, name, type, price, nbItems, price * nbItems, date});
    }

    public boolean isStringInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addToBasket;
    private javax.swing.JButton addToWishlist;
    private javax.swing.JLabel address;
    private javax.swing.JTable basketTab;
    private javax.swing.JButton checkOutBTN;
    private javax.swing.JLabel customerSince;
    private javax.swing.JLabel dob;
    private javax.swing.JButton editBasketNbItemsBTN;
    private javax.swing.JTable elecTab;
    private javax.swing.JLabel fname;
    private javax.swing.JTable foodTab;
    private javax.swing.JLabel gender;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTable kitchenTab;
    private javax.swing.JLabel lname;
    private javax.swing.JTextField newNbItems;
    private javax.swing.JTable otherTab;
    private javax.swing.JTabbedPane productsTabs;
    private javax.swing.JTextField quantity;
    private javax.swing.JButton removeFromBasketBTN;
    private javax.swing.JTable stockTab;
    private javax.swing.JLabel username;
    // End of variables declaration//GEN-END:variables
}
