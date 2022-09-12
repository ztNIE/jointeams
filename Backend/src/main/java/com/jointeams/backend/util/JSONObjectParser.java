package com.jointeams.backend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

@Component
public class JSONObjectParser {

    private ObjectMapper objectMapper = new ObjectMapper();
    private JSONParser jsonParser = new JSONParser();

    public JSONObject parseObject(Object object) {
        try {
            String jsonString = objectMapper.writeValueAsString(object);
            return (JSONObject) jsonParser.parse(jsonString);
        } catch (JsonProcessingException | ParseException e) {
            return null;
        }
    }

    public JSONObject parseString(String jsonString) {
        try {
            return (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e) {
            return null;
        }
    }
}
