package com.jointeams.backend.util;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public enum IsCommentAvailable {
    Flag;
    private boolean value;
    final private String path = "Backend/src/main/resources/config/globalConfig.json";
    final private String key = "isCommentFunctionAvailable";

    IsCommentAvailable()
    {
        JSONParser parser = new JSONParser();
        boolean isCommentAvailable = false;
        try {
            FileReader reader = new FileReader(path);
            Object obj = parser.parse(reader);
            JSONObject jsonObj = (JSONObject) obj;
            isCommentAvailable = (boolean) jsonObj.get(key);
            reader.close();

        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.value = isCommentAvailable;
    }

    public String getKey() {
        return key;
    }

    public boolean getValue()
    {
        return value;
    }

    public void setValue(boolean value)
    {
        this.value = value;
    }
    public void saveValue()
    {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(path);
            Object obj = parser.parse(reader);
            JSONObject jsonObj = (JSONObject) obj;
            jsonObj.put(key, this.value);
            reader.close();
            FileWriter writer = new FileWriter(path);
            writer.write(jsonObj.toJSONString());
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
