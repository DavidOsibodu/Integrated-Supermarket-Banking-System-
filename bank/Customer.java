/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class Customer {

    private String FirstName = "";
    private String SurName = "";
    private String Email = "";
    private IAddress HomeAdress;
    private Date CustomerSince;
    public Account[] OwnedAccounts = {null, null, null, null};

    public Customer(String FirstName, String SurName) {
        this.FirstName = FirstName;
        this.SurName = SurName;
    }

    public boolean createBankAccount(Account A, int no) {
        if (A instanceof CurrentAccount) {
            OwnedAccounts[0] = (CurrentAccount) A;
            OwnedAccounts[0].saveToFile(Email, 0);
            return true;
        } else if (A instanceof ISAAccount) {
            OwnedAccounts[1] = (ISAAccount) A;
            OwnedAccounts[1].saveToFile(Email, 0);
            return true;
        } else if (A instanceof SavingAccount) {
            if (no == 1) {
                OwnedAccounts[2] = (SavingAccount) A;
                OwnedAccounts[2].saveToFile(Email, 1);
                return true;
            } else if (no == 2) {
                OwnedAccounts[3] = (SavingAccount) A;
                OwnedAccounts[3].saveToFile(Email, 2);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void readBankAccountFromFile(String accType, int n) {
        try {
            FileReader reader = new FileReader("Accounts/" + accType + "/" + Email + (n != 0 ? "_N" + n : "") + ".txt");
            BufferedReader bin = new BufferedReader(reader);
            String line = "";
            String info = "";

            while ((line = bin.readLine()) != null) {
                info += line + "_%specialSeparator%_";
            }
            bin.close();
            bin = null;
            String[] arr = info.split("_%specialSeparator%_");
            if (accType.equals("Current")) {
                OwnedAccounts[0] = new CurrentAccount().create(arr[0], Integer.parseInt(arr[1]),
                        Double.parseDouble(arr[2]), arr[3], Double.parseDouble(arr[4]),
                        new SimpleDateFormat("dd/MM/yyyy").parse(arr[5]),
                        new SimpleDateFormat("dd/MM/yyyy").parse(arr[7]), Integer.parseInt(arr[8]));
            } else if (accType.equals("ISA")) {
                OwnedAccounts[1] = new ISAAccount().create(arr[0], Integer.parseInt(arr[1]),
                        Double.parseDouble(arr[2]), arr[3], Double.parseDouble(arr[4]),
                        new SimpleDateFormat("dd/MM/yyyy").parse(arr[5]),
                        new SimpleDateFormat("dd/MM/yyyy").parse(arr[7]), Integer.parseInt(arr[8]));
            } else if (accType.equals("Saving") && n == 1) {
                OwnedAccounts[2] = new SavingAccount().create(arr[0], Integer.parseInt(arr[1]),
                        Double.parseDouble(arr[2]), arr[3], Double.parseDouble(arr[4]),
                        new SimpleDateFormat("dd/MM/yyyy").parse(arr[5]),
                        new SimpleDateFormat("dd/MM/yyyy").parse(arr[7]), Integer.parseInt(arr[8]));
            } else if (accType.equals("Saving") && n == 2) {
                OwnedAccounts[3] = new SavingAccount().create(arr[0], Integer.parseInt(arr[1]),
                        Double.parseDouble(arr[2]), arr[3], Double.parseDouble(arr[4]),
                        new SimpleDateFormat("dd/MM/yyyy").parse(arr[5]),
                        new SimpleDateFormat("dd/MM/yyyy").parse(arr[7]), Integer.parseInt(arr[8]));
            }
            System.out.println(accType + (n != 0 ? "_N" + n : "") + " Account was imported from file");
        } catch (Exception ex) {
            System.out.println(accType + (n != 0 ? "_N" + n : "") + " Account file not found");
            //ex.printStackTrace();
        }
    }

    public void readBankAccountsFromFile() {
        readBankAccountFromFile("Current", 0);
        readBankAccountFromFile("ISA", 0);
        readBankAccountFromFile("Saving", 1);
        readBankAccountFromFile("Saving", 2);

    }

    public Customer(String email, String fname, String lname, IAddress homeAdress, Date customerSince) {
        edit(email, fname, lname, homeAdress);
        this.CustomerSince = customerSince;
    }

    public void edit(String email, String fname, String lname, IAddress homeAdress) {
        FirstName = fname;
        SurName = lname;
        Email = email;
        HomeAdress = homeAdress;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getSurName() {
        return SurName;
    }

    @Override
    public String toString() {
        return "FirstName = " + FirstName
                + "\n SurName = " + SurName
                + "\n Email = " + Email
                + "\n HomeAdress = " + HomeAdress.toFileString()
                + "\n CustomerSince = "
                + CustomerSince + "\n";
    }

    String dToString() {
        try {
            return new SimpleDateFormat("dd/MM/yyyy@hh:mm:ss").format(CustomerSince);

        } catch (Exception e) {
            return ("00-00-00@00:00:00");
        }
    }

    public String toFileString() {
        return Email + " " + dToString() + " " + FirstName + " " + SurName + " " + HomeAdress.toFileString();
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Customer other = (Customer) obj;
        if (!Objects.equals(this.FirstName, other.FirstName)) {
            return false;
        }
        if (!Objects.equals(this.SurName, other.SurName)) {
            return false;
        }
        return true;
    }

    public void printBankAccounts() {
        System.out.println("Customer{" + "CurrentAccount=" + OwnedAccounts[0]
                + "\n, ISAAccount=" + OwnedAccounts[0] + "\n, SavingAccount1=" + OwnedAccounts[0]
                + "\n, SavingAccount2=" + OwnedAccounts[0] + "}\n");
    }

    public String getEmail() {
        return Email;
    }

    public IAddress getHomeAdress() {
        return HomeAdress;
    }

    public Date getCustomerSince() {
        return CustomerSince;
    }

    public Account[] getOwnedAccounts() {
        return OwnedAccounts;
    }

}
