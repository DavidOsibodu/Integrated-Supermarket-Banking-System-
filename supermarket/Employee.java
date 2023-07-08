package supermarket;

import java.awt.Component;
import java.awt.Image;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
public class Employee extends javax.swing.JFrame implements ConfirmBtnActionListener {

    /**
     * Creates new form Admin
     */
    File image = null;
    ProductList products = new ProductList();
    
    @Override
    public void onClicked() {
        System.err.println("Confirm btn clicked");
        int row = ordersTab.getSelectedRow();
        String Q = reorderQ.getText().trim();
        
        Product p = products.getProduct((String) ordersTab.getValueAt(row, 0));
        int newQ = Integer.parseInt(Q) + p.getNbItems();
        
        String type = (String) ordersTab.getValueAt(row, 1);
        if (type.equals("food")) {
            changeTable(foodTab, 8, String.valueOf(newQ), p.getID());
        } else if (type.equals("electrical")) {
            changeTable(elecTab, 8, String.valueOf(newQ), p.getID());
        } else if (type.equals("kitchen")) {
            changeTable(kitchenTab, 8, String.valueOf(newQ), p.getID());
        } else {
            changeTable(otherTab, 8, String.valueOf(newQ), p.getID());
        }
        
        products.saveToFile();
        jLabel75.setText("Order will be restocked in 3-5 working days");
    }

    public Employee() {
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);

        fillOrdersTable();

        Supplier Tesco = new Supplier("Tesco", "London", "05/01/2020");
        jLabel77.setText(jLabel77.getText() + "   " + Tesco.getName());
        jLabel78.setText(jLabel78.getText() + "   " + Tesco.getAddress());
        jLabel79.setText(jLabel79.getText() + "   " + Tesco.getSupplierSince());

