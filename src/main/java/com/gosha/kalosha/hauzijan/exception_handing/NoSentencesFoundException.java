package com.gosha.kalosha.hauzijan.exception_handing;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NoSentencesFoundException extends RuntimeException
{
    public NoSentencesFoundException(String message)
    {
        super(message);
    }
}
