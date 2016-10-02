package com.shustanov.lorimobile.api;

import java.io.IOException;

public class InternetAccessException extends RuntimeException {
    public InternetAccessException(IOException e) {
        super(e);
    }
}
