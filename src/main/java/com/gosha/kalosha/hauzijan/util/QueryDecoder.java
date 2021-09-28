package com.gosha.kalosha.hauzijan.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class QueryDecoder
{
    private final ObjectMapper objectMapper;

    @Autowired
    public QueryDecoder(ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    public <T> T decodeJsonToObject(String s, Class<T> objectType)
    {
        return objectMapper.readValue(Base64.getDecoder().decode(s), objectType);
    }
}
