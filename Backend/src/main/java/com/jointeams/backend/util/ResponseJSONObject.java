package com.jointeams.backend.util;

import org.json.simple.JSONObject;

public class ResponseJSONObject extends JSONObject {

    public ResponseJSONObject(DataJSONObject dataJSONObject) {
        this.put("data", dataJSONObject);
    }
}