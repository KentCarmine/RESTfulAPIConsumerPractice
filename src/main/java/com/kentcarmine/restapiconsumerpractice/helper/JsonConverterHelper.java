package com.kentcarmine.restapiconsumerpractice.helper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverterHelper {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
