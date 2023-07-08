package supermarket;


import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author David
 */
public class Customer {

    private final String FirstName;
    private final String LastName;
    private final String Gender;
    private final Date DOB;
    private final Date Customersince;
    private String Address;
    private String Username;
    private String Password;

    public Customer(String FirstName, String LastName, String Gender, Date DOB, String Address, String Username, String Password, Date Customersince) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Gender = Gender;
        this.DOB = DOB;
        this.Address = Address;
        this.Username = Username;
        this.Password = Password;
        this.Customersince = new Date();
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getGender() {
        return Gender;
    }

    public Date getDOB() {
        return DOB;
    }

    public String getAddress() {
        return Address;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public Date getCustomersince() {
        return Customersince;
    }

}
