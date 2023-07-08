/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import javax.swing.JOptionPane;

public class Operations {

    public double power(double base, int power) {

        double ans;
        if (power < 0) {
            return 0;
        } else if (power == 0) {
            return 1;
        } else if (power == 1) {
            return base;
        } else {
            ans = base;
            for (int i = 1; i < power; i++) {
                ans = base * ans;
            }
            return ans;
        }
    }

    public double cyclindervolumed(double radius, double height) {

        double pi = 3.1415;
        double volume = (double) (pi * (radius * radius) * height);

        return 0;
    }

    public double sphere(double radius) {

        double pi = 3.1415;

        double volume = (4.0 / 3.0) * (pi * (radius * radius * radius));

        return volume;
    }

}
