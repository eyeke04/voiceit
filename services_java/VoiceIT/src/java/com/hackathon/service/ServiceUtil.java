/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hackathon.service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.stream.JsonGenerationException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

/**
 *
 * @author AMUDA GBONJUBOLA
 */
public class ServiceUtil {
    public Object unpackBean(String requestStr, String className) throws JsonMappingException, JsonGenerationException, IOException, Exception {
       
        ObjectMapper mapper = new ObjectMapper();
        Object bb = mapper.readValue(requestStr, Class.forName(className));
        return bb;
    }

    public String objToJson(Object beanObject) {
        String jsonString = null;
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            jsonString = ow.writeValueAsString(beanObject);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(HackServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HackServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonString;

    } 
}
