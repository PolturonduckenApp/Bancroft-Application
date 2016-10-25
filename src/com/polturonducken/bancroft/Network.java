package com.polturonducken.bancroft;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;

public class Network {
	public static List login(String username, String password) {

        try {
            ConnectionRequest req = new ConnectionRequest();
            req.setUrl("https://www.bancroftschool.org/userlogin.cfm?");
            req.setPost(false);
            req.addArgument("username", username);
            req.addArgument("password", password);
            req.addArgument("format", "json");
            
            NetworkManager.getInstance().addToQueueAndWait(req);
            byte[] data = req.getResponseData();
            if (data == null) {
                throw new IOException("Network Err");
            }
            JSONParser parser = new JSONParser();
            Map response = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(data), "UTF-8"));
            System.out.println("res" + response);
            List items = (List)response.get("items");
            return items;
        } catch (Exception e) {
        }
        return null;
    }
}
