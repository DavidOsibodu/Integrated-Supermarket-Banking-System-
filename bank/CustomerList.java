/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author David
 */
public class CustomerList {

    private ArrayList<Customer> Clients;

    CustomerList() {
        Clients = new ArrayList();
    }

    public void display() {

    }

    public boolean saveToFile() {
        FileWriter writer;
        try {
            writer = new FileWriter("Customers.txt", false);
            writer.write("");
            writer.close();
            writer = new FileWriter("Customers.txt", true);
            for (Customer c : Clients) {
                writer.write(c.toFileString() + "\n");
            }
            writer.close();
            System.out.println("customers saved to file");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void loadFromFile() {
        try {
            FileReader reader = new FileReader("Customers.txt");
            BufferedReader bin = new BufferedReader(reader);
            String ch = new String();
            while ((ch = bin.readLine()) != null) {
                String[] data = ch.split(" ");
                IAddress address = new IAddress(data[4], data[5], data[6], data[7], data[8], data[9], data[10], data[11]);
                Date date = new SimpleDateFormat("dd/MM/yyyy@hh:mm:ss").parse(data[1]);
                Clients.add(new Customer(data[0], data[2], data[3], address, date));
            }
        } catch (Exception e) {
        }
    }

    public boolean addCustomer(Customer c) {
        return Clients.add(c);
    }

    public boolean deleteCustomer(Customer c) {
        for (int i = 0; i < Clients.size(); i++) {
            if (Clients.get(i).getFirstName().equals(c.getFirstName()) && Clients.get(i).getSurName().equals(c.getSurName())) {
                Clients.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList getList() {
        return Clients;
    }

    public Customer findByName(String fname, String lname) {
        for (Customer c : Clients) {
            if (c.getFirstName().equals(fname) && c.getSurName().equals(lname)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String cum = "----\n";
        for (Customer c : Clients) {
            cum += c.toFileString() + "\n----\n";
        }
        return cum;
    }

}
