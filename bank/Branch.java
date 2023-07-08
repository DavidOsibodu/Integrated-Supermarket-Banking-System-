/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JTextArea;

/**
 *
 * @author David
 */
public class Branch {

    private String WorkingHours = "09:00AM -17:00PM";
    private String SortCode = "00-00-00";
    private String Manager = "TBA";
    private IAddress Address1 = new IAddress();
    private JTextArea AddressTextArea;

    public Branch() {
    }

    public Branch(IAddress Address1, String WorkingHours, String SortCode, String Manager) {
        this.edit(WorkingHours, SortCode, Manager, Address1);
    }

    public Branch edit(String WorkingHours, String SortCode, String Manager, IAddress address) {
        this.Address1 = address;
        this.WorkingHours = WorkingHours;
        this.SortCode = SortCode;
        this.Manager = Manager;
        return this;
    }

    public void saveToFile(String WorkingHours, String SortCode, String Manager, IAddress address) {
        this.Address1 = address;
        this.WorkingHours = WorkingHours;
        this.SortCode = SortCode;
        this.Manager = Manager;
        try {
            FileWriter awriter = new FileWriter("branch.txt", true);
            awriter.write(this.Manager + " " + this.SortCode + " " + this.WorkingHours + " " + this.Address1.toFileString() + "\n");
            awriter.close();
        } catch (IOException ioe) {
            System.out.println("Enter while saving Head Office Address");
        }

    }

    public boolean LoadFromFile() {
        boolean isFound = true;
        String record = "";
        FileReader reader;
        try {
            reader = new FileReader("branch.txt");
            BufferedReader bin = new BufferedReader(reader);
            record = new String();
            while ((record = bin.readLine()) != null) {
                if ((WorkingHours + " " + SortCode + " " + Manager).contentEquals(record)) {
                    isFound = true;
                }
            }
            bin.close();
            bin = null;
        } catch (IOException ioe) {
            isFound = false;
        }
        return isFound;

    }

    public void Edit(String tsb_Head_Office, String string) {
        //To change body of generated methods, choose Tools | Templates.
    }

    public void Display(JTextArea jHeadOfficeTextArea) {
        jHeadOfficeTextArea.setText(
                "Working Hours =  " + WorkingHours
                + "\nSort Code = " + SortCode
                + "\nManager = " + Manager);
        //+ "\n\nAddress:\n" + Address1.toString());
        //To change body of generated methods, choose Tools | Templates.
    }

    public IAddress getAddress1() {
        return Address1;
    }

    public String getWorkingHours() {
        return WorkingHours;
    }

    public String getSortCode() {
        return SortCode;
    }

    public String getManager() {
        return Manager;
    }

    @Override
    public String toString() {
        return "WorkingHours = " + WorkingHours + "\n SortCode = " + SortCode
                + "\n Manager = " + Manager + "\n Address = " + Address1.toFileString().replace(" ", ", ") + "\n";
    }

}
