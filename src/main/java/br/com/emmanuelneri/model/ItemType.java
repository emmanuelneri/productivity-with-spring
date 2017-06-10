package br.com.emmanuelneri.model;

import lombok.Getter;

@Getter
public enum ItemType {

    CALL("Call"),
    SERVICE("Service");

    private final String description;

    ItemType(String description) {
        this.description = description;
    }
}
