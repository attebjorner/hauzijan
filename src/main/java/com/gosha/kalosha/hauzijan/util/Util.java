package com.gosha.kalosha.hauzijan.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.util.Base64;

public class Util
{
    private static final ObjectMapper objectMapper;

    static
    {
        objectMapper = new ObjectMapper();
    }

    @SneakyThrows
    public static <T> T decodeJsonToObject(String s, Class<T> objectType)
    {
        return objectMapper.readValue(Base64.getDecoder().decode(s), objectType);
    }
}
