package supermarket;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author David
 */
public class Start {

    public static void main(String args[]) {
        //ArrayList<Customer> customers = new ArrayList();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(false);
            }
        });

    }
}
