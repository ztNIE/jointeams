package com.jointeams.backend.util;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;

import java.io.*;

public enum IsCommentAvailable {
    Flag;
    private boolean value;
    final private String path = "config/globalConfig.json";
    final private String key = "isCommentFunctionAvailable";

    IsCommentAvailable()
    {
        JSONParser parser = new JSONParser();
        boolean isCommentAvailable = false;
        try {
            //测试和打了jar包以后resource的文件都只能用流读写
            ClassPathResource resource = new ClassPathResource("config/globalConfig.json");
            InputStream inputStream = resource.getInputStream();
            Reader reader = new InputStreamReader(inputStream);
            Object obj = parser.parse(reader);
            JSONObject jsonObj = (JSONObject) obj;
            isCommentAvailable = (boolean) jsonObj.get(key);
            inputStream.close();
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put(key, this.value);
            //测试和打了jar包以后resource的文件写入要用如下的类（PathResource+OutputStream）
            WritableResource resource = new PathResource("src/main/resources/config/globalConfig.json");
            OutputStream outputStream = resource.getOutputStream();
            Writer writer = new OutputStreamWriter(outputStream);
            writer.write(jsonObj.toJSONString());
            writer.close();
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
