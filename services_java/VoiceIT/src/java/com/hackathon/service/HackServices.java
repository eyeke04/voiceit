/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hackathon.service;

import com.hackathon.Bean.BalDataBean;
import com.hackathon.Bean.BalanceResponse;
import com.hackathon.Bean.HackBean;
import com.hackathon.Bean.OTPAuthResponse;
import com.hackathon.Bean.OTPResponse;
import com.hackathon.Bean.TokenResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author AMUDA GBONJUBOLA
 */
@Path("hackService")
public class HackServices extends RXApplication {

    @GET
    @Path("/ping")
    public String pinga() {

        try {

        } catch (Exception e) {
        }
        return "Voiceit is alive!!!";
    }

    @GET
    @Path("/balance/{accNum}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getBalance(@PathParam(value = "accNum") String accountNumber) {
        String availableBalance = "";
        try {
            BalanceResponse requestString = new HackServices().doEnquiry(accountNumber);
            if (requestString.getStatus().equalsIgnoreCase("SUCCESS")) {
                BalDataBean data = requestString.getData();
                availableBalance = data.getAvailablebalance();
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return availableBalance;
    }

    @GET
    @Path("/name/{accNum}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getName(@PathParam(value = "accNum") String accountNumber) {
        String name = "";
        try {
            BalanceResponse requestString = new HackServices().doEnquiry(accountNumber);
            if (requestString.getStatus().equalsIgnoreCase("SUCCESS")) {
                BalDataBean data = requestString.getData();
                name = data.getAccountname();
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return name;
    }

    @GET
    @Path("/sendotp/{accNum}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getOTP(@PathParam(value = "accNum") String accountNumber) {
        String status = "FAILED";
        String tokenValue = getToken();
        String phoneNo = new HackServices().getPhoneNum(accountNumber);
        if (phoneNo.substring(0, 1).equalsIgnoreCase("0")) {
            phoneNo = "+234" + phoneNo.substring(1);
        } else if (phoneNo.substring(0, 1).equalsIgnoreCase("2")) {
            phoneNo = "+" + phoneNo;
        }
        String OTP_URL = HackBean.GET_OTPAPI + phoneNo+ "?access_token=" + tokenValue;
        System.out.println("OTP:::" + OTP_URL);
        try {
            Client build = ClientBuilder.newBuilder().build();
            WebTarget target = build.target(OTP_URL);

            Response post = target.request(MediaType.APPLICATION_JSON).get();//(Entity.text(""),Response.class);

            String readEntity = post.readEntity(String.class);

            System.out.println("readEntity = " + readEntity);
            OTPResponse requestString = (OTPResponse) new ServiceUtil().unpackBean(readEntity, "com.hackathon.Bean.OTPResponse");

            if (requestString.getStatus().equalsIgnoreCase("SUCCESS")) {
                status = "SUCCESS";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    @GET
    @Path("/authenticate/{accNum}/{otp}")
    @Produces(MediaType.TEXT_PLAIN)
    public String authenticateOTP(@PathParam(value = "accNum") String accountNumber, @PathParam(value = "otp") String otp) {
        String status = "FAILED";
        String tokenValue = getToken();
        String phoneNo = new HackServices().getPhoneNum(accountNumber);

        String OTP_URL = HackBean.AUTH_OTPAPI + "?access_token=" + tokenValue;
        System.out.println("AUTH_OTPAPI:::" + OTP_URL);
        try {
            HackBean balBean = new HackBean();
            balBean.setPhonenumber(phoneNo);
            balBean.setOtp(otp);
            Client build = ClientBuilder.newBuilder().build();
            WebTarget target = build.target(OTP_URL);

            Response post = target.request(MediaType.APPLICATION_JSON).post(Entity.json(balBean), Response.class);

            String readEntity = post.readEntity(String.class);

            System.out.println("readEntity = " + readEntity);
            OTPAuthResponse requestString = (OTPAuthResponse) new ServiceUtil().unpackBean(readEntity, "com.hackathon.Bean.OTPAuthResponse");

            if (requestString.getStatus().equalsIgnoreCase("SUCCESS")) {
                status = "SUCCESS";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public String getPhoneNum(String accountNumber) {
        String phoneNum = "";
        try {
            BalanceResponse requestString = new HackServices().doEnquiry(accountNumber);
            if (requestString.getStatus().equalsIgnoreCase("SUCCESS")) {
                BalDataBean data = requestString.getData();
                phoneNum = data.getPrimaryphone();
                System.out.println("Phone number is::::" + phoneNum);
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return phoneNum;
    }

    public BalanceResponse doEnquiry(String accountNumber) {
        BalanceResponse requestString = null;
        String tokenValue = getToken();
        String BAL_URL = HackBean.BALANCEAPI + tokenValue;
        try {
            HackBean balBean = new HackBean();
            balBean.setAccountnumber(accountNumber);
            balBean.setBankcode(HackBean.BANKCODE);
            Client build = ClientBuilder.newBuilder().build();
            WebTarget target = build.target(BAL_URL);

            Response post = target.request(MediaType.APPLICATION_JSON).post(Entity.json(balBean), Response.class);

            String readEntity = post.readEntity(String.class);

            //   System.out.println("readEntity = " + readEntity);
            requestString = (BalanceResponse) new ServiceUtil().unpackBean(readEntity, "com.hackathon.Bean.BalanceResponse");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestString;
    }

    public String getToken() {
        String accessToken = "";
        String TOKEN_URL = HackBean.TOKENAPI;
        try {
            Client build = ClientBuilder.newBuilder().build();
            WebTarget target = build.target(TOKEN_URL);
            Form form = new Form();
            form.param("grant_type", HackBean.GRANTTYPE);
            form.param("client_id", HackBean.CLIENTID);
            form.param("client_secret", HackBean.CLIENTSECRET);
            Response post = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
            String readEntity = post.readEntity(String.class);
            System.out.println("readEntity = " + readEntity);
            TokenResponse requestString = (TokenResponse) new ServiceUtil().unpackBean(readEntity, "com.hackathon.Bean.TokenResponse");
            accessToken = requestString.getAccess_token();
            System.out.println("accessToken::" + accessToken);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return accessToken;
    }

    public static void main(String[] args) {
        HackServices hack = new HackServices();
        // hack.getToken();
        String otp = hack.getOTP("0065172058");
        System.out.println("OTP:::" + otp);
    }
}
