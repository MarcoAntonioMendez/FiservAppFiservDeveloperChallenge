package com.fiserv.fiservappfiservdeveloperchallenge.banking;

/**
 * The Customer class offers a container for customers information and to be displayed accordingly.
 * Contains the necessary setters and getters.
 *
 * @author  MarcoAntonioMéndez
 * @version 1.0
 * @since   2020-10-26
 */

public class Customer {
    private String customerId,bankAccountNumber,customerName,company;

    public Customer(String customerId, String bankAccountNumber, String customerName,
                    String company){
        setCustomerId(customerId);
        setBankAccountNumber(bankAccountNumber);
        setCustomerName(customerName);
        setCompany(company);
    }

    public String getCustomerId(){
        return customerId;
    }

    public void setCustomerId(String customerId){
        this.customerId = customerId;
    }

    public String getBankAccountNumber(){
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber){
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getCustomerName(){
        return customerName;
    }

    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }

    public String getCompany(){
        return company;
    }

    public void setCompany(String company){
        this.company = company;
    }
}
