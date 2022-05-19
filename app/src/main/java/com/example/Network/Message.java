package com.example.Network;
import org.json.simple.JSONObject;

public class Message {
    private JSONObject obj = new JSONObject();

    public Message(String [] a, String [] b){
        for (int i = 0; i < a.length; i++){
            obj.put(a[i], b[i]);
        }
    }

    public String getStringObject(){
        return obj.toJSONString();
    }

}
