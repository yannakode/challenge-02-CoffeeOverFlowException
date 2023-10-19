package com.compassuol.sp.challenge.msordes.enums;

import com.compassuol.sp.challenge.msordes.exceptions.customExceptions.InvalidDataException;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum Status {
    CONFIRMED, SENT, CANCELED;

    @JsonCreator
    public static Status fromString(String name) {
        return Stream
                .of(Status.values())
                .filter(val -> val.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new InvalidDataException("Invalid status value: " + name, "status"));
    }
}
