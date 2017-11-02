package br.com.emmanuelneri.dto;

import lombok.Getter;

@Getter
public final class NumberUseDTO {

    private String number;
    private long duration;

    public NumberUseDTO(String number, long duration) {
        this.number = number;
        this.duration = duration;
    }
}