        // load products from file
        addProductPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                image = null;
            }
        });
        productsTabs.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                foodTab.clearSelection();
                kitchenTab.clearSelection();
                elecTab.clearSelection();
                otherTab.clearSelection();
            }
        });
        try {
            File inputFile = new File("data/products.txt");
            Scanner inputReader = new Scanner(inputFile);
            int i = 0;
            while (inputReader.hasNextLine()) {
                String[] p = inputReader.nextLine().split(" -- ");
                appendProductRow(p);
                if (p[1].equals("food")) {
                    products.addProduct(new Product(p[0], p[1], p[2], p[3], p[5],
                            p[6], p[7], p[8], Integer.parseInt(p[9]), p[10], p[11],
                            p[12], p[13], p[14], "", "", p[15]));
                } else if (p[1].equals("electrical")) {
                    products.addProduct(new Product(p[0], p[1], p[2], p[3], p[5],
                            p[6], p[7], p[8], Integer.parseInt(p[9]), p[10], p[11],
                            "", "", p[13], "", p[12], p[14]));
                } else if (p[1].equals("kitchen")) {
                    products.addProduct(new Product(p[0], p[1], p[2], p[3], p[5],
                            p[6], p[7], p[8], Integer.parseInt(p[9]), p[10], p[11],
                            "", "", p[13], p[12], "", p[14]));
                } else {
                    products.addProduct(new Product(p[0], p[1], p[2], p[3], p[5],
                            p[6], p[7], p[8], Integer.parseInt(p[9]), p[10], p[11],
                            "", "", p[12], "", "", p[13]));
                }

            }
            inputReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
        }

        //END OF CONTRUCTOR
    }
    
    public void appendSupplyRow(String[]  p) {
        DefaultTableModel model = (DefaultTableModel) ordersTab.getModel();
        model.addRow(new Object[] {p[0], p[1], p[2], p[3]});
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

    public void fillOrdersTable() {
        try {
            File inputFile = new File("data/supplies.txt");
            Scanner inputReader = new Scanner(inputFile);

            while (inputReader.hasNextLine()) {
                String line = inputReader.nextLine();
                System.out.println(line);
                String[] p = line.split(" -- ");
                DefaultTableModel model = (DefaultTableModel) ordersTab.getModel();
                model.addRow(new Object[]{p[0], p[1], p[2], p[3]});
            }
        } catch (Exception e) {

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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        addProductPane = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        ID = new javax.swing.JTextField();
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
        addProductBtn = new javax.swing.JButton();
        errMsg = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        noItems = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel45 = new javax.swing.JLabel();
        weightCateg = new javax.swing.JComboBox<>();
        usebydate = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        soldbydate = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        ID1 = new javax.swing.JTextField();
        name1 = new javax.swing.JTextField();
        weight1 = new javax.swing.JTextField();
        price1 = new javax.swing.JTextField();
        expDate1 = new javax.swing.JTextField();
        estOrderDate1 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        supplier1 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        max1 = new javax.swing.JTextField();
        min1 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        addProductBtn1 = new javax.swing.JButton();
        errMsg1 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        noItems1 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel61 = new javax.swing.JLabel();
        prodType = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        ID2 = new javax.swing.JTextField();
        name2 = new javax.swing.JTextField();
        weight2 = new javax.swing.JTextField();
        price2 = new javax.swing.JTextField();
        expDate2 = new javax.swing.JTextField();
        estOrderDate2 = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        supplier2 = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        max2 = new javax.swing.JTextField();
        min2 = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        addProductBtn2 = new javax.swing.JButton();
        errMsg2 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        noItems2 = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel60 = new javax.swing.JLabel();
        guaranteeDuration = new javax.swing.JTextField();
        guarantDays = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        ID3 = new javax.swing.JTextField();
        name3 = new javax.swing.JTextField();
        weight3 = new javax.swing.JTextField();
        price3 = new javax.swing.JTextField();
        expDate3 = new javax.swing.JTextField();
        estOrderDate3 = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        supplier3 = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        max3 = new javax.swing.JTextField();
        min3 = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        addProductBtn3 = new javax.swing.JButton();
        errMsg3 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        noItems3 = new javax.swing.JTextField();
        jLabel73 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        productsTabs = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        foodTab = new javax.swing.JTable();
        editFood = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        kitchenTab = new javax.swing.JTable();
        editKitchen = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        elecTab = new javax.swing.JTable();
        editElect = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        otherTab = new javax.swing.JTable();
        editOther = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel74 = new javax.swing.JLabel();
        reorderProductBtn = new javax.swing.JButton();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        ordersTab = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        reorderPriceLabel = new javax.swing.JLabel();
        reorderQ = new javax.swing.JTextField();
        reorderErrMsg = new javax.swing.JLabel();
        plusBtnReorder = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SUPERMARKET ADMINISTRATION");

        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        addProductPane.setBackground(new java.awt.Color(102, 255, 153));
        addProductPane.setForeground(new java.awt.Color(51, 51, 51));
        addProductPane.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel2.setText("Product ID");

        jLabel3.setText("Name");

        jLabel4.setText("Price");

        jLabel5.setText("Expiry Date");

        jLabel6.setText("Weight");

        jLabel7.setText("Estimated Date to order");

        jLabel8.setText("Supplier(s)");

        jLabel9.setText("Quantity in stock");

        jLabel10.setText("MIN");

        jLabel11.setText("MAX");

        addProductBtn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        addProductBtn.setText("ADD PRODUCT");
        addProductBtn.setToolTipText("");
        addProductBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductBtnActionPerformed(evt);
            }
        });

        errMsg.setForeground(new java.awt.Color(255, 0, 0));
        errMsg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel12.setText("N° of items");

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel13.setText("Image");

        jButton2.setText("Browse for image");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel45.setText("Weight Category");

        weightCateg.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "g", "lb", "kg", "L", "cL" }));

        jLabel46.setText("Use by Date");

        jLabel47.setText("Sold by Date");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel2)
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
                                        .addComponent(max, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(noItems, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(estOrderDate, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ID, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(name, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(weight, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(price, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(expDate, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(supplier, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                                        .addGap(44, 44, 44)
                                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel45)
                                            .addComponent(jLabel46)
                                            .addComponent(jLabel47))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(soldbydate)
                                            .addComponent(usebydate, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                            .addGroup(jPanel9Layout.createSequentialGroup()
                                                .addComponent(weightCateg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))))))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addGap(54, 54, 54)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(48, 48, 48)
                                        .addComponent(jButton2))
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addGap(34, 34, 34)
                                        .addComponent(errMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(addProductBtn)))
                .addGap(670, 670, 670))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(weight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel45)
                                        .addComponent(weightCateg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel5)
                                        .addComponent(expDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(usebydate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel46))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(estOrderDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addComponent(soldbydate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47))
                .addGap(21, 21, 21)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(supplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(max, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(min, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(23, 23, 23)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(noItems, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addProductBtn)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
        );

        addProductPane.addTab("            Food            ", jPanel6);

        jLabel33.setText("Product ID");

        jLabel34.setText("Name");

        jLabel35.setText("Price");

        jLabel36.setText("Expiry Date");

        jLabel37.setText("Weight");

        jLabel38.setText("Estimated Date to order");

        jLabel39.setText("Supplier(s)");

        jLabel40.setText("Quantity in stock");

        jLabel41.setText("MIN");

        jLabel42.setText("MAX");

        addProductBtn1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        addProductBtn1.setText("ADD PRODUCT");
        addProductBtn1.setToolTipText("");
        addProductBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductBtn1ActionPerformed(evt);
            }
        });

        errMsg1.setForeground(new java.awt.Color(255, 0, 0));
        errMsg1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel43.setText("N° of items");

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel44.setText("Image");

        jButton4.setText("Browse for image");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel61.setText("Type ");

        prodType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Electrical", "Crockery" }));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(errMsg1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel39)
                                    .addComponent(jLabel33)
                                    .addComponent(jLabel34)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel37)
                                    .addComponent(jLabel36)
                                    .addComponent(jLabel38)
                                    .addComponent(jLabel40)
                                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(48, 48, 48)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jLabel41)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(min1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(34, 34, 34)
                                        .addComponent(jLabel42)
                                        .addGap(3, 3, 3)
                                        .addComponent(max1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton4)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(noItems1, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(estOrderDate1, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ID1, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(name1, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(weight1, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(price1, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(expDate1, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(supplier1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                                        .addGap(82, 82, 82)
                                        .addComponent(jLabel61)
                                        .addGap(18, 18, 18)
                                        .addComponent(prodType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(addProductBtn1)))
                .addContainerGap(664, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel33)
                    .addComponent(ID1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel61)
                        .addComponent(prodType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel34)
                    .addComponent(name1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel35)
                    .addComponent(price1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel37)
                    .addComponent(weight1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel36)
                    .addComponent(expDate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(estOrderDate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38))
                .addGap(21, 21, 21)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(supplier1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(max1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(min1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41)
                    .addComponent(jLabel42))
                .addGap(23, 23, 23)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(noItems1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errMsg1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addProductBtn1)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 43, Short.MAX_VALUE))
        );

        addProductPane.addTab("            kitchen            ", jPanel8);

        jLabel48.setText("Product ID");

        jLabel49.setText("Name");

        jLabel50.setText("Price");

        jLabel51.setText("Expiry Date");

        jLabel52.setText("Weight");

        jLabel53.setText("Estimated Date to order");

        jLabel54.setText("Supplier(s)");

        jLabel55.setText("Quantity in stock");

        jLabel56.setText("MIN");

        jLabel57.setText("MAX");

        addProductBtn2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        addProductBtn2.setText("ADD PRODUCT");
        addProductBtn2.setToolTipText("");
        addProductBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductBtn2ActionPerformed(evt);
            }
        });

        errMsg2.setForeground(new java.awt.Color(255, 0, 0));
        errMsg2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel58.setText("N° of items");

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel59.setText("Image");

        jButton5.setText("Browse for image");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel60.setText("Guarantee Duration");

        guaranteeDuration.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        guarantDays.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Days", "Months", "Years" }));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(addProductBtn2))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(errMsg2, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel54)
                                    .addComponent(jLabel48)
                                    .addComponent(jLabel49)
                                    .addComponent(jLabel50)
                                    .addComponent(jLabel52)
                                    .addComponent(jLabel51)
                                    .addComponent(jLabel53)
                                    .addComponent(jLabel55)
                                    .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(48, 48, 48)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addComponent(jLabel56)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(min2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(34, 34, 34)
                                        .addComponent(jLabel57)
                                        .addGap(3, 3, 3)
                                        .addComponent(max2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton5)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(noItems2, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(estOrderDate2, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ID2, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(name2, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(weight2, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(price2, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(expDate2, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(supplier2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                                        .addGap(57, 57, 57)
                                        .addComponent(jLabel60)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(guaranteeDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(guarantDays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(553, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel48)
                    .addComponent(ID2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(guaranteeDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel60)
                        .addComponent(guarantDays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel49)
                    .addComponent(name2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel50)
                    .addComponent(price2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel52)
                    .addComponent(weight2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel51)
                    .addComponent(expDate2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(estOrderDate2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53))
                .addGap(21, 21, 21)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(supplier2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(max2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(min2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56)
                    .addComponent(jLabel57))
                .addGap(23, 23, 23)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(noItems2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errMsg2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addProductBtn2)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
        );

        addProductPane.addTab("            Electrical            ", jPanel7);

        jLabel62.setText("Product ID");

        jLabel63.setText("Name");

        jLabel64.setText("Price");

        jLabel65.setText("Expiry Date");

        jLabel66.setText("Weight");

        jLabel67.setText("Estimated Date to order");

        ID3.setText("tissue");

        name3.setText("tissue");

        weight3.setText("200");

        price3.setText("10");

        expDate3.setText("12/01/2021");

        estOrderDate3.setText("12/01/2021");

        jLabel68.setText("Supplier(s)");

        supplier3.setText("Tesco");

        jLabel69.setText("Quantity in stock");

        max3.setText("30");

        min3.setText("20");

        jLabel70.setText("MIN");

        jLabel71.setText("MAX");

        addProductBtn3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        addProductBtn3.setText("ADD PRODUCT");
        addProductBtn3.setToolTipText("");
        addProductBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductBtn3ActionPerformed(evt);
            }
        });

        errMsg3.setForeground(new java.awt.Color(255, 0, 0));
        errMsg3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel72.setText("N° of items");

        noItems3.setText("25");

        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel73.setText("Image");

        jButton6.setText("Browse for image");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(errMsg3, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel68)
                                    .addComponent(jLabel62)
                                    .addComponent(jLabel63)
                                    .addComponent(jLabel64)
                                    .addComponent(jLabel66)
                                    .addComponent(jLabel65)
                                    .addComponent(jLabel67)
                                    .addComponent(jLabel69)
                                    .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(48, 48, 48)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addComponent(jLabel70)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(min3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(34, 34, 34)
                                        .addComponent(jLabel71)
                                        .addGap(3, 3, 3)
                                        .addComponent(max3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton6)
                                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(noItems3, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(estOrderDate3, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(ID3, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(name3, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(weight3, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(price3, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(expDate3, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(supplier3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))))))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(addProductBtn3)))
                .addContainerGap(796, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel62)
                    .addComponent(ID3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel63)
                    .addComponent(name3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel64)
                    .addComponent(price3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel66)
                    .addComponent(weight3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel65)
                    .addComponent(expDate3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(estOrderDate3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel67))
                .addGap(21, 21, 21)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel68)
                    .addComponent(supplier3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel69)
                    .addComponent(max3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(min3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel70)
                    .addComponent(jLabel71))
                .addGap(23, 23, 23)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel72)
                    .addComponent(noItems3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel73)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errMsg3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addProductBtn3)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 43, Short.MAX_VALUE))
        );

        addProductPane.addTab("            Other            ", jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1093, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(addProductPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1093, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 576, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(addProductPane, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Add New Product", jPanel1);

        productsTabs.setBackground(new java.awt.Color(153, 255, 204));
        productsTabs.setForeground(new java.awt.Color(51, 51, 51));
        productsTabs.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

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
        foodTab.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(foodTab);

        editFood.setText("Edit Product");
        editFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editFoodActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(editFood)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1067, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(editFood)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
                .addContainerGap())
        );

        productsTabs.addTab("            Food            ", jPanel4);

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
        kitchenTab.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(kitchenTab);

        editKitchen.setText("Edit Product");
        editKitchen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editKitchenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(editKitchen, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1061, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(editKitchen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2)
                .addGap(68, 68, 68))
        );

        productsTabs.addTab("            kitchen            ", jPanel5);

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
        elecTab.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(elecTab);

        editElect.setText("Edit product");
        editElect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editElectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1075, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(editElect)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(editElect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3)
                .addGap(66, 66, 66))
        );

        productsTabs.addTab("            Electrical            ", jPanel16);

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
        otherTab.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(otherTab);

        editOther.setText("Edit Product");
        editOther.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editOtherActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(editOther)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1081, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(editOther)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4)
                .addGap(65, 65, 65))
        );

        productsTabs.addTab("            Other            ", jPanel15);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(productsTabs)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(productsTabs)
                .addContainerGap())
        );

        jTabbedPane1.addTab("List of products", jPanel3);

        jPanel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel13.setLayout(null);

        jLabel74.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel74.setText("Supplier: TESCO");
        jPanel13.add(jLabel74);
        jLabel74.setBounds(313, 21, 182, 32);

        reorderProductBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        reorderProductBtn.setText("Re-order product");
        reorderProductBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reorderProductBtnActionPerformed(evt);
            }
        });
        jPanel13.add(reorderProductBtn);
        reorderProductBtn.setBounds(680, 280, 160, 26);

        jLabel75.setForeground(new java.awt.Color(0, 153, 0));
        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel13.add(jLabel75);
        jLabel75.setBounds(580, 310, 340, 16);

        jLabel76.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel76.setText("Supplier Details:");
        jPanel13.add(jLabel76);
        jLabel76.setBounds(580, 370, 240, 25);

        jLabel77.setText("Name:                     ");
        jPanel13.add(jLabel77);
        jLabel77.setBounds(580, 420, 350, 16);

        jLabel78.setText("Address:                  ");
        jPanel13.add(jLabel78);
        jLabel78.setBounds(580, 450, 360, 16);

        jLabel79.setText("Supplier Since:       ");
        jPanel13.add(jLabel79);
        jLabel79.setBounds(580, 480, 380, 16);

        ordersTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Product Type", "Product Name", "Ordering Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ordersTab.setCellSelectionEnabled(false);
        ordersTab.setRowHeight(50);
        ordersTab.setRowMargin(5);
        ordersTab.setRowSelectionAllowed(true);
        ordersTab.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ordersTab.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ordersTab.setShowGrid(true);
        ordersTab.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(ordersTab);

        jPanel13.add(jScrollPane5);
        jScrollPane5.setBounds(30, 110, 520, 402);

        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel80.setText("Quantity: ");

        jLabel81.setText("Total Re-ordering Price:");

        reorderPriceLabel.setText(" ");

        reorderQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reorderQActionPerformed(evt);
            }
        });

        reorderErrMsg.setForeground(new java.awt.Color(255, 0, 0));
        reorderErrMsg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        plusBtnReorder.setText("+");
        plusBtnReorder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plusBtnReorderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel81)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(reorderPriceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel80)
                        .addGap(18, 18, 18)
                        .addComponent(reorderQ, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(plusBtnReorder)
                        .addGap(0, 57, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(reorderErrMsg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel80)
                    .addComponent(reorderQ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(plusBtnReorder))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel81)
                    .addComponent(reorderPriceLabel))
                .addGap(18, 18, 18)
                .addComponent(reorderErrMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.add(jPanel14);
        jPanel14.setBounds(610, 140, 300, 120);

        jTabbedPane1.addTab("     Re-Ordering Products   ", jPanel13);

        jMenu1.setText("File");

        jMenuItem1.setText("Import Product");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public boolean productExists(String ID) {
        try {
            File inputFile = new File("data/products.txt");
            Scanner inputReader = new Scanner(inputFile);
            int i = 0;
            while (inputReader.hasNextLine()) {
                if (i == 0) {
                    i++;
                    continue;
                }
                String data = inputReader.nextLine();
                String[] user = data.split(" -- ");
                if (user[0].equals(ID)) {
                    return true;
                }
            }
            inputReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
            return true;
        }
        return false;
    }

    public void appendProductRow(String[] p, String type) {
        JLabel imgLabel = new JLabel();

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

    public void appendProduct(String details) {
        try {
            FileWriter fw = new FileWriter("data/products.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter writer = new PrintWriter(bw);
            writer.println(details);
            writer.close();
            JOptionPane.showMessageDialog(this, "New product added!");
            appendProductRow(details.split(" -- "), details.split(" -- ")[1]);
            this.image = null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An Error Occured", null, JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void appendSupply(String details) {
        try {
            FileWriter fw = new FileWriter("data/supplies.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter writer = new PrintWriter(bw);
            writer.println(details);
            writer.close();
            appendSupplyRow(details.split(" -- "));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error Occured", null, JOptionPane.ERROR_MESSAGE);
        }
    }

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

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String fext = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
            System.out.println(fext);
            if (fext.equals("txt")) {

                try {
                    Scanner sc = new Scanner(file);
                    if (sc.hasNext()) {
                        String[] product = sc.nextLine().split(" -- ");
                        if (product.length != Product.NB_ATTRS) {
                            JOptionPane.showMessageDialog(this, "The data in the file is not a valid product data.", "ERROR", JOptionPane.ERROR_MESSAGE);
                        } else {
                            if (productExists(product[0])) {
                                JOptionPane.showMessageDialog(this, "A product already exists with ID: \"" + product[0] + "\"", "ERROR", JOptionPane.ERROR_MESSAGE);
                            } else {
                                appendProduct(product[0] + " -- other -- " + product[1] + " -- " + product[2]
                                        + " -- " + product[3] + " -- " + product[4] + " -- " + product[5]
                                        + " -- " + product[6] + " -- " + product[7] + " -- " + product[8]
                                        + " -- " + product[9] + " -- " + product[9] + " -- " + product[9]);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "The file is empty", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "File not found", "ERROR", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Select a .txt file", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No file was selected.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        browseForImage();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void addProductBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductBtnActionPerformed
        // TODO add your handling code here:
        errMsg.setText("");
        if (ID.getText().trim().length() == 0) {
            errMsg.setText("Enter Product ID");
        } else if (productExists(ID.getText())) {
            errMsg.setText("Product ID already exists");
        } else if (name.getText().trim().length() == 0) {
            errMsg.setText("Enter Product Name");
        } else if (price.getText().trim().length() == 0) {
            errMsg.setText("Enter Product Price");
        } else if (!isStringInt(price.getText().trim())) {
            errMsg.setText("Price Must be a number");
        } else if (weight.getText().trim().length() == 0) {
            errMsg.setText("Enter Product Weight");
        } else if (!isStringInt(weight.getText().trim())) {
            errMsg.setText("Weight Must be a number");
        } else if (expDate.getText().trim().length() == 0) {
            errMsg.setText("Enter Expiry Date");
        } else if (!isValidDateStr(expDate.getText().trim())) {
            errMsg.setText("Expiry Date format is invalid");
        } else if (usebydate.getText().trim().length() == 0) {
            errMsg.setText("Enter Use by Date");
        } else if (soldbydate.getText().trim().length() == 0) {
            errMsg.setText("Enter Sold by Date");
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
            String imgPath = "images/" + ID.getText() + image.getName().substring(image.getName().lastIndexOf("."), image.getName().length());
            try {
                File source = new File(image.getPath());
                File dest = new File(imgPath);
                copyFileUsingStream(source, dest);
            } catch (Exception e) {

            }

            appendProduct(ID.getText() + " -- food -- " + name.getText() + " -- " + price.getText()
                    + " -- " + Integer.parseInt(price.getText()) * 1.2 + " -- " + weight.getText()
                    + (String) weightCateg.getSelectedItem()
                    + " -- " + expDate.getText() + " -- " + estOrderDate.getText() + " -- "
                    + supplier.getText() + " -- " + noItems.getText()
                    + " -- " + min.getText() + " -- " + max.getText() + " -- " + soldbydate.getText().trim()
                    + " -- " + soldbydate.getText().trim() + " -- " + imgPath + " -- "
                    + new Date().toString()
            );
            
            appendSupply(ID.getText() + " -- food -- " + name.getText() + " -- " + price.getText());
        }
    }//GEN-LAST:event_addProductBtnActionPerformed

    private void addProductBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductBtn1ActionPerformed
        // TODO add your handling code here:
        errMsg1.setText("");
        if (ID1.getText().trim().length() == 0) {
            errMsg1.setText("Enter Product ID");
        } else if (productExists(ID1.getText())) {
            errMsg1.setText("Product ID already exists");
        } else if (name1.getText().trim().length() == 0) {
            errMsg1.setText("Enter Product Name");
        } else if (price1.getText().trim().length() == 0) {
            errMsg1.setText("Enter Product Price");
        } else if (!isStringInt(price1.getText().trim())) {
            errMsg1.setText("Price Must be a number");
        } else if (weight1.getText().trim().length() == 0) {
            errMsg1.setText("Enter Product Weight");
        } else if (!isStringInt(weight1.getText().trim())) {
            errMsg1.setText("Weight Must be a number");
        } else if (expDate1.getText().trim().length() == 0) {
            errMsg1.setText("Enter Expiry Date");
        } else if (!isValidDateStr(expDate1.getText().trim())) {
            errMsg1.setText("Expiry Date format is invalid");
        } else if (estOrderDate1.getText().trim().length() == 0) {
            errMsg1.setText("Enter Estimated Order Date");
        } else if (!isValidDateStr(estOrderDate1.getText().trim())) {
            errMsg1.setText("Order Date format is invalid");
        } else if (supplier1.getText().trim().length() == 0) {
            errMsg1.setText("Enter Supplier(s)");
        } else if (min1.getText().trim().length() == 0) {
            errMsg1.setText("Enter Min Quantity");
        } else if (!isStringInt(min1.getText().trim())) {
            errMsg1.setText("Min Quantity must be a number");
        } else if (max1.getText().trim().length() == 0) {
            errMsg1.setText("Enter Max Quantity");
        } else if (!isStringInt(max1.getText().trim())) {
            errMsg1.setText("Max Quantity must be a number");
        } else if (Integer.parseInt(min1.getText().trim()) > Integer.parseInt(min1.getText().trim())) {
            errMsg1.setText("Max Quantity can't be lower than Min");
        } else if (noItems1.getText().trim().length() == 0) {
            errMsg1.setText("Enter Number of Items");
        } else if (!isStringInt(noItems1.getText().trim())) {
            errMsg1.setText("Number of items must be a number");
        } else if (this.image == null) {
            errMsg1.setText("Select an image");
        } else {
            String imgPath = "images/" + ID1.getText() + image.getName().substring(image.getName().lastIndexOf("."), image.getName().length());
            try {
                File source = new File(image.getPath());
                File dest = new File(imgPath);
                copyFileUsingStream(source, dest);
            } catch (Exception e) {

            }

            appendProduct(ID1.getText() + " -- kitchen -- " + name1.getText() + " -- " + price1.getText()
                    + " -- " + Integer.parseInt(price1.getText()) * 1.2 + " -- " + weight1.getText()
                    + " -- " + expDate1.getText() + " -- " + estOrderDate1.getText() + " -- "
                    + supplier1.getText() + " -- " + noItems1.getText()
                    + " -- " + min1.getText() + " -- " + max1.getText() + " -- " + (String) prodType.getSelectedItem() + " -- " + imgPath + " -- "
                    + new Date().toString());
            
            appendSupply(ID.getText() + " -- kitchen -- " + name.getText() + " -- " + price.getText());
        }
    }//GEN-LAST:event_addProductBtn1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        browseForImage();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void addProductBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductBtn2ActionPerformed
        // TODO add your handling code here:
        errMsg2.setText("");
        if (ID2.getText().trim().length() == 0) {
            errMsg2.setText("Enter Product ID");
        } else if (productExists(ID2.getText())) {
            errMsg2.setText("Product ID already exists");
        } else if (guaranteeDuration.getText().length() == 0) {
            errMsg2.setText("Enter Guarantee Durantion");
        } else if (name2.getText().trim().length() == 0) {
            errMsg2.setText("Enter Product Name");
        } else if (price2.getText().trim().length() == 0) {
            errMsg2.setText("Enter Product Price");
        } else if (!isStringInt(price2.getText().trim())) {
            errMsg2.setText("Price Must be a number");
        } else if (weight2.getText().trim().length() == 0) {
            errMsg2.setText("Enter Product Weight");
        } else if (!isStringInt(weight2.getText().trim())) {
            errMsg2.setText("Weight Must be a number");
        } else if (expDate2.getText().trim().length() == 0) {
            errMsg2.setText("Enter Expiry Date");
        } else if (!isValidDateStr(expDate2.getText().trim())) {
            errMsg2.setText("Expiry Date format is invalid");
        } else if (estOrderDate2.getText().trim().length() == 0) {
            errMsg2.setText("Enter Estimated Order Date");
        } else if (!isValidDateStr(estOrderDate2.getText().trim())) {
            errMsg2.setText("Order Date format is invalid");
        } else if (supplier2.getText().trim().length() == 0) {
            errMsg2.setText("Enter Supplier(s)");
        } else if (min2.getText().trim().length() == 0) {
            errMsg2.setText("Enter Min Quantity");
        } else if (!isStringInt(min2.getText().trim())) {
            errMsg2.setText("Min Quantity must be a number");
        } else if (max2.getText().trim().length() == 0) {
            errMsg2.setText("Enter Max Quantity");
        } else if (!isStringInt(max2.getText().trim())) {
            errMsg2.setText("Max Quantity must be a number");
        } else if (Integer.parseInt(min2.getText().trim()) > Integer.parseInt(min2.getText().trim())) {
            errMsg2.setText("Max Quantity can't be lower than Min");
        } else if (noItems2.getText().trim().length() == 0) {
            errMsg2.setText("Enter Number of Items");
        } else if (!isStringInt(noItems2.getText().trim())) {
            errMsg2.setText("Number of items must be a number");
        } else if (this.image == null) {
            errMsg2.setText("Select an image");
        } else {
            String imgPath = "images/" + ID2.getText() + image.getName().substring(image.getName().lastIndexOf("."), image.getName().length());
            try {
                File source = new File(image.getPath());
                File dest = new File(imgPath);
                copyFileUsingStream(source, dest);
            } catch (Exception e) {

            }

            appendProduct(ID2.getText() + " -- electrical -- " + name2.getText() + " -- " + price2.getText()
                    + " -- " + Integer.parseInt(price2.getText()) * 1.2 + " -- " + weight2.getText()
                    + " -- " + expDate2.getText() + " -- " + estOrderDate2.getText() + " -- "
                    + supplier2.getText() + " -- " + noItems2.getText()
                    + " -- " + min2.getText() + " -- " + max2.getText() + " -- "
                    + guaranteeDuration.getText() + " " + (String) guarantDays.getSelectedItem()
                    + " -- " + imgPath + " -- "
                    + new Date().toString()
            );
            
            appendSupply(ID.getText() + " -- electrical -- " + name.getText() + " -- " + price.getText());
        }
    }//GEN-LAST:event_addProductBtn2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        browseForImage();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void addProductBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductBtn3ActionPerformed
        // TODO add your handling code here:
        errMsg3.setText("");
        if (ID3.getText().trim().length() == 0) {
            errMsg3.setText("Enter Product ID");
        } else if (productExists(ID3.getText())) {
            errMsg3.setText("Product ID already exists");
        } else if (name3.getText().trim().length() == 0) {
            errMsg3.setText("Enter Product Name");
        } else if (price3.getText().trim().length() == 0) {
            errMsg3.setText("Enter Product Price");
        } else if (!isStringInt(price3.getText().trim())) {
            errMsg3.setText("Price Must be a number");
        } else if (weight3.getText().trim().length() == 0) {
            errMsg3.setText("Enter Product Weight");
        } else if (!isStringInt(weight3.getText().trim())) {
            errMsg3.setText("Weight Must be a number");
        } else if (expDate3.getText().trim().length() == 0) {
            errMsg3.setText("Enter Expiry Date");
        } else if (!isValidDateStr(expDate3.getText().trim())) {
            errMsg3.setText("Expiry Date format is invalid");
        } else if (estOrderDate3.getText().trim().length() == 0) {
            errMsg3.setText("Enter Estimated Order Date");
        } else if (!isValidDateStr(estOrderDate3.getText().trim())) {
            errMsg3.setText("Order Date format is invalid");
        } else if (supplier3.getText().trim().length() == 0) {
            errMsg3.setText("Enter Supplier(s)");
        } else if (min3.getText().trim().length() == 0) {
            errMsg3.setText("Enter Min Quantity");
        } else if (!isStringInt(min3.getText().trim())) {
            errMsg3.setText("Min Quantity must be a number");
        } else if (max3.getText().trim().length() == 0) {
            errMsg3.setText("Enter Max Quantity");
        } else if (!isStringInt(max3.getText().trim())) {
            errMsg3.setText("Max Quantity must be a number");
        } else if (Integer.parseInt(min3.getText().trim()) > Integer.parseInt(min3.getText().trim())) {
            errMsg3.setText("Max Quantity can't be lower than Min");
        } else if (noItems3.getText().trim().length() == 0) {
            errMsg3.setText("Enter Number of Items");
        } else if (!isStringInt(noItems3.getText().trim())) {
            errMsg3.setText("Number of items must be a number");
        } else if (this.image == null) {
            errMsg3.setText("Select an image");
        } else {
            String imgPath = "images/" + ID3.getText() + image.getName().substring(image.getName().lastIndexOf("."), image.getName().length());
            try {
                File source = new File(image.getPath());
                File dest = new File(imgPath);
                copyFileUsingStream(source, dest);
            } catch (Exception e) {

            }

            appendProduct(ID3.getText() + " -- other -- " + name3.getText() + " -- " + price3.getText()
                    + " -- " + Integer.parseInt(price3.getText()) * 1.2 + " -- " + weight3.getText()
                    + " -- " + expDate3.getText() + " -- " + estOrderDate3.getText() + " -- "
                    + supplier3.getText() + " -- " + noItems3.getText()
                    + " -- " + min3.getText() + " -- " + max3.getText() + " -- " + imgPath + " -- "
                    + new Date().toString()
            );
            
            appendSupply(ID.getText() + " -- other -- " + name.getText() + " -- " + price.getText());
        }
    }//GEN-LAST:event_addProductBtn3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        browseForImage();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void reorderProductBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reorderProductBtnActionPerformed
        // TODO add your handling code here:
        int row = ordersTab.getSelectedRow();
        String Q = reorderQ.getText().trim();
        reorderErrMsg.setText("");

        if (row < 0) {
            reorderErrMsg.setText("Select a product first!");
        } else if (Q.length() == 0 || !isStringInt(Q)) {
            reorderErrMsg.setText("Enter a number for quantity!");
        } else if (Q.length() > 0) {
            System.out.println(ordersTab.getValueAt(row, 0));

            Product p = products.getProduct((String) ordersTab.getValueAt(row, 0));
            int newQ = Integer.parseInt(Q) + p.getNbItems();
            if (newQ > (p.getMaxStock())) {
                reorderErrMsg.setText("Max stock quantity exceeded for the product!");
            } else {
                p.setNbItems(newQ);
                p.setLastOrderDate(new Date());
                reorderPriceLabel.setText(String.valueOf(Integer.parseInt(Q) * Integer.parseInt((String) ordersTab.getValueAt(row, 3))) + " £");
                
                                
                // When the re-order product btn clicked, navigate to the Checkout window
                // ...Should be implemented
//                this.dispose();
                ReorderCheckout reorderCheckout = new ReorderCheckout(Integer.parseInt(Q), p);
                reorderCheckout.setConfirmBtnActionListener(this);
                
                Runnable reorderBtnRunnable = new Runnable() {
                    public void run() {                        
                        reorderCheckout.setVisible(true);
                    }
                };
                java.awt.EventQueue.invokeLater(reorderBtnRunnable);                             
            }
        }
    }//GEN-LAST:event_reorderProductBtnActionPerformed

    void changeTable(JTable tab, int col, String val, String ID) {
        for (int row = 0; row < tab.getRowCount(); row++) {
            System.err.println(tab.getValueAt(row, 0));
            if (((String) tab.getValueAt(row, 0)).equals(ID)) {
                tab.setValueAt(val, row, col);
            }
        }
    }

    private void reorderQActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reorderQActionPerformed
        // TODO add your handling code here:
        int row = ordersTab.getSelectedRow();
        String Q = reorderQ.getText().trim();
        reorderErrMsg.setText("");
        if (row < 0) {
            reorderErrMsg.setText("Select a product first!");
        } else if (Q.length() == 0 || !isStringInt(Q)) {
            reorderErrMsg.setText("Enter a number for quantity!");
        } else if (Q.length() > 0) {
            reorderPriceLabel.setText(String.valueOf(Integer.parseInt(Q) * Integer.parseInt((String) ordersTab.getValueAt(row, 2))) + " £");
        }
    }//GEN-LAST:event_reorderQActionPerformed

    private void plusBtnReorderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plusBtnReorderActionPerformed
        // TODO add your handling code here:
        int row = ordersTab.getSelectedRow();
        String Q = reorderQ.getText().trim();
        reorderErrMsg.setText("");
        if (row < 0) {
            reorderErrMsg.setText("Select a product first!");
        } else if (!isStringInt(Q)) {
            reorderErrMsg.setText("Enter a number for quantity!");
        } else if (Q.length() > 0) {
            reorderPriceLabel.setText(String.valueOf(Integer.parseInt(Q) * Integer.parseInt((String) ordersTab.getValueAt(row, 3))) + " £");
        }
    }//GEN-LAST:event_plusBtnReorderActionPerformed

    private void editFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editFoodActionPerformed
        // TODO add your handling code here:
        int row = foodTab.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a product to EDIT", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            this.dispose();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new EditProduct((String) foodTab.getValueAt(row, 0), products).setVisible(true);
                }
            });
        }
    }//GEN-LAST:event_editFoodActionPerformed

    private void editKitchenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editKitchenActionPerformed
        // TODO add your handling code here:
        int row = kitchenTab.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a product to EDIT", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            this.dispose();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new EditProduct((String) kitchenTab.getValueAt(row, 0), products).setVisible(true);
                }
            });
        }
    }//GEN-LAST:event_editKitchenActionPerformed

    private void editElectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editElectActionPerformed
        // TODO add your handling code here:
        int row = elecTab.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a product to EDIT", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            this.dispose();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new EditProduct((String) elecTab.getValueAt(row, 0), products).setVisible(true);
                }
            });
        }
    }//GEN-LAST:event_editElectActionPerformed

    private void editOtherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editOtherActionPerformed
        // TODO add your handling code here:
        int row = otherTab.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a product to EDIT", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            this.dispose();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new EditProduct((String) otherTab.getValueAt(row, 0), products).setVisible(true);
                }
            });
        }
    }//GEN-LAST:event_editOtherActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ID;
    private javax.swing.JTextField ID1;
    private javax.swing.JTextField ID2;
    private javax.swing.JTextField ID3;
    private javax.swing.JButton addProductBtn;
    private javax.swing.JButton addProductBtn1;
    private javax.swing.JButton addProductBtn2;
    private javax.swing.JButton addProductBtn3;
    private javax.swing.JTabbedPane addProductPane;
    private javax.swing.JButton editElect;
    private javax.swing.JButton editFood;
    private javax.swing.JButton editKitchen;
    private javax.swing.JButton editOther;
    private javax.swing.JTable elecTab;
    private javax.swing.JLabel errMsg;
    private javax.swing.JLabel errMsg1;
    private javax.swing.JLabel errMsg2;
    private javax.swing.JLabel errMsg3;
    private javax.swing.JTextField estOrderDate;
    private javax.swing.JTextField estOrderDate1;
    private javax.swing.JTextField estOrderDate2;
    private javax.swing.JTextField estOrderDate3;
    private javax.swing.JTextField expDate;
    private javax.swing.JTextField expDate1;
    private javax.swing.JTextField expDate2;
    private javax.swing.JTextField expDate3;
    private javax.swing.JTable foodTab;
    private javax.swing.JComboBox<String> guarantDays;
    private javax.swing.JTextField guaranteeDuration;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable kitchenTab;
    private javax.swing.JTextField max;
    private javax.swing.JTextField max1;
    private javax.swing.JTextField max2;
    private javax.swing.JTextField max3;
    private javax.swing.JTextField min;
    private javax.swing.JTextField min1;
    private javax.swing.JTextField min2;
    private javax.swing.JTextField min3;
    private javax.swing.JTextField name;
    private javax.swing.JTextField name1;
    private javax.swing.JTextField name2;
    private javax.swing.JTextField name3;
    private javax.swing.JTextField noItems;
    private javax.swing.JTextField noItems1;
    private javax.swing.JTextField noItems2;
    private javax.swing.JTextField noItems3;
    private javax.swing.JTable ordersTab;
    private javax.swing.JTable otherTab;
    private javax.swing.JButton plusBtnReorder;
    private javax.swing.JTextField price;
    private javax.swing.JTextField price1;
    private javax.swing.JTextField price2;
    private javax.swing.JTextField price3;
    private javax.swing.JComboBox<String> prodType;
    private javax.swing.JTabbedPane productsTabs;
    private javax.swing.JLabel reorderErrMsg;
    private javax.swing.JLabel reorderPriceLabel;
    private javax.swing.JButton reorderProductBtn;
    private javax.swing.JTextField reorderQ;
    private javax.swing.JTextField soldbydate;
    private javax.swing.JTextField supplier;
    private javax.swing.JTextField supplier1;
    private javax.swing.JTextField supplier2;
    private javax.swing.JTextField supplier3;
    private javax.swing.JTextField usebydate;
    private javax.swing.JTextField weight;
    private javax.swing.JTextField weight1;
    private javax.swing.JTextField weight2;
    private javax.swing.JTextField weight3;
    private javax.swing.JComboBox<String> weightCateg;
    // End of variables declaration//GEN-END:variables
}
