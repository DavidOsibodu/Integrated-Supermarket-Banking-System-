/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.io.File;
import javax.swing.JTextArea;

/**
 *
 * @author David
 */
public class IAddress {

    private String Name = "";
    private String Street = "";
    private String HouseNo = "";
    private String HouseName = "";
    private String Area = "";
    private String Postcode = "";
    private String Town = "";
    private String Country = "";

    public IAddress() {
    }

    public IAddress(String Name, String HouseNo, String HouseName, String Street, String Area, String Postcode, String Town, String Country) {
        this.edit(Name, HouseNo, HouseName, Street, Area, Postcode, Town, Country);

    }

    public IAddress edit(String Name, String HouseNo, String HouseName, String Street, String Area, String Postcode, String Town, String Country) {
        this.Name = Name;
        this.HouseNo = HouseNo;
        this.HouseName = HouseName;
        this.Street = Street;
        this.Area = Area;
        this.Postcode = Postcode;
        this.Town = Town;
        this.Country = Country;
        return this;
    }

    @Override
    public String toString() {
        return "Name = " + Name
                + "\nHouse Number = " + HouseNo
                + "\nHouse Name = " + HouseName
                + "\nStreet = " + Street
                + "\nArea = " + Area
                + "\nPostcode = " + Postcode
                + "\nTown = " + Town
                + "\nCountry = " + Country;
    }

    public String toFileString() {
        return Name + " " + HouseName
                + " " + HouseNo
                + " " + Street
                + " " + Area
                + " " + Postcode
                + " " + Town
                + " " + Country;
    }

    public JTextArea display(JTextArea jTextArea) {
        jTextArea.setText(toString());
        return jTextArea;
    }

    public void saveToFile() {

    }

}
