/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.io.File;
import java.util.Date;
import javax.swing.*;

public class Person {
    

    private String FirstName = "";
         private String LastName = "";
            private IAddress HomeAddress = new IAddress();
               private Date DOB = new Date();
    private Date Customersince = new Date();

    
    public Person(String FirstName, String LastName, IAddress HomeAddress, Date DOB,Date Custromersince  ) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.HomeAddress = HomeAddress;
        this.DOB = DOB;
    }

    public void display(JTextArea jTextArea) {
        jTextArea.setText("First Name = '" + FirstName + '\'' +
                "\nLast Name = '" + LastName + '\'' +
                "\nHome Address = " + HomeAddress +
                "\nDate of Birth =" + DOB+
        "/nCustomer Since=" + Customersince);
    
    }

    public boolean checkDoB(Date date) {
        return date.equals(DOB);
    }

    public void saveToFile() {

    }

    public static Person loadFromFile(File file) {
        return null;
       
    
    }
}