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
public class BalanceResponse {
  private String status;  
    private BalDataBean data; 

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the data
     */
    public BalDataBean getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(BalDataBean data) {
        this.data = data;
    }

   
}
