package com.jointeams.backend.util;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class JsonResult {
    int status;
    JSONObject msgAndData;

    public JsonResult() {
        this.status = 0;
        this.msgAndData = new JSONObject();
    }

    public void setMsg(String msg) {
        this.msgAndData.put("msg", msg);
    }

    public <T> void setData(T data) {
        JSONObject jsonObject =  new JSONObject();
        if(data == null || data == Optional.empty())
            jsonObject.put("Null", null);
        else
        {
            String[] name = data.getClass().getName().split("\\.");
            String key = name[name.length-1];
            jsonObject.put(key, data);
        }
        this.msgAndData.put("data", jsonObject);
    }

    public <T> void setData(String key, T data) {
        JSONObject jsonObject =  new JSONObject();
        jsonObject.put(key, data);
        this.msgAndData.put("data", jsonObject);
    }

    public <E> void setData(List<E> data) {
        JSONObject jsonObject =  new JSONObject();
        if(data.size() == 0)
            jsonObject.put("Null", null);
        else {
            String[] name = data.get(0).getClass().getName().split("\\.");
            String key = name[name.length-1];
            jsonObject.put(key + " List", data);
        }
        this.msgAndData.put("data", jsonObject);
    }

    public <E> void setDataList(String key, List<E> data) {
        JSONObject jsonObject =  new JSONObject();
        jsonObject.put(key + " List", data);
        this.msgAndData.put("data", jsonObject);
    }

    public <T> void setMsgAndData(String msg, T data) {
        setMsg(msg);
        setData(data);
    }

    public <T> void setMsgAndData(String msg, String key, T data) {
        setMsg(msg);
        setData(key, data);
    }

    public <E> void setMsgAndData(String msg, List<E> data) {
        setMsg(msg);
        setData(data);
    }

    public <E> void setMsgAndData(String msg, String key, List<E> data) {
        setMsg(msg);
        setData(key, data);
    }
}
