/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.io.FileWriter;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

/**
 *
 * @author David
 */
public class ISAAccount extends Account {

    private final double MaximumLimitPerYear = 3250;
    private double DepositedThisYear = 0;

    public ISAAccount create(String SortCode, int AccountNo, double balance,
            String NameOfBank, double Rate, Date LastReportedDate, Date createdOn, int txn) {
        super.create(SortCode, AccountNo, balance, NameOfBank, Rate, LastReportedDate, createdOn, txn);
        return this;
    }

    @Override
    public void display() {

    }

    @Override
    public void deposit(int Amount) {
        if (Amount + DepositedThisYear > MaximumLimitPerYear) {
            JOptionPane.showMessageDialog(null, "You cannot exceed 3250 of deposits for ISA Accounts.\n",
                    "Error Message", JOptionPane.ERROR_MESSAGE);
        } else {
            super.deposit(Amount);
            DepositedThisYear += Amount;
            JOptionPane.showMessageDialog(null, "Successfully deposited £" + Amount);
        }
    }

    @Override
    public void withdraw(int Amount) {
        super.withdraw(Amount);
        JOptionPane.showMessageDialog(null, "Successfully withdrew £" + Amount);
    }

    @Override
    public void calculateCharges() {

    }

    @Override
    public void transfer(Account A) {

    }

    public void depositYearlyInterest() {

    }

    @Override
    public String toString() {
        return "ISAAccount{" + super.toString() + "MaximumLimitPerYear=" + MaximumLimitPerYear + ", DepositedThisYear=" + DepositedThisYear + '}';
    }

    public void saveToFile(String cEmail, int n) {
        FileWriter writer;
        try {
            writer = new FileWriter("Accounts/ISA/" + cEmail + ".txt", false);
            writer.write(toFileString());
            writer.close();
            System.out.println("Account saved to file");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
