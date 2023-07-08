package bank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author David
 */
public class BranchList {

    ArrayList<Branch> branches;

    BranchList() {
        branches = new ArrayList<>();
    }

    public void loadFromFile() {
        try {
            FileReader reader = new FileReader("branches.txt");
            BufferedReader bin = new BufferedReader(reader);
            String ch = new String();
            while ((ch = bin.readLine()) != null) {
                String[] branch = ch.split(" ");
                IAddress adrs = new IAddress();
                adrs.edit(branch[3], branch[4], branch[5], branch[6], branch[7], branch[8], branch[9], branch[10]);
                branches.add(new Branch(adrs, branch[0], branch[1], branch[2]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean add(Branch b) {
        try {
            Writer awriter = new FileWriter("branches.txt", true);
            awriter.write(b.getWorkingHours() + " " + b.getSortCode() + " " + b.getManager() + " " + b.getAddress1().toFileString() + "\n");
            awriter.close();
        } catch (IOException ioe) {
            System.out.println("Error while saving branch to file");
        }
        return branches.add(b);
    }

    public boolean remove(Branch b) {
        return branches.remove(b);
    }

    @Override
    public String toString() {
        String cum = "---------\n";
        for (Branch b : branches) {
            cum += b.toString() + "---------\n";
        }
        return cum;
    }

}
