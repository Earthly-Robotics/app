package com.example.Network;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Message {
    private JSONObject obj = new JSONObject();
    private JSONParser parser = new JSONParser();

    /**
     * Creates json object from 2 String arrays
     *
     * @param a an string array to make an json string. Names
     * @param b an string array to make an json string. Variables
     */
    public Message(String [] a, String [] b){
        for (int i = 0; i < a.length; i++){
            obj.put(a[i], b[i]);
        }
    }

    public Message(String JsonString) throws ParseException {
        obj = (JSONObject) parser.parse(JsonString);
    }

    public JSONObject getJSONObject() { return obj; }
    public String getStringObject(){
        return obj.toJSONString();
    }

}