package com.nadantas.jobopportunitymanagement.shared;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

    public static String objectToJSON(Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
