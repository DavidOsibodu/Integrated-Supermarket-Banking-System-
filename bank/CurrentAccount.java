/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.io.FileWriter;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author David
 */
public class CurrentAccount extends Account {

    private final double Overdraft = 100;
    private String Conditions = "";
    private double AvailableBalance = 0;

    public Account create(String SortCode, int AccountNo, double balance,
            String NameOfBank, double Rate, Date LastReportedDate, Date createdOn, int txn) {
        super.create(SortCode, AccountNo, balance, NameOfBank, Rate, LastReportedDate, createdOn, txn);
        AvailableBalance = balance;
        return this;
    }

    public void display() {

    }

    public void depositMonthlyInterest() {

    }

    public void calculateCharges() {

    }

    @Override
    public void withdraw(int Amount) {
        if (Amount - balance > Overdraft) {
            JOptionPane.showMessageDialog(null, "£25.00 will be deducted for exceeding overdraft.\n",
                    "Error Message", JOptionPane.ERROR_MESSAGE);
            super.withdraw(Amount + 25);
            AvailableBalance = balance;
            transactions++;
        } else {
            super.withdraw(Amount);
            transactions++;
            JOptionPane.showMessageDialog(null, "Successfully withdrew £" + Amount);
        }
    }

    @Override
    public void deposit(int Amount) {
        super.deposit(Amount);
        AvailableBalance = balance;
        JOptionPane.showMessageDialog(null, "Successfully deposited £" + Amount);
    }

    @Override
    public void transfer(Account A) {

    }

    @Override
    public String toString() {
        return "CurrentAccount{" + super.toString() + "Overdraft=" + Overdraft + ", Conditions=" + Conditions + ", AvailableBalance=" + AvailableBalance + '}';
    }

    public void saveToFile(String cEmail, int n) {
        FileWriter writer;
        try {
            writer = new FileWriter("Accounts/Current/" + cEmail + ".txt", false);
            writer.write(toFileString());
            writer.close();
            System.out.println("Account saved to file");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toFileString() {
        return super.toFileString();
    }

}
