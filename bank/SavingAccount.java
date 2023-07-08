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
public class SavingAccount extends Account {

    private double WithdrawLimit = 200;

    public SavingAccount create(String SortCode, int AccountNo,
            double balance, String NameOfBank, double Rate, Date LastReportedDate, Date createdOn, int txn) {
        super.create(SortCode, AccountNo, balance, NameOfBank, Rate, LastReportedDate, createdOn, txn);
        return this;
    }

    @Override
    public void display() {

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
    public void withdraw(int Amount) {
        if (Amount > 200) {
            JOptionPane.showMessageDialog(null, "Withdraw limit is $200", "Error Message", JOptionPane.ERROR_MESSAGE);
        } else {
            int diff = Math.round(TimeUnit.DAYS.convert(Math.abs(new Date().getTime() - createdOn.getTime()),
                    TimeUnit.MILLISECONDS));
            super.withdraw(Amount);
            if (diff < 90) {
                JOptionPane.showMessageDialog(null, "Careful! 90 days haven't passed yet\n"
                        + "since creating you saving account and hence \n"
                        + "a charge will be deducted of £10", "Error Message", JOptionPane.ERROR_MESSAGE);
                balance -= 10;
                transactions++;
            } else {
                JOptionPane.showMessageDialog(null, "Successfully withdrew £" + Amount);
            }
        }
    }

    @Override
    public void deposit(int Amount) {
        super.deposit(Amount);
        JOptionPane.showMessageDialog(null, "Successfully deposited £" + Amount);
    }

    protected void endMonth() {

    }

    protected void endMonthUtil() {

    }

    @Override
    public String toString() {
        return "SavingAccount{" + super.toString() + "WithdrawLimit=" + WithdrawLimit + '}';
    }

    @Override
    public void saveToFile(String cEmail, int n) {
        FileWriter writer;
        try {
            writer = new FileWriter("Accounts/Saving/" + cEmail + "_N" + n + ".txt", false);
            writer.write(toFileString());
            writer.close();
            System.out.println("Account saved to file");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
