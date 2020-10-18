package com.fiserv.fiservappfiservdeveloperchallenge.banking;

public class BankAccount{
    public static final String COMPANY_VISA_CONSTANT = "VISA";
    public static final String COMPANY_MASTERCARD_CONSTANT = "MASTERCARD";
    public static final String COMPANY_DISCOVER_CONSTANT = "DISCOVER";
    public static final String DEBIT_TYPE_CONSTANT = "DEBIT";
    public static final String CREDIT_TYPE_CONSTANT = "CREDIT";

    private String bankAccountId,expirationDate,securityCodeCvv,bankAccountNumber,company,type;

    public BankAccount(String bankAccountId, String expirationDate, String securityCodeCvv,
                       String bankAccountNumber,String company,String type){

        setBankAccountId(bankAccountId);
        setExpirationDate(expirationDate);
        setSecurityCodeCvv(securityCodeCvv);
        setBankAccountNumber(bankAccountNumber);
        setCompany(company);
        setType(type);
    }

    public String getBankAccountId(){
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId){
        this.bankAccountId = bankAccountId;
    }

    public String getExpirationDate(){
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate){
        this.expirationDate = expirationDate;
    }

    public String getSecurityCodeCvv(){
        return securityCodeCvv;
    }

    public void setSecurityCodeCvv(String securityCodeCvv){
        this.securityCodeCvv = securityCodeCvv;
    }

    public String getBankAccountNumber(){
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber){
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getCompany(){
        return company;
    }

    public void setCompany(String company){
        this.company = company;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }
}
