package com.gosha.kalosha.hauzijan.exception_handing;

public class InvalidJsonException extends RuntimeException
{
    public InvalidJsonException(String message)
    {
        super(message);
    }
}
