/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author David
 */
public class User {

    private String Name = "";
    private String Email;
    private String Role;
    private String Password = "";
    private String Filename = "login.txt";
    Date date;
    private Object[] arr;

    User(String newName, String email, String newPass, String newRole) {
        Name = newName;
        Password = newPass;
        Role = newRole;
        Email = email;
        this.date = (new Date(System.currentTimeMillis()));
    }

    public User() {

    }

    public boolean isUser(String email, String password, String role) {
        Email = email;
        boolean isFound = false;
        String record = "";
        FileReader reader;
        try {
            reader = new FileReader(Filename);
            BufferedReader bin = new BufferedReader(reader);
            record = new String();
            while ((record = bin.readLine()) != null) {
                     if (arr.length >= 4 && Email.equals(arr[1]) && password.equals(arr[2]) && role.equals(arr[3]))  {
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

    public boolean isRegistered(String newName, String email, String newPassword, String newRole) throws IOException {
        boolean isRegistered = false;
        Name = newName;
        Password = newPassword;
        Role = newRole;
        Email = email;
        FileWriter writer;
        writer = new FileWriter("login.txt", true);
        String addedOn = " " + new SimpleDateFormat("dd/MM/yyyy@hh:mm:ss").format(new Date(System.currentTimeMillis()));

        writer.write(Name + " " + email + " " + Password + " " + Role + " " + addedOn + "\n");
        writer.flush();
        writer.close();
        if (Role.equals("Customer")) {
            writer = new FileWriter("Customers.txt", true);
            writer.write(Email + " " + addedOn + "\n");
            writer.flush();
            writer.close();
        }
        //writer. write("##" + System.getProperty( "line.separator"));
        isRegistered = true;

        writer = null;
        return isRegistered;
    }

    public boolean isManager(String new_role) {
        Role = new_role;
        boolean boo = false;
        if (Role == "Manager") {
            boo = true;
        } else {
            boo = false;
        }
        return boo;
    }

    public boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public String getEmail() {
        return Email;
    }

}
