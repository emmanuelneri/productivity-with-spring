package br.com.emmanuelneri.to;

import lombok.Getter;

@Getter
public final class ExceptionTO {

    private final String error;

    public ExceptionTO(String error) {
        this.error = error;
    }
}
