/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hackathon.Bean;

/**
 *
 * @author AMUDA GBONJUBOLA
 */
public class HackBean {

    public static String CLIENTID = "58d557dc4637e61000b651e6";
    public static String CLIENTSECRET = "lfv78LRsCLPFybYE4zoDx5y3xZpuoYj4usgzONzNAz7N9sGCp3uzfg6632SXMWHgn4JeFOw1SLILbIncZmdNrYaDPZmlCaQWsM6i";
    public static String GRANTTYPE = "client_credentials";
    public static String BANKCODE="044";
    public static String SUCCESS="SUCCESS";
    public static String BALANCEAPI="https://pwcstaging.herokuapp.com/account/validation?access_token=";
    public static String TOKENAPI="https://pwcstaging.herokuapp.com/oauth/token";
    public static String GET_OTPAPI="https://pwcstaging.herokuapp.com/otp/sms/";
    public static String AUTH_OTPAPI="https://pwcstaging.herokuapp.com/otp/authenticate";
     private String bankcode;
    private String accountnumber;
    private String otp;
    private String phonenumber;

    /**
     * @return the bankcode
     */
    public String getBankcode() {
        return bankcode;
    }

    /**
     * @param bankcode the bankcode to set
     */
    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    /**
     * @return the accountnumber
     */
    public String getAccountnumber() {
        return accountnumber;
    }

    /**
     * @param accountnumber the accountnumber to set
     */
    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    /**
     * @return the otp
     */
    public String getOtp() {
        return otp;
    }

    /**
     * @param otp the otp to set
     */
    public void setOtp(String otp) {
        this.otp = otp;
    }

    /**
     * @return the phonenumber
     */
    public String getPhonenumber() {
        return phonenumber;
    }

    /**
     * @param phonenumber the phonenumber to set
     */
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

   
    
}
