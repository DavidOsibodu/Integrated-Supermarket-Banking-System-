/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public abstract class Account {

    protected String SortCode;
    protected int AccountNo;
    protected double balance;
    protected String NameOfBank;
    protected double Rate;
    protected Date LastReportedDate;
    protected boolean joint;
    protected int transactions;
    public Date createdOn;

    public Account create(String SortCode, int AccountNo, double balance,
            String NameOfBank, double Rate, Date LastReportedDate, Date createdOn, int transactions) {
        this.SortCode = SortCode;
        this.AccountNo = AccountNo;
        this.balance = balance;
        this.NameOfBank = NameOfBank;
        this.Rate = Rate;
        this.LastReportedDate = LastReportedDate;
        this.createdOn = createdOn;
        this.transactions = transactions;
        return this;
    }

    public abstract void display();

    public abstract void transfer(Account A);

    public abstract void saveToFile(String email, int n);

    public abstract void calculateCharges();

    public void deposit(int Amount) {
        balance += Amount;
        transactions++;
    }

    public void withdraw(int Amount) {
        balance -= Amount;
        transactions++;
    }

    public void printStatement() {

    }

    public void lastReportedDate(Date aLastReportedDate) {

    }

    public double getBalance() {
        return (double) Math.round(balance * 100) / 100;
    }

    @Override
    public String toString() {
        return "SortCode=" + SortCode + ", AccountNo=" + AccountNo
                + ", balance=" + balance + ", NameOfBank=" + NameOfBank
                + ", Rate=" + Rate + ", LastReportedDate=" + LastReportedDate + ", joint=" + joint;
    }

    public String toFileString() {
        return SortCode + "\n" + AccountNo + "\n" + balance
                + "\n" + NameOfBank + "\n" + Rate + "\n" + new SimpleDateFormat("dd/MM/yyyy").format(LastReportedDate)
                + "\n" + joint + "\n" + new SimpleDateFormat("dd/MM/yyyy").format(createdOn) + "\n" + transactions;
    }

    public String getSortCode() {
        return SortCode;
    }

    public int getAccountNo() {
        return AccountNo;
    }

    public String getNameOfBank() {
        return NameOfBank;
    }

    public double getRate() {
        return Rate;
    }

    public Date getLastReportedDate() {
        return LastReportedDate;
    }

    public boolean isJoint() {
        return joint;
    }

    public String dToStr(Date d) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").format(d);
        } catch (Exception e) {
            return null;
        }
    }

    public int getTransactions() {
        return transactions;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

}
